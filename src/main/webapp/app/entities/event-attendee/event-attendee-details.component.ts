import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EventAttendeeService from './event-attendee.service';
import { type IEventAttendee } from '@/shared/model/event-attendee.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventAttendeeDetails',
  setup() {
    const eventAttendeeService = inject('eventAttendeeService', () => new EventAttendeeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const eventAttendee: Ref<IEventAttendee> = ref({});

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

    return {
      alertService,
      eventAttendee,

      previousState,
      t$: useI18n().t,
    };
  },
});
