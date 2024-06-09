import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfEventService from './type-of-event.service';
import { type ITypeOfEvent } from '@/shared/model/type-of-event.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfEventDetails',
  setup() {
    const typeOfEventService = inject('typeOfEventService', () => new TypeOfEventService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfEvent: Ref<ITypeOfEvent> = ref({});

    const retrieveTypeOfEvent = async typeOfEventId => {
      try {
        const res = await typeOfEventService().find(typeOfEventId);
        typeOfEvent.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfEventId) {
      retrieveTypeOfEvent(route.params.typeOfEventId);
    }

    return {
      alertService,
      typeOfEvent,

      previousState,
      t$: useI18n().t,
    };
  },
});
