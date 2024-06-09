/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OrganizationDomain from './organization-domain.vue';
import OrganizationDomainService from './organization-domain.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationDomainComponentType = InstanceType<typeof OrganizationDomain>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OrganizationDomain Management Component', () => {
    let organizationDomainServiceStub: SinonStubbedInstance<OrganizationDomainService>;
    let mountOptions: MountingOptions<OrganizationDomainComponentType>['global'];

    beforeEach(() => {
      organizationDomainServiceStub = sinon.createStubInstance<OrganizationDomainService>(OrganizationDomainService);
      organizationDomainServiceStub.retrieve.resolves({ headers: {} });

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
          organizationDomainService: () => organizationDomainServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationDomainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(OrganizationDomain, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationDomainServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.organizationDomains[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(OrganizationDomain, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationDomainServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: OrganizationDomainComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OrganizationDomain, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        organizationDomainServiceStub.retrieve.reset();
        organizationDomainServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        organizationDomainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(organizationDomainServiceStub.retrieve.called).toBeTruthy();
        expect(comp.organizationDomains[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(organizationDomainServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        organizationDomainServiceStub.retrieve.reset();
        organizationDomainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(organizationDomainServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.organizationDomains[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(organizationDomainServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        organizationDomainServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOrganizationDomain();
        await comp.$nextTick(); // clear components

        // THEN
        expect(organizationDomainServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(organizationDomainServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
