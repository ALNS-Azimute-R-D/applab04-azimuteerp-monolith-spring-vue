/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfOrganizationUpdate from './type-of-organization-update.vue';
import TypeOfOrganizationService from './type-of-organization.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfOrganizationUpdateComponentType = InstanceType<typeof TypeOfOrganizationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfOrganizationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfOrganizationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfOrganization Management Update Component', () => {
    let comp: TypeOfOrganizationUpdateComponentType;
    let typeOfOrganizationServiceStub: SinonStubbedInstance<TypeOfOrganizationService>;

    beforeEach(() => {
      route = {};
      typeOfOrganizationServiceStub = sinon.createStubInstance<TypeOfOrganizationService>(TypeOfOrganizationService);
      typeOfOrganizationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeOfOrganizationService: () => typeOfOrganizationServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfOrganizationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfOrganization = typeOfOrganizationSample;
        typeOfOrganizationServiceStub.update.resolves(typeOfOrganizationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfOrganizationServiceStub.update.calledWith(typeOfOrganizationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfOrganizationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfOrganizationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfOrganization = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfOrganizationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfOrganizationServiceStub.find.resolves(typeOfOrganizationSample);
        typeOfOrganizationServiceStub.retrieve.resolves([typeOfOrganizationSample]);

        // WHEN
        route = {
          params: {
            typeOfOrganizationId: '' + typeOfOrganizationSample.id,
          },
        };
        const wrapper = shallowMount(TypeOfOrganizationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfOrganization).toMatchObject(typeOfOrganizationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfOrganizationServiceStub.find.resolves(typeOfOrganizationSample);
        const wrapper = shallowMount(TypeOfOrganizationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
