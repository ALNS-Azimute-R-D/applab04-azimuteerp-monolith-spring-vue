/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OrganizationMemberRole from './organization-member-role.vue';
import OrganizationMemberRoleService from './organization-member-role.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationMemberRoleComponentType = InstanceType<typeof OrganizationMemberRole>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OrganizationMemberRole Management Component', () => {
    let organizationMemberRoleServiceStub: SinonStubbedInstance<OrganizationMemberRoleService>;
    let mountOptions: MountingOptions<OrganizationMemberRoleComponentType>['global'];

    beforeEach(() => {
      organizationMemberRoleServiceStub = sinon.createStubInstance<OrganizationMemberRoleService>(OrganizationMemberRoleService);
      organizationMemberRoleServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          organizationMemberRoleService: () => organizationMemberRoleServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationMemberRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(OrganizationMemberRole, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationMemberRoleServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.organizationMemberRoles[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(OrganizationMemberRole, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationMemberRoleServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: OrganizationMemberRoleComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OrganizationMemberRole, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        organizationMemberRoleServiceStub.retrieve.reset();
        organizationMemberRoleServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        organizationMemberRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(organizationMemberRoleServiceStub.retrieve.called).toBeTruthy();
        expect(comp.organizationMemberRoles[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(organizationMemberRoleServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        organizationMemberRoleServiceStub.retrieve.reset();
        organizationMemberRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(organizationMemberRoleServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.organizationMemberRoles[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(organizationMemberRoleServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        organizationMemberRoleServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOrganizationMemberRole();
        await comp.$nextTick(); // clear components

        // THEN
        expect(organizationMemberRoleServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(organizationMemberRoleServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
