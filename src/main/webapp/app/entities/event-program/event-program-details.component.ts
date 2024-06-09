import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EventProgramService from './event-program.service';
import { type IEventProgram } from '@/shared/model/event-program.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventProgramDetails',
  setup() {
    const eventProgramService = inject('eventProgramService', () => new EventProgramService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const eventProgram: Ref<IEventProgram> = ref({});

    const retrieveEventProgram = async eventProgramId => {
      try {
        const res = await eventProgramService().find(eventProgramId);
        eventProgram.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.eventProgramId) {
      retrieveEventProgram(route.params.eventProgramId);
    }

    return {
      alertService,
      eventProgram,

      previousState,
      t$: useI18n().t,
    };
  },
});
