import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TenantService from './tenant.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITenant, Tenant } from '@/shared/model/tenant.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TenantUpdate',
  setup() {
    const tenantService = inject('tenantService', () => new TenantService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tenant: Ref<ITenant> = ref(new Tenant());
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTenant = async tenantId => {
      try {
        const res = await tenantService().find(tenantId);
        tenant.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.tenantId) {
      retrieveTenant(route.params.tenantId);
    }

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      customerBusinessCode: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      businessHandlerClazz: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      mainContactPersonDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      billingDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      technicalEnvironmentsDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      logoImg: {},
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, tenant as any);
    v$.value.$validate();

    return {
      tenantService,
      alertService,
      tenant,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.tenant.id) {
        this.tenantService()
          .update(this.tenant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.tenant.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.tenantService()
          .create(this.tenant)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.tenant.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    clearInputImage(field, fieldContentType, idInput): void {
      if (this.tenant && field && fieldContentType) {
        if (Object.prototype.hasOwnProperty.call(this.tenant, field)) {
          this.tenant[field] = null;
        }
        if (Object.prototype.hasOwnProperty.call(this.tenant, fieldContentType)) {
          this.tenant[fieldContentType] = null;
        }
        if (idInput) {
          (<any>this).$refs[idInput] = null;
        }
      }
    },
  },
});
