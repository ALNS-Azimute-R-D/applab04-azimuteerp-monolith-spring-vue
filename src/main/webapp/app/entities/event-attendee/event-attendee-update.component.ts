import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EventAttendeeService from './event-attendee.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import EventService from '@/entities/event/event.service';
import { type IEvent } from '@/shared/model/event.model';
import TicketPurchasedService from '@/entities/ticket-purchased/ticket-purchased.service';
import { type ITicketPurchased } from '@/shared/model/ticket-purchased.model';
import { type IEventAttendee, EventAttendee } from '@/shared/model/event-attendee.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventAttendeeUpdate',
  setup() {
    const eventAttendeeService = inject('eventAttendeeService', () => new EventAttendeeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const eventAttendee: Ref<IEventAttendee> = ref(new EventAttendee());

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);

    const eventService = inject('eventService', () => new EventService());

    const events: Ref<IEvent[]> = ref([]);

    const ticketPurchasedService = inject('ticketPurchasedService', () => new TicketPurchasedService());

    const ticketPurchaseds: Ref<ITicketPurchased[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEventAttendee = async eventAttendeeId => {
      try {
        const res = await eventAttendeeService().find(eventAttendeeId);
        eventAttendee.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.eventAttendeeId) {
      retrieveEventAttendee(route.params.eventAttendeeId);
    }

    const initRelationships = () => {
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
      eventService()
        .retrieve()
        .then(res => {
          events.value = res.data;
        });
      ticketPurchasedService()
        .retrieve()
        .then(res => {
          ticketPurchaseds.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      attendedAsRole: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      wasPresentInEvent: {},
      shouldBuyTicket: {},
      ticketNumber: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      seatNumber: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 10 }).toString(), 10),
      },
      attendeePerson: {},
      event: {},
      ticketPurchased: {},
    };
    const v$ = useVuelidate(validationRules, eventAttendee as any);
    v$.value.$validate();

    return {
      eventAttendeeService,
      alertService,
      eventAttendee,
      previousState,
      isSaving,
      currentLanguage,
      people,
      events,
      ticketPurchaseds,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.eventAttendee.id) {
        this.eventAttendeeService()
          .update(this.eventAttendee)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.eventAttendee.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.eventAttendeeService()
          .create(this.eventAttendee)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.eventAttendee.created', { param: param.id }).toString(),
            );
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
