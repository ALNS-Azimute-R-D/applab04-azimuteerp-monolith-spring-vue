import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import VenueService from './venue.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IVenue } from '@/shared/model/venue.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'VenueDetails',
  setup() {
    const venueService = inject('venueService', () => new VenueService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const venue: Ref<IVenue> = ref({});

    const retrieveVenue = async venueId => {
      try {
        const res = await venueService().find(venueId);
        venue.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.venueId) {
      retrieveVenue(route.params.venueId);
    }

    return {
      alertService,
      venue,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
