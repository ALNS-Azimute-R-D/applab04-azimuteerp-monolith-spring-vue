/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtworkDetails from './artwork-details.vue';
import ArtworkService from './artwork.service';
import AlertService from '@/shared/alert/alert.service';

type ArtworkDetailsComponentType = InstanceType<typeof ArtworkDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artworkSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Artwork Management Detail Component', () => {
    let artworkServiceStub: SinonStubbedInstance<ArtworkService>;
    let mountOptions: MountingOptions<ArtworkDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      artworkServiceStub = sinon.createStubInstance<ArtworkService>(ArtworkService);

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
          artworkService: () => artworkServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        artworkServiceStub.find.resolves(artworkSample);
        route = {
          params: {
            artworkId: '' + 123,
          },
        };
        const wrapper = shallowMount(ArtworkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.artwork).toMatchObject(artworkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artworkServiceStub.find.resolves(artworkSample);
        const wrapper = shallowMount(ArtworkDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
