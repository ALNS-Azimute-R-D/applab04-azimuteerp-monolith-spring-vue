import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import WarehouseService from './warehouse.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IWarehouse } from '@/shared/model/warehouse.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'WarehouseDetails',
  setup() {
    const warehouseService = inject('warehouseService', () => new WarehouseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const warehouse: Ref<IWarehouse> = ref({});

    const retrieveWarehouse = async warehouseId => {
      try {
        const res = await warehouseService().find(warehouseId);
        warehouse.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.warehouseId) {
      retrieveWarehouse(route.params.warehouseId);
    }

    return {
      alertService,
      warehouse,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
