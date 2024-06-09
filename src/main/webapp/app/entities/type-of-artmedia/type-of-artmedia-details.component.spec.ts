/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfArtmediaDetails from './type-of-artmedia-details.vue';
import TypeOfArtmediaService from './type-of-artmedia.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfArtmediaDetailsComponentType = InstanceType<typeof TypeOfArtmediaDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfArtmediaSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfArtmedia Management Detail Component', () => {
    let typeOfArtmediaServiceStub: SinonStubbedInstance<TypeOfArtmediaService>;
    let mountOptions: MountingOptions<TypeOfArtmediaDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfArtmediaServiceStub = sinon.createStubInstance<TypeOfArtmediaService>(TypeOfArtmediaService);

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
          typeOfArtmediaService: () => typeOfArtmediaServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfArtmediaServiceStub.find.resolves(typeOfArtmediaSample);
        route = {
          params: {
            typeOfArtmediaId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfArtmediaDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfArtmedia).toMatchObject(typeOfArtmediaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfArtmediaServiceStub.find.resolves(typeOfArtmediaSample);
        const wrapper = shallowMount(TypeOfArtmediaDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
