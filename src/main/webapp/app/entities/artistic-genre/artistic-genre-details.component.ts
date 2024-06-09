import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ArtisticGenreService from './artistic-genre.service';
import { type IArtisticGenre } from '@/shared/model/artistic-genre.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ArtisticGenreDetails',
  setup() {
    const artisticGenreService = inject('artisticGenreService', () => new ArtisticGenreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const artisticGenre: Ref<IArtisticGenre> = ref({});

    const retrieveArtisticGenre = async artisticGenreId => {
      try {
        const res = await artisticGenreService().find(artisticGenreId);
        artisticGenre.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.artisticGenreId) {
      retrieveArtisticGenre(route.params.artisticGenreId);
    }

    return {
      alertService,
      artisticGenre,

      previousState,
      t$: useI18n().t,
    };
  },
});
