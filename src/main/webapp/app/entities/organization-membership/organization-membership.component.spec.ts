/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OrganizationMembership from './organization-membership.vue';
import OrganizationMembershipService from './organization-membership.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationMembershipComponentType = InstanceType<typeof OrganizationMembership>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OrganizationMembership Management Component', () => {
    let organizationMembershipServiceStub: SinonStubbedInstance<OrganizationMembershipService>;
    let mountOptions: MountingOptions<OrganizationMembershipComponentType>['global'];

    beforeEach(() => {
      organizationMembershipServiceStub = sinon.createStubInstance<OrganizationMembershipService>(OrganizationMembershipService);
      organizationMembershipServiceStub.retrieve.resolves({ headers: {} });

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
          organizationMembershipService: () => organizationMembershipServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationMembershipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(OrganizationMembership, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationMembershipServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.organizationMemberships[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(OrganizationMembership, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationMembershipServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: OrganizationMembershipComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OrganizationMembership, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        organizationMembershipServiceStub.retrieve.reset();
        organizationMembershipServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        organizationMembershipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(organizationMembershipServiceStub.retrieve.called).toBeTruthy();
        expect(comp.organizationMemberships[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(organizationMembershipServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        organizationMembershipServiceStub.retrieve.reset();
        organizationMembershipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(organizationMembershipServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.organizationMemberships[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(organizationMembershipServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        organizationMembershipServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOrganizationMembership();
        await comp.$nextTick(); // clear components

        // THEN
        expect(organizationMembershipServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(organizationMembershipServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
