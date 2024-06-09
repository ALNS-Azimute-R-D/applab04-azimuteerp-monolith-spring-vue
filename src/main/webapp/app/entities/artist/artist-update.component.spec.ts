/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtistUpdate from './artist-update.vue';
import ArtistService from './artist.service';
import AlertService from '@/shared/alert/alert.service';

import TypeOfArtmediaService from '@/entities/type-of-artmedia/type-of-artmedia.service';
import TypeOfArtistService from '@/entities/type-of-artist/type-of-artist.service';
import ArtisticGenreService from '@/entities/artistic-genre/artistic-genre.service';

type ArtistUpdateComponentType = InstanceType<typeof ArtistUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artistSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ArtistUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Artist Management Update Component', () => {
    let comp: ArtistUpdateComponentType;
    let artistServiceStub: SinonStubbedInstance<ArtistService>;

    beforeEach(() => {
      route = {};
      artistServiceStub = sinon.createStubInstance<ArtistService>(ArtistService);
      artistServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          artistService: () => artistServiceStub,
          typeOfArtmediaService: () =>
            sinon.createStubInstance<TypeOfArtmediaService>(TypeOfArtmediaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          typeOfArtistService: () =>
            sinon.createStubInstance<TypeOfArtistService>(TypeOfArtistService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          artisticGenreService: () =>
            sinon.createStubInstance<ArtisticGenreService>(ArtisticGenreService, {
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
        const wrapper = shallowMount(ArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artist = artistSample;
        artistServiceStub.update.resolves(artistSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artistServiceStub.update.calledWith(artistSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        artistServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artist = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artistServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        artistServiceStub.find.resolves(artistSample);
        artistServiceStub.retrieve.resolves([artistSample]);

        // WHEN
        route = {
          params: {
            artistId: '' + artistSample.id,
          },
        };
        const wrapper = shallowMount(ArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.artist).toMatchObject(artistSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artistServiceStub.find.resolves(artistSample);
        const wrapper = shallowMount(ArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
