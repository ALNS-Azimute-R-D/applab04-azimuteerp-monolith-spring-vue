/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EventAttendeeDetails from './event-attendee-details.vue';
import EventAttendeeService from './event-attendee.service';
import AlertService from '@/shared/alert/alert.service';

type EventAttendeeDetailsComponentType = InstanceType<typeof EventAttendeeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const eventAttendeeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('EventAttendee Management Detail Component', () => {
    let eventAttendeeServiceStub: SinonStubbedInstance<EventAttendeeService>;
    let mountOptions: MountingOptions<EventAttendeeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      eventAttendeeServiceStub = sinon.createStubInstance<EventAttendeeService>(EventAttendeeService);

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
          eventAttendeeService: () => eventAttendeeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        eventAttendeeServiceStub.find.resolves(eventAttendeeSample);
        route = {
          params: {
            eventAttendeeId: '' + 123,
          },
        };
        const wrapper = shallowMount(EventAttendeeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.eventAttendee).toMatchObject(eventAttendeeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        eventAttendeeServiceStub.find.resolves(eventAttendeeSample);
        const wrapper = shallowMount(EventAttendeeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
