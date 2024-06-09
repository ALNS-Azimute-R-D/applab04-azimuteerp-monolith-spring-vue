import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TypeOfPersonService from './type-of-person.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITypeOfPerson, TypeOfPerson } from '@/shared/model/type-of-person.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfPersonUpdate',
  setup() {
    const typeOfPersonService = inject('typeOfPersonService', () => new TypeOfPersonService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const typeOfPerson: Ref<ITypeOfPerson> = ref(new TypeOfPerson());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTypeOfPerson = async typeOfPersonId => {
      try {
        const res = await typeOfPersonService().find(typeOfPersonId);
        typeOfPerson.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfPersonId) {
      retrieveTypeOfPerson(route.params.typeOfPersonId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      code: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 5 }).toString(), 5),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 80 }).toString(), 80),
      },
    };
    const v$ = useVuelidate(validationRules, typeOfPerson as any);
    v$.value.$validate();

    return {
      typeOfPersonService,
      alertService,
      typeOfPerson,
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
      if (this.typeOfPerson.id) {
        this.typeOfPersonService()
          .update(this.typeOfPerson)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.typeOfPerson.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.typeOfPersonService()
          .create(this.typeOfPerson)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.typeOfPerson.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
