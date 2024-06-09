import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfPersonService from './type-of-person.service';
import { type ITypeOfPerson } from '@/shared/model/type-of-person.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfPersonDetails',
  setup() {
    const typeOfPersonService = inject('typeOfPersonService', () => new TypeOfPersonService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfPerson: Ref<ITypeOfPerson> = ref({});

    const retrieveTypeOfPerson = async typeOfPersonId => {
      try {
        const res = await typeOfPersonService().find(typeOfPersonId);
        typeOfPerson.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfPersonId) {
      retrieveTypeOfPerson(route.params.typeOfPersonId);
    }

    return {
      alertService,
      typeOfPerson,

      previousState,
      t$: useI18n().t,
    };
  },
});
