import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import InventoryTransactionService from './inventory-transaction.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import SupplierService from '@/entities/supplier/supplier.service';
import { type ISupplier } from '@/shared/model/supplier.model';
import ProductService from '@/entities/product/product.service';
import { type IProduct } from '@/shared/model/product.model';
import WarehouseService from '@/entities/warehouse/warehouse.service';
import { type IWarehouse } from '@/shared/model/warehouse.model';
import { type IInventoryTransaction, InventoryTransaction } from '@/shared/model/inventory-transaction.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InventoryTransactionUpdate',
  setup() {
    const inventoryTransactionService = inject('inventoryTransactionService', () => new InventoryTransactionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const inventoryTransaction: Ref<IInventoryTransaction> = ref(new InventoryTransaction());

    const supplierService = inject('supplierService', () => new SupplierService());

    const suppliers: Ref<ISupplier[]> = ref([]);

    const productService = inject('productService', () => new ProductService());

    const products: Ref<IProduct[]> = ref([]);

    const warehouseService = inject('warehouseService', () => new WarehouseService());

    const warehouses: Ref<IWarehouse[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveInventoryTransaction = async inventoryTransactionId => {
      try {
        const res = await inventoryTransactionService().find(inventoryTransactionId);
        res.transactionCreatedDate = new Date(res.transactionCreatedDate);
        res.transactionModifiedDate = new Date(res.transactionModifiedDate);
        inventoryTransaction.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.inventoryTransactionId) {
      retrieveInventoryTransaction(route.params.inventoryTransactionId);
    }

    const initRelationships = () => {
      supplierService()
        .retrieve()
        .then(res => {
          suppliers.value = res.data;
        });
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
      warehouseService()
        .retrieve()
        .then(res => {
          warehouses.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      invoiceId: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      transactionCreatedDate: {},
      transactionModifiedDate: {},
      quantity: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      transactionComments: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      supplier: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      product: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      warehouse: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, inventoryTransaction as any);
    v$.value.$validate();

    return {
      inventoryTransactionService,
      alertService,
      inventoryTransaction,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      suppliers,
      products,
      warehouses,
      v$,
      ...useDateFormat({ entityRef: inventoryTransaction }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.inventoryTransaction.id) {
        this.inventoryTransactionService()
          .update(this.inventoryTransaction)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.inventoryTransactionService()
          .create(this.inventoryTransaction)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.created', { param: param.id }).toString(),
            );
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
