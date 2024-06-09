/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationAttributeUpdate from './organization-attribute-update.vue';
import OrganizationAttributeService from './organization-attribute.service';
import AlertService from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';

type OrganizationAttributeUpdateComponentType = InstanceType<typeof OrganizationAttributeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationAttributeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OrganizationAttributeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OrganizationAttribute Management Update Component', () => {
    let comp: OrganizationAttributeUpdateComponentType;
    let organizationAttributeServiceStub: SinonStubbedInstance<OrganizationAttributeService>;

    beforeEach(() => {
      route = {};
      organizationAttributeServiceStub = sinon.createStubInstance<OrganizationAttributeService>(OrganizationAttributeService);
      organizationAttributeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          organizationAttributeService: () => organizationAttributeServiceStub,
          organizationService: () =>
            sinon.createStubInstance<OrganizationService>(OrganizationService, {
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
        const wrapper = shallowMount(OrganizationAttributeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationAttribute = organizationAttributeSample;
        organizationAttributeServiceStub.update.resolves(organizationAttributeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationAttributeServiceStub.update.calledWith(organizationAttributeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        organizationAttributeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OrganizationAttributeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationAttribute = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationAttributeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        organizationAttributeServiceStub.find.resolves(organizationAttributeSample);
        organizationAttributeServiceStub.retrieve.resolves([organizationAttributeSample]);

        // WHEN
        route = {
          params: {
            organizationAttributeId: '' + organizationAttributeSample.id,
          },
        };
        const wrapper = shallowMount(OrganizationAttributeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.organizationAttribute).toMatchObject(organizationAttributeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationAttributeServiceStub.find.resolves(organizationAttributeSample);
        const wrapper = shallowMount(OrganizationAttributeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
