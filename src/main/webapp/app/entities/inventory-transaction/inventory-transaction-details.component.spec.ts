/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import InventoryTransactionDetails from './inventory-transaction-details.vue';
import InventoryTransactionService from './inventory-transaction.service';
import AlertService from '@/shared/alert/alert.service';

type InventoryTransactionDetailsComponentType = InstanceType<typeof InventoryTransactionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const inventoryTransactionSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('InventoryTransaction Management Detail Component', () => {
    let inventoryTransactionServiceStub: SinonStubbedInstance<InventoryTransactionService>;
    let mountOptions: MountingOptions<InventoryTransactionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      inventoryTransactionServiceStub = sinon.createStubInstance<InventoryTransactionService>(InventoryTransactionService);

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
          inventoryTransactionService: () => inventoryTransactionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        inventoryTransactionServiceStub.find.resolves(inventoryTransactionSample);
        route = {
          params: {
            inventoryTransactionId: '' + 123,
          },
        };
        const wrapper = shallowMount(InventoryTransactionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.inventoryTransaction).toMatchObject(inventoryTransactionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        inventoryTransactionServiceStub.find.resolves(inventoryTransactionSample);
        const wrapper = shallowMount(InventoryTransactionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
