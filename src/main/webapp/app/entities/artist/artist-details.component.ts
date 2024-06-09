import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ArtistService from './artist.service';
import { type IArtist } from '@/shared/model/artist.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtistDetails',
  setup() {
    const artistService = inject('artistService', () => new ArtistService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const artist: Ref<IArtist> = ref({});

    const retrieveArtist = async artistId => {
      try {
        const res = await artistService().find(artistId);
        artist.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artistId) {
      retrieveArtist(route.params.artistId);
    }

    return {
      alertService,
      artist,

      previousState,
      t$: useI18n().t,
    };
  },
});
