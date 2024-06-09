import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OrganizationMemberRoleService from './organization-member-role.service';
import { type IOrganizationMemberRole } from '@/shared/model/organization-member-role.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationMemberRoleDetails',
  setup() {
    const organizationMemberRoleService = inject('organizationMemberRoleService', () => new OrganizationMemberRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const organizationMemberRole: Ref<IOrganizationMemberRole> = ref({});

    const retrieveOrganizationMemberRole = async organizationMemberRoleId => {
      try {
        const res = await organizationMemberRoleService().find(organizationMemberRoleId);
        organizationMemberRole.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationMemberRoleId) {
      retrieveOrganizationMemberRole(route.params.organizationMemberRoleId);
    }

    return {
      alertService,
      organizationMemberRole,

      previousState,
      t$: useI18n().t,
    };
  },
});
