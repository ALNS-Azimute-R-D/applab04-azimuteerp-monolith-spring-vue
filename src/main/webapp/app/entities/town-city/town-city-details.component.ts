import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TownCityService from './town-city.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type ITownCity } from '@/shared/model/town-city.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TownCityDetails',
  setup() {
    const townCityService = inject('townCityService', () => new TownCityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const townCity: Ref<ITownCity> = ref({});

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

    return {
      alertService,
      townCity,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
