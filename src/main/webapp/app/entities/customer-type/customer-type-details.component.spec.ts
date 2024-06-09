/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CustomerTypeDetails from './customer-type-details.vue';
import CustomerTypeService from './customer-type.service';
import AlertService from '@/shared/alert/alert.service';

type CustomerTypeDetailsComponentType = InstanceType<typeof CustomerTypeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const customerTypeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('CustomerType Management Detail Component', () => {
    let customerTypeServiceStub: SinonStubbedInstance<CustomerTypeService>;
    let mountOptions: MountingOptions<CustomerTypeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      customerTypeServiceStub = sinon.createStubInstance<CustomerTypeService>(CustomerTypeService);

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
          customerTypeService: () => customerTypeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        customerTypeServiceStub.find.resolves(customerTypeSample);
        route = {
          params: {
            customerTypeId: '' + 123,
          },
        };
        const wrapper = shallowMount(CustomerTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.customerType).toMatchObject(customerTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        customerTypeServiceStub.find.resolves(customerTypeSample);
        const wrapper = shallowMount(CustomerTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
