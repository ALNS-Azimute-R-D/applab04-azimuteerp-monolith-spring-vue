import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import BusinessUnitService from './business-unit.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';
import { type IOrganization } from '@/shared/model/organization.model';
import { type IBusinessUnit, BusinessUnit } from '@/shared/model/business-unit.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BusinessUnitUpdate',
  setup() {
    const businessUnitService = inject('businessUnitService', () => new BusinessUnitService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const businessUnit: Ref<IBusinessUnit> = ref(new BusinessUnit());

    const organizationService = inject('organizationService', () => new OrganizationService());

    const organizations: Ref<IOrganization[]> = ref([]);

    const businessUnits: Ref<IBusinessUnit[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveBusinessUnit = async businessUnitId => {
      try {
        const res = await businessUnitService().find(businessUnitId);
        businessUnit.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.businessUnitId) {
      retrieveBusinessUnit(route.params.businessUnitId);
    }

    const initRelationships = () => {
      organizationService()
        .retrieve()
        .then(res => {
          organizations.value = res.data;
        });
      businessUnitService()
        .retrieve()
        .then(res => {
          businessUnits.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      hierarchicalLevel: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      organization: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      businessUnitParent: {},
    };
    const v$ = useVuelidate(validationRules, businessUnit as any);
    v$.value.$validate();

    return {
      businessUnitService,
      alertService,
      businessUnit,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      organizations,
      businessUnits,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.businessUnit.id) {
        this.businessUnitService()
          .update(this.businessUnit)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.businessUnit.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.businessUnitService()
          .create(this.businessUnit)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.businessUnit.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
