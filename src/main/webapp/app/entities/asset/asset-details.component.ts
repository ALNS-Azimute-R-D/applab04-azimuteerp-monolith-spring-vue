import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AssetService from './asset.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IAsset } from '@/shared/model/asset.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetDetails',
  setup() {
    const assetService = inject('assetService', () => new AssetService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const asset: Ref<IAsset> = ref({});

    const retrieveAsset = async assetId => {
      try {
        const res = await assetService().find(assetId);
        asset.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetId) {
      retrieveAsset(route.params.assetId);
    }

    return {
      alertService,
      asset,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
