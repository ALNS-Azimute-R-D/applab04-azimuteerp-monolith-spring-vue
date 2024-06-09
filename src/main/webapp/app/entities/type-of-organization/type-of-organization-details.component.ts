import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeOfOrganizationService from './type-of-organization.service';
import { type ITypeOfOrganization } from '@/shared/model/type-of-organization.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfOrganizationDetails',
  setup() {
    const typeOfOrganizationService = inject('typeOfOrganizationService', () => new TypeOfOrganizationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const typeOfOrganization: Ref<ITypeOfOrganization> = ref({});

    const retrieveTypeOfOrganization = async typeOfOrganizationId => {
      try {
        const res = await typeOfOrganizationService().find(typeOfOrganizationId);
        typeOfOrganization.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfOrganizationId) {
      retrieveTypeOfOrganization(route.params.typeOfOrganizationId);
    }

    return {
      alertService,
      typeOfOrganization,

      previousState,
      t$: useI18n().t,
    };
  },
});
