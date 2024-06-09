import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfArtmediaService from './type-of-artmedia.service';
import { type ITypeOfArtmedia } from '@/shared/model/type-of-artmedia.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfArtmediaDetails',
  setup() {
    const typeOfArtmediaService = inject('typeOfArtmediaService', () => new TypeOfArtmediaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfArtmedia: Ref<ITypeOfArtmedia> = ref({});

    const retrieveTypeOfArtmedia = async typeOfArtmediaId => {
      try {
        const res = await typeOfArtmediaService().find(typeOfArtmediaId);
        typeOfArtmedia.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfArtmediaId) {
      retrieveTypeOfArtmedia(route.params.typeOfArtmediaId);
    }

    return {
      alertService,
      typeOfArtmedia,

      previousState,
      t$: useI18n().t,
    };
  },
});
