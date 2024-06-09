import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfActivityService from './type-of-activity.service';
import { type ITypeOfActivity } from '@/shared/model/type-of-activity.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfActivityDetails',
  setup() {
    const typeOfActivityService = inject('typeOfActivityService', () => new TypeOfActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfActivity: Ref<ITypeOfActivity> = ref({});

    const retrieveTypeOfActivity = async typeOfActivityId => {
      try {
        const res = await typeOfActivityService().find(typeOfActivityId);
        typeOfActivity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfActivityId) {
      retrieveTypeOfActivity(route.params.typeOfActivityId);
    }

    return {
      alertService,
      typeOfActivity,

      previousState,
      t$: useI18n().t,
    };
  },
});
