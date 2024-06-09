/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationRoleUpdate from './organization-role-update.vue';
import OrganizationRoleService from './organization-role.service';
import AlertService from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';

type OrganizationRoleUpdateComponentType = InstanceType<typeof OrganizationRoleUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationRoleSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OrganizationRoleUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OrganizationRole Management Update Component', () => {
    let comp: OrganizationRoleUpdateComponentType;
    let organizationRoleServiceStub: SinonStubbedInstance<OrganizationRoleService>;

    beforeEach(() => {
      route = {};
      organizationRoleServiceStub = sinon.createStubInstance<OrganizationRoleService>(OrganizationRoleService);
      organizationRoleServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          organizationRoleService: () => organizationRoleServiceStub,
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
        const wrapper = shallowMount(OrganizationRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationRole = organizationRoleSample;
        organizationRoleServiceStub.update.resolves(organizationRoleSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationRoleServiceStub.update.calledWith(organizationRoleSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        organizationRoleServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OrganizationRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationRole = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationRoleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        organizationRoleServiceStub.find.resolves(organizationRoleSample);
        organizationRoleServiceStub.retrieve.resolves([organizationRoleSample]);

        // WHEN
        route = {
          params: {
            organizationRoleId: '' + organizationRoleSample.id,
          },
        };
        const wrapper = shallowMount(OrganizationRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.organizationRole).toMatchObject(organizationRoleSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationRoleServiceStub.find.resolves(organizationRoleSample);
        const wrapper = shallowMount(OrganizationRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
