import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AssetCollectionService from './asset-collection.service';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetCollectionDetails',
  setup() {
    const assetCollectionService = inject('assetCollectionService', () => new AssetCollectionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const assetCollection: Ref<IAssetCollection> = ref({});

    const retrieveAssetCollection = async assetCollectionId => {
      try {
        const res = await assetCollectionService().find(assetCollectionId);
        assetCollection.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetCollectionId) {
      retrieveAssetCollection(route.params.assetCollectionId);
    }

    return {
      alertService,
      assetCollection,

      previousState,
      t$: useI18n().t,
    };
  },
});
