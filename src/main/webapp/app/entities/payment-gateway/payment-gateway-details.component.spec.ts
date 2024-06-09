/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentGatewayDetails from './payment-gateway-details.vue';
import PaymentGatewayService from './payment-gateway.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentGatewayDetailsComponentType = InstanceType<typeof PaymentGatewayDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentGatewaySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PaymentGateway Management Detail Component', () => {
    let paymentGatewayServiceStub: SinonStubbedInstance<PaymentGatewayService>;
    let mountOptions: MountingOptions<PaymentGatewayDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      paymentGatewayServiceStub = sinon.createStubInstance<PaymentGatewayService>(PaymentGatewayService);

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
          paymentGatewayService: () => paymentGatewayServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentGatewayServiceStub.find.resolves(paymentGatewaySample);
        route = {
          params: {
            paymentGatewayId: '' + 123,
          },
        };
        const wrapper = shallowMount(PaymentGatewayDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.paymentGateway).toMatchObject(paymentGatewaySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentGatewayServiceStub.find.resolves(paymentGatewaySample);
        const wrapper = shallowMount(PaymentGatewayDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
