import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TypeOfArtistService from './type-of-artist.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITypeOfArtist, TypeOfArtist } from '@/shared/model/type-of-artist.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfArtistUpdate',
  setup() {
    const typeOfArtistService = inject('typeOfArtistService', () => new TypeOfArtistService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const typeOfArtist: Ref<ITypeOfArtist> = ref(new TypeOfArtist());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTypeOfArtist = async typeOfArtistId => {
      try {
        const res = await typeOfArtistService().find(typeOfArtistId);
        typeOfArtist.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfArtistId) {
      retrieveTypeOfArtist(route.params.typeOfArtistId);
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
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
    };
    const v$ = useVuelidate(validationRules, typeOfArtist as any);
    v$.value.$validate();

    return {
      typeOfArtistService,
      alertService,
      typeOfArtist,
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
      if (this.typeOfArtist.id) {
        this.typeOfArtistService()
          .update(this.typeOfArtist)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.typeOfArtist.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.typeOfArtistService()
          .create(this.typeOfArtist)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.typeOfArtist.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
