import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TypeOfOrganizationService from './type-of-organization.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITypeOfOrganization, TypeOfOrganization } from '@/shared/model/type-of-organization.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeOfOrganizationUpdate',
  setup() {
    const typeOfOrganizationService = inject('typeOfOrganizationService', () => new TypeOfOrganizationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const typeOfOrganization: Ref<ITypeOfOrganization> = ref(new TypeOfOrganization());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTypeOfOrganization = async typeOfOrganizationId => {
      try {
        const res = await typeOfOrganizationService().find(typeOfOrganizationId);
        typeOfOrganization.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeOfOrganizationId) {
      retrieveTypeOfOrganization(route.params.typeOfOrganizationId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      description: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 128 }).toString(), 128),
      },
      businessHandlerClazz: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
    };
    const v$ = useVuelidate(validationRules, typeOfOrganization as any);
    v$.value.$validate();

    return {
      typeOfOrganizationService,
      alertService,
      typeOfOrganization,
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
      if (this.typeOfOrganization.id) {
        this.typeOfOrganizationService()
          .update(this.typeOfOrganization)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.typeOfOrganizationService()
          .create(this.typeOfOrganization)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.created', { param: param.id }).toString(),
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
