import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CommonLocalityService from './common-locality.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import DistrictService from '@/entities/district/district.service';
import { type IDistrict } from '@/shared/model/district.model';
import { type ICommonLocality, CommonLocality } from '@/shared/model/common-locality.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CommonLocalityUpdate',
  setup() {
    const commonLocalityService = inject('commonLocalityService', () => new CommonLocalityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const commonLocality: Ref<ICommonLocality> = ref(new CommonLocality());

    const districtService = inject('districtService', () => new DistrictService());

    const districts: Ref<IDistrict[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCommonLocality = async commonLocalityId => {
      try {
        const res = await commonLocalityService().find(commonLocalityId);
        commonLocality.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.commonLocalityId) {
      retrieveCommonLocality(route.params.commonLocalityId);
    }

    const initRelationships = () => {
      districtService()
        .retrieve()
        .then(res => {
          districts.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 20 }).toString(), 20),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 840 }).toString(), 840),
      },
      description: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
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
      geoPolygonArea: {},
      district: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, commonLocality as any);
    v$.value.$validate();

    return {
      commonLocalityService,
      alertService,
      commonLocality,
      previousState,
      isSaving,
      currentLanguage,
      districts,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.commonLocality.id) {
        this.commonLocalityService()
          .update(this.commonLocality)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.commonLocality.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.commonLocalityService()
          .create(this.commonLocality)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.commonLocality.created', { param: param.id }).toString(),
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
