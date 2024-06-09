/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import WarehouseDetails from './warehouse-details.vue';
import WarehouseService from './warehouse.service';
import AlertService from '@/shared/alert/alert.service';

type WarehouseDetailsComponentType = InstanceType<typeof WarehouseDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const warehouseSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Warehouse Management Detail Component', () => {
    let warehouseServiceStub: SinonStubbedInstance<WarehouseService>;
    let mountOptions: MountingOptions<WarehouseDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      warehouseServiceStub = sinon.createStubInstance<WarehouseService>(WarehouseService);

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
          warehouseService: () => warehouseServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        warehouseServiceStub.find.resolves(warehouseSample);
        route = {
          params: {
            warehouseId: '' + 123,
          },
        };
        const wrapper = shallowMount(WarehouseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.warehouse).toMatchObject(warehouseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        warehouseServiceStub.find.resolves(warehouseSample);
        const wrapper = shallowMount(WarehouseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
