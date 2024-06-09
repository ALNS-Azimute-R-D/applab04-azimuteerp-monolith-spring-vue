/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentGatewayUpdate from './payment-gateway-update.vue';
import PaymentGatewayService from './payment-gateway.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentGatewayUpdateComponentType = InstanceType<typeof PaymentGatewayUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentGatewaySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PaymentGatewayUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PaymentGateway Management Update Component', () => {
    let comp: PaymentGatewayUpdateComponentType;
    let paymentGatewayServiceStub: SinonStubbedInstance<PaymentGatewayService>;

    beforeEach(() => {
      route = {};
      paymentGatewayServiceStub = sinon.createStubInstance<PaymentGatewayService>(PaymentGatewayService);
      paymentGatewayServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          paymentGatewayService: () => paymentGatewayServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PaymentGatewayUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentGateway = paymentGatewaySample;
        paymentGatewayServiceStub.update.resolves(paymentGatewaySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentGatewayServiceStub.update.calledWith(paymentGatewaySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        paymentGatewayServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PaymentGatewayUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentGateway = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentGatewayServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        paymentGatewayServiceStub.find.resolves(paymentGatewaySample);
        paymentGatewayServiceStub.retrieve.resolves([paymentGatewaySample]);

        // WHEN
        route = {
          params: {
            paymentGatewayId: '' + paymentGatewaySample.id,
          },
        };
        const wrapper = shallowMount(PaymentGatewayUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.paymentGateway).toMatchObject(paymentGatewaySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentGatewayServiceStub.find.resolves(paymentGatewaySample);
        const wrapper = shallowMount(PaymentGatewayUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
