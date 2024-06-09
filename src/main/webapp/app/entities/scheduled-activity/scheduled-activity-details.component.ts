import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ScheduledActivityService from './scheduled-activity.service';
import { useDateFormat } from '@/shared/composables';
import { type IScheduledActivity } from '@/shared/model/scheduled-activity.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ScheduledActivityDetails',
  setup() {
    const dateFormat = useDateFormat();
    const scheduledActivityService = inject('scheduledActivityService', () => new ScheduledActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const scheduledActivity: Ref<IScheduledActivity> = ref({});

    const retrieveScheduledActivity = async scheduledActivityId => {
      try {
        const res = await scheduledActivityService().find(scheduledActivityId);
        scheduledActivity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.scheduledActivityId) {
      retrieveScheduledActivity(route.params.scheduledActivityId);
    }

    return {
      ...dateFormat,
      alertService,
      scheduledActivity,

      previousState,
      t$: useI18n().t,
    };
  },
});
