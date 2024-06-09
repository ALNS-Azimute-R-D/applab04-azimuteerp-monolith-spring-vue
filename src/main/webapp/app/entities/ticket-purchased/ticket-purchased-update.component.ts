import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TicketPurchasedService from './ticket-purchased.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EventService from '@/entities/event/event.service';
import { type IEvent } from '@/shared/model/event.model';
import InvoiceService from '@/entities/invoice/invoice.service';
import { type IInvoice } from '@/shared/model/invoice.model';
import { type ITicketPurchased, TicketPurchased } from '@/shared/model/ticket-purchased.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TicketPurchasedUpdate',
  setup() {
    const ticketPurchasedService = inject('ticketPurchasedService', () => new TicketPurchasedService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const ticketPurchased: Ref<ITicketPurchased> = ref(new TicketPurchased());

    const eventService = inject('eventService', () => new EventService());

    const events: Ref<IEvent[]> = ref([]);

    const invoiceService = inject('invoiceService', () => new InvoiceService());

    const invoices: Ref<IInvoice[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTicketPurchased = async ticketPurchasedId => {
      try {
        const res = await ticketPurchasedService().find(ticketPurchasedId);
        res.duePaymentDate = new Date(res.duePaymentDate);
        ticketPurchased.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.ticketPurchasedId) {
      retrieveTicketPurchased(route.params.ticketPurchasedId);
    }

    const initRelationships = () => {
      eventService()
        .retrieve()
        .then(res => {
          events.value = res.data;
        });
      invoiceService()
        .retrieve()
        .then(res => {
          invoices.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      buyingCode: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      duePaymentDate: {},
      amountOfTickets: {},
      taxValue: {},
      ticketValue: {},
      acquiredSeatsNumbers: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      description: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      event: {},
      invoice: {},
    };
    const v$ = useVuelidate(validationRules, ticketPurchased as any);
    v$.value.$validate();

    return {
      ticketPurchasedService,
      alertService,
      ticketPurchased,
      previousState,
      isSaving,
      currentLanguage,
      events,
      invoices,
      v$,
      ...useDateFormat({ entityRef: ticketPurchased }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.ticketPurchased.id) {
        this.ticketPurchasedService()
          .update(this.ticketPurchased)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.ticketPurchased.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.ticketPurchasedService()
          .create(this.ticketPurchased)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.ticketPurchased.created', { param: param.id }).toString(),
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
