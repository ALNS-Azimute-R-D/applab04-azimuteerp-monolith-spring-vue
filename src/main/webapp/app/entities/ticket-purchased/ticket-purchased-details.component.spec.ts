/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TicketPurchasedDetails from './ticket-purchased-details.vue';
import TicketPurchasedService from './ticket-purchased.service';
import AlertService from '@/shared/alert/alert.service';

type TicketPurchasedDetailsComponentType = InstanceType<typeof TicketPurchasedDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const ticketPurchasedSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TicketPurchased Management Detail Component', () => {
    let ticketPurchasedServiceStub: SinonStubbedInstance<TicketPurchasedService>;
    let mountOptions: MountingOptions<TicketPurchasedDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      ticketPurchasedServiceStub = sinon.createStubInstance<TicketPurchasedService>(TicketPurchasedService);

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
          ticketPurchasedService: () => ticketPurchasedServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        ticketPurchasedServiceStub.find.resolves(ticketPurchasedSample);
        route = {
          params: {
            ticketPurchasedId: '' + 123,
          },
        };
        const wrapper = shallowMount(TicketPurchasedDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.ticketPurchased).toMatchObject(ticketPurchasedSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        ticketPurchasedServiceStub.find.resolves(ticketPurchasedSample);
        const wrapper = shallowMount(TicketPurchasedDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
