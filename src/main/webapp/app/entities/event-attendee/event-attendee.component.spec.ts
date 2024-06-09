/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import EventAttendee from './event-attendee.vue';
import EventAttendeeService from './event-attendee.service';
import AlertService from '@/shared/alert/alert.service';

type EventAttendeeComponentType = InstanceType<typeof EventAttendee>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('EventAttendee Management Component', () => {
    let eventAttendeeServiceStub: SinonStubbedInstance<EventAttendeeService>;
    let mountOptions: MountingOptions<EventAttendeeComponentType>['global'];

    beforeEach(() => {
      eventAttendeeServiceStub = sinon.createStubInstance<EventAttendeeService>(EventAttendeeService);
      eventAttendeeServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          eventAttendeeService: () => eventAttendeeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        eventAttendeeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(EventAttendee, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(eventAttendeeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.eventAttendees[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(EventAttendee, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(eventAttendeeServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: EventAttendeeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(EventAttendee, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        eventAttendeeServiceStub.retrieve.reset();
        eventAttendeeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        eventAttendeeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(eventAttendeeServiceStub.retrieve.called).toBeTruthy();
        expect(comp.eventAttendees[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(eventAttendeeServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        eventAttendeeServiceStub.retrieve.reset();
        eventAttendeeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(eventAttendeeServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.eventAttendees[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(eventAttendeeServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        eventAttendeeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEventAttendee();
        await comp.$nextTick(); // clear components

        // THEN
        expect(eventAttendeeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(eventAttendeeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
