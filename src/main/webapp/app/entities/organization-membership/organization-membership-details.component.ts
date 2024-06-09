import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OrganizationMembershipService from './organization-membership.service';
import { type IOrganizationMembership } from '@/shared/model/organization-membership.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationMembershipDetails',
  setup() {
    const organizationMembershipService = inject('organizationMembershipService', () => new OrganizationMembershipService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const organizationMembership: Ref<IOrganizationMembership> = ref({});

    const retrieveOrganizationMembership = async organizationMembershipId => {
      try {
        const res = await organizationMembershipService().find(organizationMembershipId);
        organizationMembership.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationMembershipId) {
      retrieveOrganizationMembership(route.params.organizationMembershipId);
    }

    return {
      alertService,
      organizationMembership,

      previousState,
      t$: useI18n().t,
    };
  },
});
