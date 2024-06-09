/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OrganizationAttribute from './organization-attribute.vue';
import OrganizationAttributeService from './organization-attribute.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationAttributeComponentType = InstanceType<typeof OrganizationAttribute>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OrganizationAttribute Management Component', () => {
    let organizationAttributeServiceStub: SinonStubbedInstance<OrganizationAttributeService>;
    let mountOptions: MountingOptions<OrganizationAttributeComponentType>['global'];

    beforeEach(() => {
      organizationAttributeServiceStub = sinon.createStubInstance<OrganizationAttributeService>(OrganizationAttributeService);
      organizationAttributeServiceStub.retrieve.resolves({ headers: {} });

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
          organizationAttributeService: () => organizationAttributeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationAttributeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(OrganizationAttribute, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationAttributeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.organizationAttributes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(OrganizationAttribute, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(organizationAttributeServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: OrganizationAttributeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OrganizationAttribute, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        organizationAttributeServiceStub.retrieve.reset();
        organizationAttributeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        organizationAttributeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(organizationAttributeServiceStub.retrieve.called).toBeTruthy();
        expect(comp.organizationAttributes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(organizationAttributeServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        organizationAttributeServiceStub.retrieve.reset();
        organizationAttributeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(organizationAttributeServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.organizationAttributes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(organizationAttributeServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        organizationAttributeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOrganizationAttribute();
        await comp.$nextTick(); // clear components

        // THEN
        expect(organizationAttributeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(organizationAttributeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
