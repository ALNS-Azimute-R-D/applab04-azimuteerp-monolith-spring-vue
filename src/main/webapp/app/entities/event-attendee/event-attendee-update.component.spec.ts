/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EventAttendeeUpdate from './event-attendee-update.vue';
import EventAttendeeService from './event-attendee.service';
import AlertService from '@/shared/alert/alert.service';

import PersonService from '@/entities/person/person.service';
import EventService from '@/entities/event/event.service';
import TicketPurchasedService from '@/entities/ticket-purchased/ticket-purchased.service';

type EventAttendeeUpdateComponentType = InstanceType<typeof EventAttendeeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const eventAttendeeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EventAttendeeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EventAttendee Management Update Component', () => {
    let comp: EventAttendeeUpdateComponentType;
    let eventAttendeeServiceStub: SinonStubbedInstance<EventAttendeeService>;

    beforeEach(() => {
      route = {};
      eventAttendeeServiceStub = sinon.createStubInstance<EventAttendeeService>(EventAttendeeService);
      eventAttendeeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          eventAttendeeService: () => eventAttendeeServiceStub,
          personService: () =>
            sinon.createStubInstance<PersonService>(PersonService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          eventService: () =>
            sinon.createStubInstance<EventService>(EventService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          ticketPurchasedService: () =>
            sinon.createStubInstance<TicketPurchasedService>(TicketPurchasedService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EventAttendeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.eventAttendee = eventAttendeeSample;
        eventAttendeeServiceStub.update.resolves(eventAttendeeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eventAttendeeServiceStub.update.calledWith(eventAttendeeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        eventAttendeeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EventAttendeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.eventAttendee = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eventAttendeeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        eventAttendeeServiceStub.find.resolves(eventAttendeeSample);
        eventAttendeeServiceStub.retrieve.resolves([eventAttendeeSample]);

        // WHEN
        route = {
          params: {
            eventAttendeeId: '' + eventAttendeeSample.id,
          },
        };
        const wrapper = shallowMount(EventAttendeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.eventAttendee).toMatchObject(eventAttendeeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        eventAttendeeServiceStub.find.resolves(eventAttendeeSample);
        const wrapper = shallowMount(EventAttendeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
