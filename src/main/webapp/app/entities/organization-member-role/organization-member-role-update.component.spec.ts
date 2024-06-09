/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationMemberRoleUpdate from './organization-member-role-update.vue';
import OrganizationMemberRoleService from './organization-member-role.service';
import AlertService from '@/shared/alert/alert.service';

import OrganizationMembershipService from '@/entities/organization-membership/organization-membership.service';
import OrganizationRoleService from '@/entities/organization-role/organization-role.service';

type OrganizationMemberRoleUpdateComponentType = InstanceType<typeof OrganizationMemberRoleUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationMemberRoleSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OrganizationMemberRoleUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OrganizationMemberRole Management Update Component', () => {
    let comp: OrganizationMemberRoleUpdateComponentType;
    let organizationMemberRoleServiceStub: SinonStubbedInstance<OrganizationMemberRoleService>;

    beforeEach(() => {
      route = {};
      organizationMemberRoleServiceStub = sinon.createStubInstance<OrganizationMemberRoleService>(OrganizationMemberRoleService);
      organizationMemberRoleServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          organizationMemberRoleService: () => organizationMemberRoleServiceStub,
          organizationMembershipService: () =>
            sinon.createStubInstance<OrganizationMembershipService>(OrganizationMembershipService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          organizationRoleService: () =>
            sinon.createStubInstance<OrganizationRoleService>(OrganizationRoleService, {
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
        const wrapper = shallowMount(OrganizationMemberRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationMemberRole = organizationMemberRoleSample;
        organizationMemberRoleServiceStub.update.resolves(organizationMemberRoleSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationMemberRoleServiceStub.update.calledWith(organizationMemberRoleSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        organizationMemberRoleServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OrganizationMemberRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationMemberRole = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationMemberRoleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        organizationMemberRoleServiceStub.find.resolves(organizationMemberRoleSample);
        organizationMemberRoleServiceStub.retrieve.resolves([organizationMemberRoleSample]);

        // WHEN
        route = {
          params: {
            organizationMemberRoleId: '' + organizationMemberRoleSample.id,
          },
        };
        const wrapper = shallowMount(OrganizationMemberRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.organizationMemberRole).toMatchObject(organizationMemberRoleSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationMemberRoleServiceStub.find.resolves(organizationMemberRoleSample);
        const wrapper = shallowMount(OrganizationMemberRoleUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
