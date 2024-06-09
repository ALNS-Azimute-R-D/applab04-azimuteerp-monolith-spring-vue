import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrganizationMembershipService from './organization-membership.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';
import { type IOrganization } from '@/shared/model/organization.model';
import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import { type IOrganizationMembership, OrganizationMembership } from '@/shared/model/organization-membership.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationMembershipUpdate',
  setup() {
    const organizationMembershipService = inject('organizationMembershipService', () => new OrganizationMembershipService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const organizationMembership: Ref<IOrganizationMembership> = ref(new OrganizationMembership());

    const organizationService = inject('organizationService', () => new OrganizationService());

    const organizations: Ref<IOrganization[]> = ref([]);

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      organizationService()
        .retrieve()
        .then(res => {
          organizations.value = res.data;
        });
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      joinedAt: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organization: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      person: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, organizationMembership as any);
    v$.value.$validate();

    return {
      organizationMembershipService,
      alertService,
      organizationMembership,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      organizations,
      people,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.organizationMembership.id) {
        this.organizationMembershipService()
          .update(this.organizationMembership)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.organizationMembership.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.organizationMembershipService()
          .create(this.organizationMembership)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.organizationMembership.created', { param: param.id }).toString(),
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
