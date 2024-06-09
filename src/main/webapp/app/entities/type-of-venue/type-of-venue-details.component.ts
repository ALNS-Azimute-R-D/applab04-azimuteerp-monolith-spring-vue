import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfVenueService from './type-of-venue.service';
import { type ITypeOfVenue } from '@/shared/model/type-of-venue.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfVenueDetails',
  setup() {
    const typeOfVenueService = inject('typeOfVenueService', () => new TypeOfVenueService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfVenue: Ref<ITypeOfVenue> = ref({});

    const retrieveTypeOfVenue = async typeOfVenueId => {
      try {
        const res = await typeOfVenueService().find(typeOfVenueId);
        typeOfVenue.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfVenueId) {
      retrieveTypeOfVenue(route.params.typeOfVenueId);
    }

    return {
      alertService,
      typeOfVenue,

      previousState,
      t$: useI18n().t,
    };
  },
});
