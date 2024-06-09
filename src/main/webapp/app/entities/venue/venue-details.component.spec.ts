/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import VenueDetails from './venue-details.vue';
import VenueService from './venue.service';
import AlertService from '@/shared/alert/alert.service';

type VenueDetailsComponentType = InstanceType<typeof VenueDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const venueSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Venue Management Detail Component', () => {
    let venueServiceStub: SinonStubbedInstance<VenueService>;
    let mountOptions: MountingOptions<VenueDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      venueServiceStub = sinon.createStubInstance<VenueService>(VenueService);

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
          venueService: () => venueServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        venueServiceStub.find.resolves(venueSample);
        route = {
          params: {
            venueId: '' + 123,
          },
        };
        const wrapper = shallowMount(VenueDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.venue).toMatchObject(venueSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        venueServiceStub.find.resolves(venueSample);
        const wrapper = shallowMount(VenueDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
