/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfActivityUpdate from './type-of-activity-update.vue';
import TypeOfActivityService from './type-of-activity.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfActivityUpdateComponentType = InstanceType<typeof TypeOfActivityUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfActivitySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfActivityUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfActivity Management Update Component', () => {
    let comp: TypeOfActivityUpdateComponentType;
    let typeOfActivityServiceStub: SinonStubbedInstance<TypeOfActivityService>;

    beforeEach(() => {
      route = {};
      typeOfActivityServiceStub = sinon.createStubInstance<TypeOfActivityService>(TypeOfActivityService);
      typeOfActivityServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeOfActivityService: () => typeOfActivityServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfActivity = typeOfActivitySample;
        typeOfActivityServiceStub.update.resolves(typeOfActivitySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfActivityServiceStub.update.calledWith(typeOfActivitySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfActivityServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfActivity = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfActivityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfActivityServiceStub.find.resolves(typeOfActivitySample);
        typeOfActivityServiceStub.retrieve.resolves([typeOfActivitySample]);

        // WHEN
        route = {
          params: {
            typeOfActivityId: '' + typeOfActivitySample.id,
          },
        };
        const wrapper = shallowMount(TypeOfActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfActivity).toMatchObject(typeOfActivitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfActivityServiceStub.find.resolves(typeOfActivitySample);
        const wrapper = shallowMount(TypeOfActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
