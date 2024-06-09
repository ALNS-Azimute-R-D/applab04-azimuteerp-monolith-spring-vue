import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OrganizationRoleService from './organization-role.service';
import { type IOrganizationRole } from '@/shared/model/organization-role.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationRoleDetails',
  setup() {
    const organizationRoleService = inject('organizationRoleService', () => new OrganizationRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const organizationRole: Ref<IOrganizationRole> = ref({});

    const retrieveOrganizationRole = async organizationRoleId => {
      try {
        const res = await organizationRoleService().find(organizationRoleId);
        organizationRole.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationRoleId) {
      retrieveOrganizationRole(route.params.organizationRoleId);
    }

    return {
      alertService,
      organizationRole,

      previousState,
      t$: useI18n().t,
    };
  },
});
