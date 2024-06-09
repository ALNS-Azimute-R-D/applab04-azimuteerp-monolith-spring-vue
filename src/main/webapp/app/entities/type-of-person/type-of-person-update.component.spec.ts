/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfPersonUpdate from './type-of-person-update.vue';
import TypeOfPersonService from './type-of-person.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfPersonUpdateComponentType = InstanceType<typeof TypeOfPersonUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfPersonSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfPersonUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfPerson Management Update Component', () => {
    let comp: TypeOfPersonUpdateComponentType;
    let typeOfPersonServiceStub: SinonStubbedInstance<TypeOfPersonService>;

    beforeEach(() => {
      route = {};
      typeOfPersonServiceStub = sinon.createStubInstance<TypeOfPersonService>(TypeOfPersonService);
      typeOfPersonServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeOfPersonService: () => typeOfPersonServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfPersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfPerson = typeOfPersonSample;
        typeOfPersonServiceStub.update.resolves(typeOfPersonSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfPersonServiceStub.update.calledWith(typeOfPersonSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfPersonServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfPersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfPerson = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfPersonServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfPersonServiceStub.find.resolves(typeOfPersonSample);
        typeOfPersonServiceStub.retrieve.resolves([typeOfPersonSample]);

        // WHEN
        route = {
          params: {
            typeOfPersonId: '' + typeOfPersonSample.id,
          },
        };
        const wrapper = shallowMount(TypeOfPersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfPerson).toMatchObject(typeOfPersonSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfPersonServiceStub.find.resolves(typeOfPersonSample);
        const wrapper = shallowMount(TypeOfPersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
