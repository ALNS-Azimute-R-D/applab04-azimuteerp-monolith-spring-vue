/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ScheduledActivityDetails from './scheduled-activity-details.vue';
import ScheduledActivityService from './scheduled-activity.service';
import AlertService from '@/shared/alert/alert.service';

type ScheduledActivityDetailsComponentType = InstanceType<typeof ScheduledActivityDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const scheduledActivitySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ScheduledActivity Management Detail Component', () => {
    let scheduledActivityServiceStub: SinonStubbedInstance<ScheduledActivityService>;
    let mountOptions: MountingOptions<ScheduledActivityDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      scheduledActivityServiceStub = sinon.createStubInstance<ScheduledActivityService>(ScheduledActivityService);

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
          scheduledActivityService: () => scheduledActivityServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        scheduledActivityServiceStub.find.resolves(scheduledActivitySample);
        route = {
          params: {
            scheduledActivityId: '' + 123,
          },
        };
        const wrapper = shallowMount(ScheduledActivityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.scheduledActivity).toMatchObject(scheduledActivitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        scheduledActivityServiceStub.find.resolves(scheduledActivitySample);
        const wrapper = shallowMount(ScheduledActivityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
