import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CommonLocalityService from './common-locality.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type ICommonLocality } from '@/shared/model/common-locality.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CommonLocalityDetails',
  setup() {
    const commonLocalityService = inject('commonLocalityService', () => new CommonLocalityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const commonLocality: Ref<ICommonLocality> = ref({});

    const retrieveCommonLocality = async commonLocalityId => {
      try {
        const res = await commonLocalityService().find(commonLocalityId);
        commonLocality.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.commonLocalityId) {
      retrieveCommonLocality(route.params.commonLocalityId);
    }

    return {
      alertService,
      commonLocality,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
