import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OrganizationService from './organization.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IOrganization } from '@/shared/model/organization.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationDetails',
  setup() {
    const organizationService = inject('organizationService', () => new OrganizationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const organization: Ref<IOrganization> = ref({});

    const retrieveOrganization = async organizationId => {
      try {
        const res = await organizationService().find(organizationId);
        organization.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationId) {
      retrieveOrganization(route.params.organizationId);
    }

    return {
      alertService,
      organization,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
