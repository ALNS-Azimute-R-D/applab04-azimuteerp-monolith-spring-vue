import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import SupplierService from './supplier.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import ProductService from '@/entities/product/product.service';
import { type IProduct } from '@/shared/model/product.model';
import { type ISupplier, Supplier } from '@/shared/model/supplier.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SupplierUpdate',
  setup() {
    const supplierService = inject('supplierService', () => new SupplierService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const supplier: Ref<ISupplier> = ref(new Supplier());

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);

    const productService = inject('productService', () => new ProductService());

    const products: Ref<IProduct[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveSupplier = async supplierId => {
      try {
        const res = await supplierService().find(supplierId);
        supplier.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.supplierId) {
      retrieveSupplier(route.params.supplierId);
    }

    const initRelationships = () => {
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      companyName: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      streetAddress: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      houseNumber: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      locationName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      city: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      stateProvince: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      zipPostalCode: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      countryRegion: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      pointLocation: {},
      mainEmail: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      phoneNumber1: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      phoneNumber2: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      representativePerson: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      toProducts: {},
    };
    const v$ = useVuelidate(validationRules, supplier as any);
    v$.value.$validate();

    return {
      supplierService,
      alertService,
      supplier,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      people,
      products,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {
    this.supplier.toProducts = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.supplier.id) {
        this.supplierService()
          .update(this.supplier)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.supplier.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.supplierService()
          .create(this.supplier)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.supplier.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
