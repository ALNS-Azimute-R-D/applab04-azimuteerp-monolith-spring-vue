import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DistrictService from './district.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TownCityService from '@/entities/town-city/town-city.service';
import { type ITownCity } from '@/shared/model/town-city.model';
import { type IDistrict, District } from '@/shared/model/district.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DistrictUpdate',
  setup() {
    const districtService = inject('districtService', () => new DistrictService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const district: Ref<IDistrict> = ref(new District());

    const townCityService = inject('townCityService', () => new TownCityService());

    const townCities: Ref<ITownCity[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveDistrict = async districtId => {
      try {
        const res = await districtService().find(districtId);
        district.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.districtId) {
      retrieveDistrict(route.params.districtId);
    }

    const initRelationships = () => {
      townCityService()
        .retrieve()
        .then(res => {
          townCities.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      acronym: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 8 }).toString(), 8),
      },
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 40 }).toString(), 40),
      },
      geoPolygonArea: {},
      townCity: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, district as any);
    v$.value.$validate();

    return {
      districtService,
      alertService,
      district,
      previousState,
      isSaving,
      currentLanguage,
      townCities,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.district.id) {
        this.districtService()
          .update(this.district)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.district.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.districtService()
          .create(this.district)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.district.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
