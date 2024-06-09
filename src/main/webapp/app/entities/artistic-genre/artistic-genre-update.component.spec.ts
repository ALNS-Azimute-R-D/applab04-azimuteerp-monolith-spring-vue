/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtisticGenreUpdate from './artistic-genre-update.vue';
import ArtisticGenreService from './artistic-genre.service';
import AlertService from '@/shared/alert/alert.service';

import ArtistService from '@/entities/artist/artist.service';

type ArtisticGenreUpdateComponentType = InstanceType<typeof ArtisticGenreUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artisticGenreSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ArtisticGenreUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ArtisticGenre Management Update Component', () => {
    let comp: ArtisticGenreUpdateComponentType;
    let artisticGenreServiceStub: SinonStubbedInstance<ArtisticGenreService>;

    beforeEach(() => {
      route = {};
      artisticGenreServiceStub = sinon.createStubInstance<ArtisticGenreService>(ArtisticGenreService);
      artisticGenreServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          artisticGenreService: () => artisticGenreServiceStub,
          artistService: () =>
            sinon.createStubInstance<ArtistService>(ArtistService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ArtisticGenreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artisticGenre = artisticGenreSample;
        artisticGenreServiceStub.update.resolves(artisticGenreSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artisticGenreServiceStub.update.calledWith(artisticGenreSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        artisticGenreServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ArtisticGenreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artisticGenre = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artisticGenreServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        artisticGenreServiceStub.find.resolves(artisticGenreSample);
        artisticGenreServiceStub.retrieve.resolves([artisticGenreSample]);

        // WHEN
        route = {
          params: {
            artisticGenreId: '' + artisticGenreSample.id,
          },
        };
        const wrapper = shallowMount(ArtisticGenreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.artisticGenre).toMatchObject(artisticGenreSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artisticGenreServiceStub.find.resolves(artisticGenreSample);
        const wrapper = shallowMount(ArtisticGenreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
