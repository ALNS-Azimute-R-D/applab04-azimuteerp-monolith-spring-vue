import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AssetTypeService from './asset-type.service';
import { type IAssetType } from '@/shared/model/asset-type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetTypeDetails',
  setup() {
    const assetTypeService = inject('assetTypeService', () => new AssetTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const assetType: Ref<IAssetType> = ref({});

    const retrieveAssetType = async assetTypeId => {
      try {
        const res = await assetTypeService().find(assetTypeId);
        assetType.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetTypeId) {
      retrieveAssetType(route.params.assetTypeId);
    }

    return {
      alertService,
      assetType,

      previousState,
      t$: useI18n().t,
    };
  },
});
