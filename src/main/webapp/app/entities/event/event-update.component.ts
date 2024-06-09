import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EventService from './event.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import VenueService from '@/entities/venue/venue.service';
import { type IVenue } from '@/shared/model/venue.model';
import TypeOfEventService from '@/entities/type-of-event/type-of-event.service';
import { type ITypeOfEvent } from '@/shared/model/type-of-event.model';
import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import { type IEvent, Event } from '@/shared/model/event.model';
import { EventStatusEnum } from '@/shared/model/enumerations/event-status-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventUpdate',
  setup() {
    const eventService = inject('eventService', () => new EventService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const event: Ref<IEvent> = ref(new Event());

    const venueService = inject('venueService', () => new VenueService());

    const venues: Ref<IVenue[]> = ref([]);

    const typeOfEventService = inject('typeOfEventService', () => new TypeOfEventService());

    const typeOfEvents: Ref<ITypeOfEvent[]> = ref([]);

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);

    const assetCollectionService = inject('assetCollectionService', () => new AssetCollectionService());

    const assetCollections: Ref<IAssetCollection[]> = ref([]);
    const eventStatusEnumValues: Ref<string[]> = ref(Object.keys(EventStatusEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEvent = async eventId => {
      try {
        const res = await eventService().find(eventId);
        res.startTime = new Date(res.startTime);
        res.endTime = new Date(res.endTime);
        event.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.eventId) {
      retrieveEvent(route.params.eventId);
    }

    const initRelationships = () => {
      venueService()
        .retrieve()
        .then(res => {
          venues.value = res.data;
        });
      typeOfEventService()
        .retrieve()
        .then(res => {
          typeOfEvents.value = res.data;
        });
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
      assetCollectionService()
        .retrieve()
        .then(res => {
          assetCollections.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 150 }).toString(), 150),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      fullDescription: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      startTime: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      endTime: {},
      defaultTicketValue: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      mainVenue: {},
      typeOfEvent: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      promoteurPerson: {},
      assetCollections: {},
    };
    const v$ = useVuelidate(validationRules, event as any);
    v$.value.$validate();

    return {
      eventService,
      alertService,
      event,
      previousState,
      eventStatusEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      venues,
      typeOfEvents,
      people,
      assetCollections,
      v$,
      ...useDateFormat({ entityRef: event }),
      t$,
    };
  },
  created(): void {
    this.event.assetCollections = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.event.id) {
        this.eventService()
          .update(this.event)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.event.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.eventService()
          .create(this.event)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.event.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
