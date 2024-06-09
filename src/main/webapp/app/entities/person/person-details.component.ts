import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PersonService from './person.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IPerson } from '@/shared/model/person.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PersonDetails',
  setup() {
    const personService = inject('personService', () => new PersonService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const person: Ref<IPerson> = ref({});

    const retrievePerson = async personId => {
      try {
        const res = await personService().find(personId);
        person.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.personId) {
      retrievePerson(route.params.personId);
    }

    return {
      alertService,
      person,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
