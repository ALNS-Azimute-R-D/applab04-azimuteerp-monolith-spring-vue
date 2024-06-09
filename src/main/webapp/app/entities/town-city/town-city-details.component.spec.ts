/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TownCityDetails from './town-city-details.vue';
import TownCityService from './town-city.service';
import AlertService from '@/shared/alert/alert.service';

type TownCityDetailsComponentType = InstanceType<typeof TownCityDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const townCitySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TownCity Management Detail Component', () => {
    let townCityServiceStub: SinonStubbedInstance<TownCityService>;
    let mountOptions: MountingOptions<TownCityDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      townCityServiceStub = sinon.createStubInstance<TownCityService>(TownCityService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          townCityService: () => townCityServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        townCityServiceStub.find.resolves(townCitySample);
        route = {
          params: {
            townCityId: '' + 123,
          },
        };
        const wrapper = shallowMount(TownCityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.townCity).toMatchObject(townCitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        townCityServiceStub.find.resolves(townCitySample);
        const wrapper = shallowMount(TownCityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
