import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OrganizationDomainService from './organization-domain.service';
import { type IOrganizationDomain } from '@/shared/model/organization-domain.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationDomainDetails',
  setup() {
    const organizationDomainService = inject('organizationDomainService', () => new OrganizationDomainService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const organizationDomain: Ref<IOrganizationDomain> = ref({});

    const retrieveOrganizationDomain = async organizationDomainId => {
      try {
        const res = await organizationDomainService().find(organizationDomainId);
        organizationDomain.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationDomainId) {
      retrieveOrganizationDomain(route.params.organizationDomainId);
    }

    return {
      alertService,
      organizationDomain,

      previousState,
      t$: useI18n().t,
    };
  },
});
