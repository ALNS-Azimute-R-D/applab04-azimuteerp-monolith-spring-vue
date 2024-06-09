import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TownCityService from './town-city.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProvinceService from '@/entities/province/province.service';
import { type IProvince } from '@/shared/model/province.model';
import { type ITownCity, TownCity } from '@/shared/model/town-city.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TownCityUpdate',
  setup() {
    const townCityService = inject('townCityService', () => new TownCityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const townCity: Ref<ITownCity> = ref(new TownCity());

    const provinceService = inject('provinceService', () => new ProvinceService());

    const provinces: Ref<IProvince[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTownCity = async townCityId => {
      try {
        const res = await townCityService().find(townCityId);
        townCity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.townCityId) {
      retrieveTownCity(route.params.townCityId);
    }

    const initRelationships = () => {
      provinceService()
        .retrieve()
        .then(res => {
          provinces.value = res.data;
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
      province: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
    };
    const v$ = useVuelidate(validationRules, townCity as any);
    v$.value.$validate();

    return {
      townCityService,
      alertService,
      townCity,
      previousState,
      isSaving,
      currentLanguage,
      provinces,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.townCity.id) {
        this.townCityService()
          .update(this.townCity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.townCity.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.townCityService()
          .create(this.townCity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.townCity.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
