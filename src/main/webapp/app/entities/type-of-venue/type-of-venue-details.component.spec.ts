/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfVenueDetails from './type-of-venue-details.vue';
import TypeOfVenueService from './type-of-venue.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfVenueDetailsComponentType = InstanceType<typeof TypeOfVenueDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfVenueSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfVenue Management Detail Component', () => {
    let typeOfVenueServiceStub: SinonStubbedInstance<TypeOfVenueService>;
    let mountOptions: MountingOptions<TypeOfVenueDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfVenueServiceStub = sinon.createStubInstance<TypeOfVenueService>(TypeOfVenueService);

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
          typeOfVenueService: () => typeOfVenueServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfVenueServiceStub.find.resolves(typeOfVenueSample);
        route = {
          params: {
            typeOfVenueId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfVenueDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfVenue).toMatchObject(typeOfVenueSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfVenueServiceStub.find.resolves(typeOfVenueSample);
        const wrapper = shallowMount(TypeOfVenueDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
