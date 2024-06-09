/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfArtistDetails from './type-of-artist-details.vue';
import TypeOfArtistService from './type-of-artist.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfArtistDetailsComponentType = InstanceType<typeof TypeOfArtistDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfArtistSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfArtist Management Detail Component', () => {
    let typeOfArtistServiceStub: SinonStubbedInstance<TypeOfArtistService>;
    let mountOptions: MountingOptions<TypeOfArtistDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfArtistServiceStub = sinon.createStubInstance<TypeOfArtistService>(TypeOfArtistService);

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
          typeOfArtistService: () => typeOfArtistServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfArtistServiceStub.find.resolves(typeOfArtistSample);
        route = {
          params: {
            typeOfArtistId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfArtistDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfArtist).toMatchObject(typeOfArtistSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfArtistServiceStub.find.resolves(typeOfArtistSample);
        const wrapper = shallowMount(TypeOfArtistDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
