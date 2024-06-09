/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OrganizationRole from './organization-role.vue';
import OrganizationRoleService from './organization-role.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationRoleComponentType = InstanceType<typeof OrganizationRole>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OrganizationRole Management Component', () => {
    let organizationRoleServiceStub: SinonStubbedInstance<OrganizationRoleService>;
    let mountOptions: MountingOptions<OrganizationRoleComponentType>['global'];

    beforeEach(() => {
      organizationRoleServiceStub = sinon.createStubInstance<OrganizationRoleService>(OrganizationRoleService);
      organizationRoleServiceStub.retrieve.resolves({ headers: {} });

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
          organizationRoleService: () => organizationRoleServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(OrganizationRole, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationRoleServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.organizationRoles[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(OrganizationRole, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationRoleServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: OrganizationRoleComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OrganizationRole, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        organizationRoleServiceStub.retrieve.reset();
        organizationRoleServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        organizationRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(organizationRoleServiceStub.retrieve.called).toBeTruthy();
        expect(comp.organizationRoles[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(organizationRoleServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        organizationRoleServiceStub.retrieve.reset();
        organizationRoleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(organizationRoleServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.organizationRoles[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(organizationRoleServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        organizationRoleServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOrganizationRole();
        await comp.$nextTick(); // clear components

        // THEN
        expect(organizationRoleServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(organizationRoleServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
