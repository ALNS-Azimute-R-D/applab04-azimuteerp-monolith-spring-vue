import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OrganizationAttributeService from './organization-attribute.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';
import { type IOrganization } from '@/shared/model/organization.model';
import { type IOrganizationAttribute, OrganizationAttribute } from '@/shared/model/organization-attribute.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OrganizationAttributeUpdate',
  setup() {
    const organizationAttributeService = inject('organizationAttributeService', () => new OrganizationAttributeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const organizationAttribute: Ref<IOrganizationAttribute> = ref(new OrganizationAttribute());

    const organizationService = inject('organizationService', () => new OrganizationService());

    const organizations: Ref<IOrganization[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOrganizationAttribute = async organizationAttributeId => {
      try {
        const res = await organizationAttributeService().find(organizationAttributeId);
        organizationAttribute.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.organizationAttributeId) {
      retrieveOrganizationAttribute(route.params.organizationAttributeId);
    }

    const initRelationships = () => {
      organizationService()
        .retrieve()
        .then(res => {
          organizations.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      attributeName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 255 }).toString(), 255),
      },
      attributeValue: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      organization: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, organizationAttribute as any);
    v$.value.$validate();

    return {
      organizationAttributeService,
      alertService,
      organizationAttribute,
      previousState,
      isSaving,
      currentLanguage,
      organizations,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.organizationAttribute.id) {
        this.organizationAttributeService()
          .update(this.organizationAttribute)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.organizationAttribute.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.organizationAttributeService()
          .create(this.organizationAttribute)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.organizationAttribute.created', { param: param.id }).toString(),
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
