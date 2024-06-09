import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfArtistService from './type-of-artist.service';
import { type ITypeOfArtist } from '@/shared/model/type-of-artist.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfArtistDetails',
  setup() {
    const typeOfArtistService = inject('typeOfArtistService', () => new TypeOfArtistService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfArtist: Ref<ITypeOfArtist> = ref({});

    const retrieveTypeOfArtist = async typeOfArtistId => {
      try {
        const res = await typeOfArtistService().find(typeOfArtistId);
        typeOfArtist.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfArtistId) {
      retrieveTypeOfArtist(route.params.typeOfArtistId);
    }

    return {
      alertService,
      typeOfArtist,

      previousState,
      t$: useI18n().t,
    };
  },
});
