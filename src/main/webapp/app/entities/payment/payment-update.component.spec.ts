/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import PaymentUpdate from './payment-update.vue';
import PaymentService from './payment.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import PaymentGatewayService from '@/entities/payment-gateway/payment-gateway.service';

type PaymentUpdateComponentType = InstanceType<typeof PaymentUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PaymentUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Payment Management Update Component', () => {
    let comp: PaymentUpdateComponentType;
    let paymentServiceStub: SinonStubbedInstance<PaymentService>;

    beforeEach(() => {
      route = {};
      paymentServiceStub = sinon.createStubInstance<PaymentService>(PaymentService);
      paymentServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          paymentService: () => paymentServiceStub,
          paymentGatewayService: () =>
            sinon.createStubInstance<PaymentGatewayService>(PaymentGatewayService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(PaymentUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PaymentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.payment = paymentSample;
        paymentServiceStub.update.resolves(paymentSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentServiceStub.update.calledWith(paymentSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        paymentServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PaymentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.payment = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        paymentServiceStub.find.resolves(paymentSample);
        paymentServiceStub.retrieve.resolves([paymentSample]);

        // WHEN
        route = {
          params: {
            paymentId: '' + paymentSample.id,
          },
        };
        const wrapper = shallowMount(PaymentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.payment).toMatchObject(paymentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentServiceStub.find.resolves(paymentSample);
        const wrapper = shallowMount(PaymentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
