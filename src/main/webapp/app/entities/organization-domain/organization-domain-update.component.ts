import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrganizationDomainService from './organization-domain.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';
import { type IOrganization } from '@/shared/model/organization.model';
import { type IOrganizationDomain, OrganizationDomain } from '@/shared/model/organization-domain.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationDomainUpdate',
  setup() {
    const organizationDomainService = inject('organizationDomainService', () => new OrganizationDomainService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const organizationDomain: Ref<IOrganizationDomain> = ref(new OrganizationDomain());

    const organizationService = inject('organizationService', () => new OrganizationService());

    const organizations: Ref<IOrganization[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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
      domainAcronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      isVerified: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      businessHandlerClazz: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organization: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, organizationDomain as any);
    v$.value.$validate();

    return {
      organizationDomainService,
      alertService,
      organizationDomain,
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
      if (this.organizationDomain.id) {
        this.organizationDomainService()
          .update(this.organizationDomain)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.organizationDomain.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.organizationDomainService()
          .create(this.organizationDomain)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.organizationDomain.created', { param: param.id }).toString(),
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
