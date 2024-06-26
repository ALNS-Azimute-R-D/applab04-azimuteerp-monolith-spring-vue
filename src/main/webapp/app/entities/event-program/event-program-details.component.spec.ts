/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EventProgramDetails from './event-program-details.vue';
import EventProgramService from './event-program.service';
import AlertService from '@/shared/alert/alert.service';

type EventProgramDetailsComponentType = InstanceType<typeof EventProgramDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const eventProgramSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('EventProgram Management Detail Component', () => {
    let eventProgramServiceStub: SinonStubbedInstance<EventProgramService>;
    let mountOptions: MountingOptions<EventProgramDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      eventProgramServiceStub = sinon.createStubInstance<EventProgramService>(EventProgramService);

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
          eventProgramService: () => eventProgramServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        eventProgramServiceStub.find.resolves(eventProgramSample);
        route = {
          params: {
            eventProgramId: '' + 123,
          },
        };
        const wrapper = shallowMount(EventProgramDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.eventProgram).toMatchObject(eventProgramSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        eventProgramServiceStub.find.resolves(eventProgramSample);
        const wrapper = shallowMount(EventProgramDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
