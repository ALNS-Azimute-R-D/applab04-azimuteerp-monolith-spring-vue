/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import BusinessUnitDetails from './business-unit-details.vue';
import BusinessUnitService from './business-unit.service';
import AlertService from '@/shared/alert/alert.service';

type BusinessUnitDetailsComponentType = InstanceType<typeof BusinessUnitDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const businessUnitSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('BusinessUnit Management Detail Component', () => {
    let businessUnitServiceStub: SinonStubbedInstance<BusinessUnitService>;
    let mountOptions: MountingOptions<BusinessUnitDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      businessUnitServiceStub = sinon.createStubInstance<BusinessUnitService>(BusinessUnitService);

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
          businessUnitService: () => businessUnitServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        businessUnitServiceStub.find.resolves(businessUnitSample);
        route = {
          params: {
            businessUnitId: '' + 123,
          },
        };
        const wrapper = shallowMount(BusinessUnitDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.businessUnit).toMatchObject(businessUnitSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        businessUnitServiceStub.find.resolves(businessUnitSample);
        const wrapper = shallowMount(BusinessUnitDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
