import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ArtworkService from './artwork.service';
import { type IArtwork } from '@/shared/model/artwork.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtworkDetails',
  setup() {
    const artworkService = inject('artworkService', () => new ArtworkService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const artwork: Ref<IArtwork> = ref({});

    const retrieveArtwork = async artworkId => {
      try {
        const res = await artworkService().find(artworkId);
        artwork.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artworkId) {
      retrieveArtwork(route.params.artworkId);
    }

    return {
      alertService,
      artwork,

      previousState,
      t$: useI18n().t,
    };
  },
});
