import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProductService from './product.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import BrandService from '@/entities/brand/brand.service';
import { type IBrand } from '@/shared/model/brand.model';
import SupplierService from '@/entities/supplier/supplier.service';
import { type ISupplier } from '@/shared/model/supplier.model';
import { type IProduct, Product } from '@/shared/model/product.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProductUpdate',
  setup() {
    const productService = inject('productService', () => new ProductService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const product: Ref<IProduct> = ref(new Product());

    const brandService = inject('brandService', () => new BrandService());

    const brands: Ref<IBrand[]> = ref([]);

    const supplierService = inject('supplierService', () => new SupplierService());

    const suppliers: Ref<ISupplier[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveProduct = async productId => {
      try {
        const res = await productService().find(productId);
        product.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.productId) {
      retrieveProduct(route.params.productId);
    }

    const initRelationships = () => {
      brandService()
        .retrieve()
        .then(res => {
          brands.value = res.data;
        });
      supplierService()
        .retrieve()
        .then(res => {
          suppliers.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      productSKU: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 25 }).toString(), 25),
      },
      productName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      description: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      standardCost: {},
      listPrice: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      reorderLevel: {},
      targetLevel: {},
      quantityPerUnit: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      discontinued: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      minimumReorderQuantity: {},
      suggestedCategory: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      attachments: {},
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      brand: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      toSuppliers: {},
    };
    const v$ = useVuelidate(validationRules, product as any);
    v$.value.$validate();

    return {
      productService,
      alertService,
      product,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      brands,
      suppliers,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {
    this.product.toSuppliers = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.product.id) {
        this.productService()
          .update(this.product)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.product.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.productService()
          .create(this.product)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.product.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
