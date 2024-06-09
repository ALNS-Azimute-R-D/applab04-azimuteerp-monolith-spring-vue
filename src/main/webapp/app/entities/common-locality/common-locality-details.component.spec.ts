/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CommonLocalityDetails from './common-locality-details.vue';
import CommonLocalityService from './common-locality.service';
import AlertService from '@/shared/alert/alert.service';

type CommonLocalityDetailsComponentType = InstanceType<typeof CommonLocalityDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const commonLocalitySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CommonLocality Management Detail Component', () => {
    let commonLocalityServiceStub: SinonStubbedInstance<CommonLocalityService>;
    let mountOptions: MountingOptions<CommonLocalityDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      commonLocalityServiceStub = sinon.createStubInstance<CommonLocalityService>(CommonLocalityService);

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
          commonLocalityService: () => commonLocalityServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        commonLocalityServiceStub.find.resolves(commonLocalitySample);
        route = {
          params: {
            commonLocalityId: '' + 123,
          },
        };
        const wrapper = shallowMount(CommonLocalityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.commonLocality).toMatchObject(commonLocalitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        commonLocalityServiceStub.find.resolves(commonLocalitySample);
        const wrapper = shallowMount(CommonLocalityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
