import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TypeOfActivityService from './type-of-activity.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITypeOfActivity, TypeOfActivity } from '@/shared/model/type-of-activity.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfActivityUpdate',
  setup() {
    const typeOfActivityService = inject('typeOfActivityService', () => new TypeOfActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const typeOfActivity: Ref<ITypeOfActivity> = ref(new TypeOfActivity());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTypeOfActivity = async typeOfActivityId => {
      try {
        const res = await typeOfActivityService().find(typeOfActivityId);
        typeOfActivity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfActivityId) {
      retrieveTypeOfActivity(route.params.typeOfActivityId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 30 }).toString(), 30),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 100 }).toString(), 100),
      },
      description: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      handlerClazzName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
    };
    const v$ = useVuelidate(validationRules, typeOfActivity as any);
    v$.value.$validate();

    return {
      typeOfActivityService,
      alertService,
      typeOfActivity,
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
      if (this.typeOfActivity.id) {
        this.typeOfActivityService()
          .update(this.typeOfActivity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.typeOfActivity.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.typeOfActivityService()
          .create(this.typeOfActivity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.typeOfActivity.created', { param: param.id }).toString(),
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
