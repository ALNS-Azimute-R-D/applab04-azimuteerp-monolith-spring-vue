import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OrganizationAttributeService from './organization-attribute.service';
import { type IOrganizationAttribute } from '@/shared/model/organization-attribute.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationAttributeDetails',
  setup() {
    const organizationAttributeService = inject('organizationAttributeService', () => new OrganizationAttributeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const organizationAttribute: Ref<IOrganizationAttribute> = ref({});

    const retrieveOrganizationAttribute = async organizationAttributeId => {
      try {
        const res = await organizationAttributeService().find(organizationAttributeId);
        organizationAttribute.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationAttributeId) {
      retrieveOrganizationAttribute(route.params.organizationAttributeId);
    }

    return {
      alertService,
      organizationAttribute,

      previousState,
      t$: useI18n().t,
    };
  },
});
