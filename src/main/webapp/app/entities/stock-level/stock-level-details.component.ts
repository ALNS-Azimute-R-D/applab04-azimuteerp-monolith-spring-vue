import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import StockLevelService from './stock-level.service';
import { useDateFormat } from '@/shared/composables';
import { type IStockLevel } from '@/shared/model/stock-level.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'StockLevelDetails',
  setup() {
    const dateFormat = useDateFormat();
    const stockLevelService = inject('stockLevelService', () => new StockLevelService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const stockLevel: Ref<IStockLevel> = ref({});

    const retrieveStockLevel = async stockLevelId => {
      try {
        const res = await stockLevelService().find(stockLevelId);
        stockLevel.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.stockLevelId) {
      retrieveStockLevel(route.params.stockLevelId);
    }

    return {
      ...dateFormat,
      alertService,
      stockLevel,

      previousState,
      t$: useI18n().t,
    };
  },
});
