import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TypeOfVenueService from './type-of-venue.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITypeOfVenue, TypeOfVenue } from '@/shared/model/type-of-venue.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfVenueUpdate',
  setup() {
    const typeOfVenueService = inject('typeOfVenueService', () => new TypeOfVenueService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const typeOfVenue: Ref<ITypeOfVenue> = ref(new TypeOfVenue());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTypeOfVenue = async typeOfVenueId => {
      try {
        const res = await typeOfVenueService().find(typeOfVenueId);
        typeOfVenue.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfVenueId) {
      retrieveTypeOfVenue(route.params.typeOfVenueId);
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
    const v$ = useVuelidate(validationRules, typeOfVenue as any);
    v$.value.$validate();

    return {
      typeOfVenueService,
      alertService,
      typeOfVenue,
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
      if (this.typeOfVenue.id) {
        this.typeOfVenueService()
          .update(this.typeOfVenue)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.typeOfVenue.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.typeOfVenueService()
          .create(this.typeOfVenue)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.typeOfVenue.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
