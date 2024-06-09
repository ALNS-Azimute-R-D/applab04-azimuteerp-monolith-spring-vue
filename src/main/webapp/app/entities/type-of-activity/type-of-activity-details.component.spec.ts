/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfActivityDetails from './type-of-activity-details.vue';
import TypeOfActivityService from './type-of-activity.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfActivityDetailsComponentType = InstanceType<typeof TypeOfActivityDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfActivitySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfActivity Management Detail Component', () => {
    let typeOfActivityServiceStub: SinonStubbedInstance<TypeOfActivityService>;
    let mountOptions: MountingOptions<TypeOfActivityDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfActivityServiceStub = sinon.createStubInstance<TypeOfActivityService>(TypeOfActivityService);

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
          typeOfActivityService: () => typeOfActivityServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfActivityServiceStub.find.resolves(typeOfActivitySample);
        route = {
          params: {
            typeOfActivityId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfActivityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfActivity).toMatchObject(typeOfActivitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfActivityServiceStub.find.resolves(typeOfActivitySample);
        const wrapper = shallowMount(TypeOfActivityDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
