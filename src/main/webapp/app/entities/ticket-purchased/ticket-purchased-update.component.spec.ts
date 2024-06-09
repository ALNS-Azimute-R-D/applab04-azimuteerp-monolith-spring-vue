/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import TicketPurchasedUpdate from './ticket-purchased-update.vue';
import TicketPurchasedService from './ticket-purchased.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import EventService from '@/entities/event/event.service';
import InvoiceService from '@/entities/invoice/invoice.service';

type TicketPurchasedUpdateComponentType = InstanceType<typeof TicketPurchasedUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const ticketPurchasedSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TicketPurchasedUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TicketPurchased Management Update Component', () => {
    let comp: TicketPurchasedUpdateComponentType;
    let ticketPurchasedServiceStub: SinonStubbedInstance<TicketPurchasedService>;

    beforeEach(() => {
      route = {};
      ticketPurchasedServiceStub = sinon.createStubInstance<TicketPurchasedService>(TicketPurchasedService);
      ticketPurchasedServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          ticketPurchasedService: () => ticketPurchasedServiceStub,
          eventService: () =>
            sinon.createStubInstance<EventService>(EventService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          invoiceService: () =>
            sinon.createStubInstance<InvoiceService>(InvoiceService, {
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
        const wrapper = shallowMount(TicketPurchasedUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(TicketPurchasedUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.ticketPurchased = ticketPurchasedSample;
        ticketPurchasedServiceStub.update.resolves(ticketPurchasedSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ticketPurchasedServiceStub.update.calledWith(ticketPurchasedSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        ticketPurchasedServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TicketPurchasedUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.ticketPurchased = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ticketPurchasedServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        ticketPurchasedServiceStub.find.resolves(ticketPurchasedSample);
        ticketPurchasedServiceStub.retrieve.resolves([ticketPurchasedSample]);

        // WHEN
        route = {
          params: {
            ticketPurchasedId: '' + ticketPurchasedSample.id,
          },
        };
        const wrapper = shallowMount(TicketPurchasedUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.ticketPurchased).toMatchObject(ticketPurchasedSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        ticketPurchasedServiceStub.find.resolves(ticketPurchasedSample);
        const wrapper = shallowMount(TicketPurchasedUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
