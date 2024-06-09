/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtworkCastDetails from './artwork-cast-details.vue';
import ArtworkCastService from './artwork-cast.service';
import AlertService from '@/shared/alert/alert.service';

type ArtworkCastDetailsComponentType = InstanceType<typeof ArtworkCastDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artworkCastSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ArtworkCast Management Detail Component', () => {
    let artworkCastServiceStub: SinonStubbedInstance<ArtworkCastService>;
    let mountOptions: MountingOptions<ArtworkCastDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      artworkCastServiceStub = sinon.createStubInstance<ArtworkCastService>(ArtworkCastService);

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
          artworkCastService: () => artworkCastServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        artworkCastServiceStub.find.resolves(artworkCastSample);
        route = {
          params: {
            artworkCastId: '' + 123,
          },
        };
        const wrapper = shallowMount(ArtworkCastDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.artworkCast).toMatchObject(artworkCastSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artworkCastServiceStub.find.resolves(artworkCastSample);
        const wrapper = shallowMount(ArtworkCastDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
