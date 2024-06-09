import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrganizationMemberRoleService from './organization-member-role.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OrganizationMembershipService from '@/entities/organization-membership/organization-membership.service';
import { type IOrganizationMembership } from '@/shared/model/organization-membership.model';
import OrganizationRoleService from '@/entities/organization-role/organization-role.service';
import { type IOrganizationRole } from '@/shared/model/organization-role.model';
import { type IOrganizationMemberRole, OrganizationMemberRole } from '@/shared/model/organization-member-role.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationMemberRoleUpdate',
  setup() {
    const organizationMemberRoleService = inject('organizationMemberRoleService', () => new OrganizationMemberRoleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const organizationMemberRole: Ref<IOrganizationMemberRole> = ref(new OrganizationMemberRole());

    const organizationMembershipService = inject('organizationMembershipService', () => new OrganizationMembershipService());

    const organizationMemberships: Ref<IOrganizationMembership[]> = ref([]);

    const organizationRoleService = inject('organizationRoleService', () => new OrganizationRoleService());

    const organizationRoles: Ref<IOrganizationRole[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      organizationMembershipService()
        .retrieve()
        .then(res => {
          organizationMemberships.value = res.data;
        });
      organizationRoleService()
        .retrieve()
        .then(res => {
          organizationRoles.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      joinedAt: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organizationMembership: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organizationRole: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, organizationMemberRole as any);
    v$.value.$validate();

    return {
      organizationMemberRoleService,
      alertService,
      organizationMemberRole,
      previousState,
      isSaving,
      currentLanguage,
      organizationMemberships,
      organizationRoles,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.organizationMemberRole.id) {
        this.organizationMemberRoleService()
          .update(this.organizationMemberRole)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.organizationMemberRoleService()
          .create(this.organizationMemberRole)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.created', { param: param.id }).toString(),
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
