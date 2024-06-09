/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import StockLevelDetails from './stock-level-details.vue';
import StockLevelService from './stock-level.service';
import AlertService from '@/shared/alert/alert.service';

type StockLevelDetailsComponentType = InstanceType<typeof StockLevelDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stockLevelSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('StockLevel Management Detail Component', () => {
    let stockLevelServiceStub: SinonStubbedInstance<StockLevelService>;
    let mountOptions: MountingOptions<StockLevelDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      stockLevelServiceStub = sinon.createStubInstance<StockLevelService>(StockLevelService);

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
          stockLevelService: () => stockLevelServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        stockLevelServiceStub.find.resolves(stockLevelSample);
        route = {
          params: {
            stockLevelId: '' + 123,
          },
        };
        const wrapper = shallowMount(StockLevelDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.stockLevel).toMatchObject(stockLevelSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stockLevelServiceStub.find.resolves(stockLevelSample);
        const wrapper = shallowMount(StockLevelDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
