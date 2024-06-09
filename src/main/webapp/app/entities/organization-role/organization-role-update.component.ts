import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrganizationRoleService from './organization-role.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';
import { type IOrganization } from '@/shared/model/organization.model';
import { type IOrganizationRole, OrganizationRole } from '@/shared/model/organization-role.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationRoleUpdate',
  setup() {
    const organizationRoleService = inject('organizationRoleService', () => new OrganizationRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const organizationRole: Ref<IOrganizationRole> = ref(new OrganizationRole());

    const organizationService = inject('organizationService', () => new OrganizationService());

    const organizations: Ref<IOrganization[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      organizationService()
        .retrieve()
        .then(res => {
          organizations.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      roleName: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organization: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, organizationRole as any);
    v$.value.$validate();

    return {
      organizationRoleService,
      alertService,
      organizationRole,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      organizations,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.organizationRole.id) {
        this.organizationRoleService()
          .update(this.organizationRole)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.organizationRole.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.organizationRoleService()
          .create(this.organizationRole)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.organizationRole.created', { param: param.id }).toString(),
            );
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
