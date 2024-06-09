import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import BusinessUnitService from './business-unit.service';
import { type IBusinessUnit } from '@/shared/model/business-unit.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BusinessUnitDetails',
  setup() {
    const businessUnitService = inject('businessUnitService', () => new BusinessUnitService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const businessUnit: Ref<IBusinessUnit> = ref({});

    const retrieveBusinessUnit = async businessUnitId => {
      try {
        const res = await businessUnitService().find(businessUnitId);
        businessUnit.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.businessUnitId) {
      retrieveBusinessUnit(route.params.businessUnitId);
    }

    return {
      alertService,
      businessUnit,

      previousState,
      t$: useI18n().t,
    };
  },
});
