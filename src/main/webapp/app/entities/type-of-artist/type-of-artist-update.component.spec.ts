/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfArtistUpdate from './type-of-artist-update.vue';
import TypeOfArtistService from './type-of-artist.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfArtistUpdateComponentType = InstanceType<typeof TypeOfArtistUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfArtistSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfArtistUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfArtist Management Update Component', () => {
    let comp: TypeOfArtistUpdateComponentType;
    let typeOfArtistServiceStub: SinonStubbedInstance<TypeOfArtistService>;

    beforeEach(() => {
      route = {};
      typeOfArtistServiceStub = sinon.createStubInstance<TypeOfArtistService>(TypeOfArtistService);
      typeOfArtistServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeOfArtistService: () => typeOfArtistServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfArtist = typeOfArtistSample;
        typeOfArtistServiceStub.update.resolves(typeOfArtistSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfArtistServiceStub.update.calledWith(typeOfArtistSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfArtistServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfArtist = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfArtistServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfArtistServiceStub.find.resolves(typeOfArtistSample);
        typeOfArtistServiceStub.retrieve.resolves([typeOfArtistSample]);

        // WHEN
        route = {
          params: {
            typeOfArtistId: '' + typeOfArtistSample.id,
          },
        };
        const wrapper = shallowMount(TypeOfArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfArtist).toMatchObject(typeOfArtistSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfArtistServiceStub.find.resolves(typeOfArtistSample);
        const wrapper = shallowMount(TypeOfArtistUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
