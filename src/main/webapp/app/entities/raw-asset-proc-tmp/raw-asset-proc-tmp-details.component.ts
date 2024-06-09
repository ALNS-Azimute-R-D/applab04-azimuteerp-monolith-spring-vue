import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RawAssetProcTmpService from './raw-asset-proc-tmp.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IRawAssetProcTmp } from '@/shared/model/raw-asset-proc-tmp.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RawAssetProcTmpDetails',
  setup() {
    const rawAssetProcTmpService = inject('rawAssetProcTmpService', () => new RawAssetProcTmpService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rawAssetProcTmp: Ref<IRawAssetProcTmp> = ref({});

    const retrieveRawAssetProcTmp = async rawAssetProcTmpId => {
      try {
        const res = await rawAssetProcTmpService().find(rawAssetProcTmpId);
        rawAssetProcTmp.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rawAssetProcTmpId) {
      retrieveRawAssetProcTmp(route.params.rawAssetProcTmpId);
    }

    return {
      alertService,
      rawAssetProcTmp,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
