/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ArtworkUpdate from './artwork-update.vue';
import ArtworkService from './artwork.service';
import AlertService from '@/shared/alert/alert.service';

import TypeOfArtmediaService from '@/entities/type-of-artmedia/type-of-artmedia.service';

type ArtworkUpdateComponentType = InstanceType<typeof ArtworkUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const artworkSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ArtworkUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Artwork Management Update Component', () => {
    let comp: ArtworkUpdateComponentType;
    let artworkServiceStub: SinonStubbedInstance<ArtworkService>;

    beforeEach(() => {
      route = {};
      artworkServiceStub = sinon.createStubInstance<ArtworkService>(ArtworkService);
      artworkServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          artworkService: () => artworkServiceStub,
          typeOfArtmediaService: () =>
            sinon.createStubInstance<TypeOfArtmediaService>(TypeOfArtmediaService, {
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
        const wrapper = shallowMount(ArtworkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artwork = artworkSample;
        artworkServiceStub.update.resolves(artworkSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artworkServiceStub.update.calledWith(artworkSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        artworkServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ArtworkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.artwork = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(artworkServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        artworkServiceStub.find.resolves(artworkSample);
        artworkServiceStub.retrieve.resolves([artworkSample]);

        // WHEN
        route = {
          params: {
            artworkId: '' + artworkSample.id,
          },
        };
        const wrapper = shallowMount(ArtworkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.artwork).toMatchObject(artworkSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        artworkServiceStub.find.resolves(artworkSample);
        const wrapper = shallowMount(ArtworkUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
