import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CustomerService from './customer.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import CustomerTypeService from '@/entities/customer-type/customer-type.service';
import { type ICustomerType } from '@/shared/model/customer-type.model';
import DistrictService from '@/entities/district/district.service';
import { type IDistrict } from '@/shared/model/district.model';
import { type ICustomer, Customer } from '@/shared/model/customer.model';
import { CustomerStatusEnum } from '@/shared/model/enumerations/customer-status-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CustomerUpdate',
  setup() {
    const customerService = inject('customerService', () => new CustomerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const customer: Ref<ICustomer> = ref(new Customer());

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);

    const customerTypeService = inject('customerTypeService', () => new CustomerTypeService());

    const customerTypes: Ref<ICustomerType[]> = ref([]);

    const districtService = inject('districtService', () => new DistrictService());

    const districts: Ref<IDistrict[]> = ref([]);
    const customerStatusEnumValues: Ref<string[]> = ref(Object.keys(CustomerStatusEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCustomer = async customerId => {
      try {
        const res = await customerService().find(customerId);
        customer.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.customerId) {
      retrieveCustomer(route.params.customerId);
    }

    const initRelationships = () => {
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
      customerTypeService()
        .retrieve()
        .then(res => {
          customerTypes.value = res.data;
        });
      districtService()
        .retrieve()
        .then(res => {
          districts.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      customerBusinessCode: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      fullname: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 80 }).toString(), 80),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      customerStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      buyerPerson: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      customerType: {},
      district: {},
    };
    const v$ = useVuelidate(validationRules, customer as any);
    v$.value.$validate();

    return {
      customerService,
      alertService,
      customer,
      previousState,
      customerStatusEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      people,
      customerTypes,
      districts,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.customer.id) {
        this.customerService()
          .update(this.customer)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.customer.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.customerService()
          .create(this.customer)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.customer.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
