/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtisticGenreDetails from './artistic-genre-details.vue';
import ArtisticGenreService from './artistic-genre.service';
import AlertService from '@/shared/alert/alert.service';

type ArtisticGenreDetailsComponentType = InstanceType<typeof ArtisticGenreDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artisticGenreSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ArtisticGenre Management Detail Component', () => {
    let artisticGenreServiceStub: SinonStubbedInstance<ArtisticGenreService>;
    let mountOptions: MountingOptions<ArtisticGenreDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      artisticGenreServiceStub = sinon.createStubInstance<ArtisticGenreService>(ArtisticGenreService);

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
          artisticGenreService: () => artisticGenreServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        artisticGenreServiceStub.find.resolves(artisticGenreSample);
        route = {
          params: {
            artisticGenreId: '' + 123,
          },
        };
        const wrapper = shallowMount(ArtisticGenreDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.artisticGenre).toMatchObject(artisticGenreSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artisticGenreServiceStub.find.resolves(artisticGenreSample);
        const wrapper = shallowMount(ArtisticGenreDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
