import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrganizationService from './organization.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TenantService from '@/entities/tenant/tenant.service';
import { type ITenant } from '@/shared/model/tenant.model';
import TypeOfOrganizationService from '@/entities/type-of-organization/type-of-organization.service';
import { type ITypeOfOrganization } from '@/shared/model/type-of-organization.model';
import { type IOrganization, Organization } from '@/shared/model/organization.model';
import { OrganizationStatusEnum } from '@/shared/model/enumerations/organization-status-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationUpdate',
  setup() {
    const organizationService = inject('organizationService', () => new OrganizationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const organization: Ref<IOrganization> = ref(new Organization());

    const tenantService = inject('tenantService', () => new TenantService());

    const tenants: Ref<ITenant[]> = ref([]);

    const typeOfOrganizationService = inject('typeOfOrganizationService', () => new TypeOfOrganizationService());

    const typeOfOrganizations: Ref<ITypeOfOrganization[]> = ref([]);

    const organizations: Ref<IOrganization[]> = ref([]);
    const organizationStatusEnumValues: Ref<string[]> = ref(Object.keys(OrganizationStatusEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      tenantService()
        .retrieve()
        .then(res => {
          tenants.value = res.data;
        });
      typeOfOrganizationService()
        .retrieve()
        .then(res => {
          typeOfOrganizations.value = res.data;
        });
      organizationService()
        .retrieve()
        .then(res => {
          organizations.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      businessCode: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      hierarchicalLevel: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      businessHandlerClazz: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      mainContactPersonDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      technicalEnvironmentsDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      organizationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      logoImg: {},
      tenant: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      typeOfOrganization: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organizationParent: {},
    };
    const v$ = useVuelidate(validationRules, organization as any);
    v$.value.$validate();

    return {
      organizationService,
      alertService,
      organization,
      previousState,
      organizationStatusEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      tenants,
      typeOfOrganizations,
      organizations,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.organization.id) {
        this.organizationService()
          .update(this.organization)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.organization.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.organizationService()
          .create(this.organization)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.organization.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    clearInputImage(field, fieldContentType, idInput): void {
      if (this.organization && field && fieldContentType) {
        if (Object.prototype.hasOwnProperty.call(this.organization, field)) {
          this.organization[field] = null;
        }
        if (Object.prototype.hasOwnProperty.call(this.organization, fieldContentType)) {
          this.organization[fieldContentType] = null;
        }
        if (idInput) {
          (<any>this).$refs[idInput] = null;
        }
      }
    },
  },
});
