import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PersonService from './person.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TypeOfPersonService from '@/entities/type-of-person/type-of-person.service';
import { type ITypeOfPerson } from '@/shared/model/type-of-person.model';
import DistrictService from '@/entities/district/district.service';
import { type IDistrict } from '@/shared/model/district.model';
import { type IPerson, Person } from '@/shared/model/person.model';
import { GenderEnum } from '@/shared/model/enumerations/gender-enum.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PersonUpdate',
  setup() {
    const personService = inject('personService', () => new PersonService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const person: Ref<IPerson> = ref(new Person());

    const typeOfPersonService = inject('typeOfPersonService', () => new TypeOfPersonService());

    const typeOfPeople: Ref<ITypeOfPerson[]> = ref([]);

    const districtService = inject('districtService', () => new DistrictService());

    const districts: Ref<IDistrict[]> = ref([]);

    const people: Ref<IPerson[]> = ref([]);
    const genderEnumValues: Ref<string[]> = ref(Object.keys(GenderEnum));
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePerson = async personId => {
      try {
        const res = await personService().find(personId);
        person.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.personId) {
      retrievePerson(route.params.personId);
    }

    const initRelationships = () => {
      typeOfPersonService()
        .retrieve()
        .then(res => {
          typeOfPeople.value = res.data;
        });
      districtService()
        .retrieve()
        .then(res => {
          districts.value = res.data;
        });
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      firstname: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      lastname: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      fullname: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      birthDate: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      gender: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      codeBI: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      codeNIF: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
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
      postalCode: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 9 }).toString(), 9),
      },
      mainEmail: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      landPhoneNumber: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      mobilePhoneNumber: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 15 }).toString(), 15),
      },
      occupation: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 40 }).toString(), 40),
      },
      preferredLanguage: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 5 }).toString(), 5),
      },
      usernameInOAuth2: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 40 }).toString(), 40),
      },
      userIdInOAuth2: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 80 }).toString(), 80),
      },
      customAttributesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      avatarImg: {},
      typeOfPerson: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      district: {},
      managerPerson: {},
    };
    const v$ = useVuelidate(validationRules, person as any);
    v$.value.$validate();

    return {
      personService,
      alertService,
      person,
      previousState,
      genderEnumValues,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      typeOfPeople,
      districts,
      people,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.person.id) {
        this.personService()
          .update(this.person)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.person.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.personService()
          .create(this.person)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.person.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    clearInputImage(field, fieldContentType, idInput): void {
      if (this.person && field && fieldContentType) {
        if (Object.prototype.hasOwnProperty.call(this.person, field)) {
          this.person[field] = null;
        }
        if (Object.prototype.hasOwnProperty.call(this.person, fieldContentType)) {
          this.person[fieldContentType] = null;
        }
        if (idInput) {
          (<any>this).$refs[idInput] = null;
        }
      }
    },
  },
});
