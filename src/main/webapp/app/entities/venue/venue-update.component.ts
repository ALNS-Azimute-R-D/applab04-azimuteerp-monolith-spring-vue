import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import VenueService from './venue.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TypeOfVenueService from '@/entities/type-of-venue/type-of-venue.service';
import { type ITypeOfVenue } from '@/shared/model/type-of-venue.model';
import CommonLocalityService from '@/entities/common-locality/common-locality.service';
import { type ICommonLocality } from '@/shared/model/common-locality.model';
import { type IVenue, Venue } from '@/shared/model/venue.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'VenueUpdate',
  setup() {
    const venueService = inject('venueService', () => new VenueService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const venue: Ref<IVenue> = ref(new Venue());

    const typeOfVenueService = inject('typeOfVenueService', () => new TypeOfVenueService());

    const typeOfVenues: Ref<ITypeOfVenue[]> = ref([]);

    const commonLocalityService = inject('commonLocalityService', () => new CommonLocalityService());

    const commonLocalities: Ref<ICommonLocality[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      typeOfVenueService()
        .retrieve()
        .then(res => {
          typeOfVenues.value = res.data;
        });
      commonLocalityService()
        .retrieve()
        .then(res => {
          commonLocalities.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      description: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      geoPointLocation: {},
      typeOfVenue: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      commonLocality: {},
    };
    const v$ = useVuelidate(validationRules, venue as any);
    v$.value.$validate();

    return {
      venueService,
      alertService,
      venue,
      previousState,
      isSaving,
      currentLanguage,
      typeOfVenues,
      commonLocalities,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.venue.id) {
        this.venueService()
          .update(this.venue)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.venue.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.venueService()
          .create(this.venue)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.venue.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
