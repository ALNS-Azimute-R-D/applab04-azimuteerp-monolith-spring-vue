import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TypeOfArtmediaService from './type-of-artmedia.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITypeOfArtmedia, TypeOfArtmedia } from '@/shared/model/type-of-artmedia.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfArtmediaUpdate',
  setup() {
    const typeOfArtmediaService = inject('typeOfArtmediaService', () => new TypeOfArtmediaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const typeOfArtmedia: Ref<ITypeOfArtmedia> = ref(new TypeOfArtmedia());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTypeOfArtmedia = async typeOfArtmediaId => {
      try {
        const res = await typeOfArtmediaService().find(typeOfArtmediaId);
        typeOfArtmedia.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfArtmediaId) {
      retrieveTypeOfArtmedia(route.params.typeOfArtmediaId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 50 }).toString(), 50),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        minLength: validations.minLength(t$('entity.validation.minlength', { min: 2 }).toString(), 2),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
    };
    const v$ = useVuelidate(validationRules, typeOfArtmedia as any);
    v$.value.$validate();

    return {
      typeOfArtmediaService,
      alertService,
      typeOfArtmedia,
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
      if (this.typeOfArtmedia.id) {
        this.typeOfArtmediaService()
          .update(this.typeOfArtmedia)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.typeOfArtmediaService()
          .create(this.typeOfArtmedia)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.created', { param: param.id }).toString(),
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
