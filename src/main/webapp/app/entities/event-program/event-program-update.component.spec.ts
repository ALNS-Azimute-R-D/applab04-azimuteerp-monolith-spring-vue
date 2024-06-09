/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EventProgramUpdate from './event-program-update.vue';
import EventProgramService from './event-program.service';
import AlertService from '@/shared/alert/alert.service';

import EventService from '@/entities/event/event.service';
import ProgramService from '@/entities/program/program.service';
import PersonService from '@/entities/person/person.service';

type EventProgramUpdateComponentType = InstanceType<typeof EventProgramUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const eventProgramSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EventProgramUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EventProgram Management Update Component', () => {
    let comp: EventProgramUpdateComponentType;
    let eventProgramServiceStub: SinonStubbedInstance<EventProgramService>;

    beforeEach(() => {
      route = {};
      eventProgramServiceStub = sinon.createStubInstance<EventProgramService>(EventProgramService);
      eventProgramServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          eventProgramService: () => eventProgramServiceStub,
          eventService: () =>
            sinon.createStubInstance<EventService>(EventService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          programService: () =>
            sinon.createStubInstance<ProgramService>(ProgramService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          personService: () =>
            sinon.createStubInstance<PersonService>(PersonService, {
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
        const wrapper = shallowMount(EventProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.eventProgram = eventProgramSample;
        eventProgramServiceStub.update.resolves(eventProgramSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eventProgramServiceStub.update.calledWith(eventProgramSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        eventProgramServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EventProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.eventProgram = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(eventProgramServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        eventProgramServiceStub.find.resolves(eventProgramSample);
        eventProgramServiceStub.retrieve.resolves([eventProgramSample]);

        // WHEN
        route = {
          params: {
            eventProgramId: '' + eventProgramSample.id,
          },
        };
        const wrapper = shallowMount(EventProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.eventProgram).toMatchObject(eventProgramSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        eventProgramServiceStub.find.resolves(eventProgramSample);
        const wrapper = shallowMount(EventProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
