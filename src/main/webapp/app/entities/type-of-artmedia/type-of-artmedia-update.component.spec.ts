/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfArtmediaUpdate from './type-of-artmedia-update.vue';
import TypeOfArtmediaService from './type-of-artmedia.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfArtmediaUpdateComponentType = InstanceType<typeof TypeOfArtmediaUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfArtmediaSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfArtmediaUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfArtmedia Management Update Component', () => {
    let comp: TypeOfArtmediaUpdateComponentType;
    let typeOfArtmediaServiceStub: SinonStubbedInstance<TypeOfArtmediaService>;

    beforeEach(() => {
      route = {};
      typeOfArtmediaServiceStub = sinon.createStubInstance<TypeOfArtmediaService>(TypeOfArtmediaService);
      typeOfArtmediaServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeOfArtmediaService: () => typeOfArtmediaServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfArtmediaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfArtmedia = typeOfArtmediaSample;
        typeOfArtmediaServiceStub.update.resolves(typeOfArtmediaSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfArtmediaServiceStub.update.calledWith(typeOfArtmediaSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfArtmediaServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfArtmediaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfArtmedia = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfArtmediaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfArtmediaServiceStub.find.resolves(typeOfArtmediaSample);
        typeOfArtmediaServiceStub.retrieve.resolves([typeOfArtmediaSample]);

        // WHEN
        route = {
          params: {
            typeOfArtmediaId: '' + typeOfArtmediaSample.id,
          },
        };
        const wrapper = shallowMount(TypeOfArtmediaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfArtmedia).toMatchObject(typeOfArtmediaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfArtmediaServiceStub.find.resolves(typeOfArtmediaSample);
        const wrapper = shallowMount(TypeOfArtmediaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
