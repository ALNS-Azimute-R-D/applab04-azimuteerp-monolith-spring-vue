import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import InventoryTransactionService from './inventory-transaction.service';
import { useDateFormat } from '@/shared/composables';
import { type IInventoryTransaction } from '@/shared/model/inventory-transaction.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InventoryTransactionDetails',
  setup() {
    const dateFormat = useDateFormat();
    const inventoryTransactionService = inject('inventoryTransactionService', () => new InventoryTransactionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const inventoryTransaction: Ref<IInventoryTransaction> = ref({});

    const retrieveInventoryTransaction = async inventoryTransactionId => {
      try {
        const res = await inventoryTransactionService().find(inventoryTransactionId);
        inventoryTransaction.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.inventoryTransactionId) {
      retrieveInventoryTransaction(route.params.inventoryTransactionId);
    }

    return {
      ...dateFormat,
      alertService,
      inventoryTransaction,

      previousState,
      t$: useI18n().t,
    };
  },
});
