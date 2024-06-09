import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ArtworkCastService from './artwork-cast.service';
import { type IArtworkCast } from '@/shared/model/artwork-cast.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtworkCastDetails',
  setup() {
    const artworkCastService = inject('artworkCastService', () => new ArtworkCastService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const artworkCast: Ref<IArtworkCast> = ref({});

    const retrieveArtworkCast = async artworkCastId => {
      try {
        const res = await artworkCastService().find(artworkCastId);
        artworkCast.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artworkCastId) {
      retrieveArtworkCast(route.params.artworkCastId);
    }

    return {
      alertService,
      artworkCast,

      previousState,
      t$: useI18n().t,
    };
  },
});
