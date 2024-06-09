import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CustomerTypeService from './customer-type.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ICustomerType, CustomerType } from '@/shared/model/customer-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CustomerTypeUpdate',
  setup() {
    const customerTypeService = inject('customerTypeService', () => new CustomerTypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const customerType: Ref<ICustomerType> = ref(new CustomerType());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCustomerType = async customerTypeId => {
      try {
        const res = await customerTypeService().find(customerTypeId);
        customerType.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.customerTypeId) {
      retrieveCustomerType(route.params.customerTypeId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      description: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      businessHandlerClazz: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
    };
    const v$ = useVuelidate(validationRules, customerType as any);
    v$.value.$validate();

    return {
      customerTypeService,
      alertService,
      customerType,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.customerType.id) {
        this.customerTypeService()
          .update(this.customerType)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.customerType.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.customerTypeService()
          .create(this.customerType)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.customerType.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
