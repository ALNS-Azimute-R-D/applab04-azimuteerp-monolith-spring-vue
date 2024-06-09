import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import StockLevelService from './stock-level.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import WarehouseService from '@/entities/warehouse/warehouse.service';
import { type IWarehouse } from '@/shared/model/warehouse.model';
import ProductService from '@/entities/product/product.service';
import { type IProduct } from '@/shared/model/product.model';
import { type IStockLevel, StockLevel } from '@/shared/model/stock-level.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StockLevelUpdate',
  setup() {
    const stockLevelService = inject('stockLevelService', () => new StockLevelService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const stockLevel: Ref<IStockLevel> = ref(new StockLevel());

    const warehouseService = inject('warehouseService', () => new WarehouseService());

    const warehouses: Ref<IWarehouse[]> = ref([]);

    const productService = inject('productService', () => new ProductService());

    const products: Ref<IProduct[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveStockLevel = async stockLevelId => {
      try {
        const res = await stockLevelService().find(stockLevelId);
        res.lastModifiedDate = new Date(res.lastModifiedDate);
        stockLevel.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stockLevelId) {
      retrieveStockLevel(route.params.stockLevelId);
    }

    const initRelationships = () => {
      warehouseService()
        .retrieve()
        .then(res => {
          warehouses.value = res.data;
        });
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      lastModifiedDate: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      remainingQuantity: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      commonAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      warehouse: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      product: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, stockLevel as any);
    v$.value.$validate();

    return {
      stockLevelService,
      alertService,
      stockLevel,
      previousState,
      isSaving,
      currentLanguage,
      warehouses,
      products,
      v$,
      ...useDateFormat({ entityRef: stockLevel }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.stockLevel.id) {
        this.stockLevelService()
          .update(this.stockLevel)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.stockLevel.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.stockLevelService()
          .create(this.stockLevel)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.stockLevel.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
