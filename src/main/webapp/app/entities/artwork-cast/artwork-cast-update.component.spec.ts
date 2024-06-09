/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtworkCastUpdate from './artwork-cast-update.vue';
import ArtworkCastService from './artwork-cast.service';
import AlertService from '@/shared/alert/alert.service';

import ArtworkService from '@/entities/artwork/artwork.service';
import ArtistService from '@/entities/artist/artist.service';

type ArtworkCastUpdateComponentType = InstanceType<typeof ArtworkCastUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artworkCastSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ArtworkCastUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ArtworkCast Management Update Component', () => {
    let comp: ArtworkCastUpdateComponentType;
    let artworkCastServiceStub: SinonStubbedInstance<ArtworkCastService>;

    beforeEach(() => {
      route = {};
      artworkCastServiceStub = sinon.createStubInstance<ArtworkCastService>(ArtworkCastService);
      artworkCastServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          artworkCastService: () => artworkCastServiceStub,
          artworkService: () =>
            sinon.createStubInstance<ArtworkService>(ArtworkService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
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
        const wrapper = shallowMount(ArtworkCastUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artworkCast = artworkCastSample;
        artworkCastServiceStub.update.resolves(artworkCastSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artworkCastServiceStub.update.calledWith(artworkCastSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        artworkCastServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ArtworkCastUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artworkCast = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artworkCastServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        artworkCastServiceStub.find.resolves(artworkCastSample);
        artworkCastServiceStub.retrieve.resolves([artworkCastSample]);

        // WHEN
        route = {
          params: {
            artworkCastId: '' + artworkCastSample.id,
          },
        };
        const wrapper = shallowMount(ArtworkCastUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.artworkCast).toMatchObject(artworkCastSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artworkCastServiceStub.find.resolves(artworkCastSample);
        const wrapper = shallowMount(ArtworkCastUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
