import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AssetMetadataService from './asset-metadata.service';
import { type IAssetMetadata } from '@/shared/model/asset-metadata.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetMetadataDetails',
  setup() {
    const assetMetadataService = inject('assetMetadataService', () => new AssetMetadataService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const assetMetadata: Ref<IAssetMetadata> = ref({});

    const retrieveAssetMetadata = async assetMetadataId => {
      try {
        const res = await assetMetadataService().find(assetMetadataId);
        assetMetadata.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetMetadataId) {
      retrieveAssetMetadata(route.params.assetMetadataId);
    }

    return {
      alertService,
      assetMetadata,

      previousState,
      t$: useI18n().t,
    };
  },
});
