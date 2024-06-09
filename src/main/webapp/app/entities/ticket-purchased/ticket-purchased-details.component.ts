import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TicketPurchasedService from './ticket-purchased.service';
import { useDateFormat } from '@/shared/composables';
import { type ITicketPurchased } from '@/shared/model/ticket-purchased.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TicketPurchasedDetails',
  setup() {
    const dateFormat = useDateFormat();
    const ticketPurchasedService = inject('ticketPurchasedService', () => new TicketPurchasedService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const ticketPurchased: Ref<ITicketPurchased> = ref({});

    const retrieveTicketPurchased = async ticketPurchasedId => {
      try {
        const res = await ticketPurchasedService().find(ticketPurchasedId);
        ticketPurchased.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.ticketPurchasedId) {
      retrieveTicketPurchased(route.params.ticketPurchasedId);
    }

    return {
      ...dateFormat,
      alertService,
      ticketPurchased,

      previousState,
      t$: useI18n().t,
    };
  },
});
