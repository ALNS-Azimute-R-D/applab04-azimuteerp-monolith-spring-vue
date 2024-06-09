/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfEventDetails from './type-of-event-details.vue';
import TypeOfEventService from './type-of-event.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfEventDetailsComponentType = InstanceType<typeof TypeOfEventDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfEventSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfEvent Management Detail Component', () => {
    let typeOfEventServiceStub: SinonStubbedInstance<TypeOfEventService>;
    let mountOptions: MountingOptions<TypeOfEventDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfEventServiceStub = sinon.createStubInstance<TypeOfEventService>(TypeOfEventService);

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
          typeOfEventService: () => typeOfEventServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfEventServiceStub.find.resolves(typeOfEventSample);
        route = {
          params: {
            typeOfEventId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfEventDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfEvent).toMatchObject(typeOfEventSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfEventServiceStub.find.resolves(typeOfEventSample);
        const wrapper = shallowMount(TypeOfEventDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
