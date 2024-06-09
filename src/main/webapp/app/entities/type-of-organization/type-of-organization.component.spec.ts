/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TypeOfOrganization from './type-of-organization.vue';
import TypeOfOrganizationService from './type-of-organization.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfOrganizationComponentType = InstanceType<typeof TypeOfOrganization>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TypeOfOrganization Management Component', () => {
    let typeOfOrganizationServiceStub: SinonStubbedInstance<TypeOfOrganizationService>;
    let mountOptions: MountingOptions<TypeOfOrganizationComponentType>['global'];

    beforeEach(() => {
      typeOfOrganizationServiceStub = sinon.createStubInstance<TypeOfOrganizationService>(TypeOfOrganizationService);
      typeOfOrganizationServiceStub.retrieve.resolves({ headers: {} });

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
          typeOfOrganizationService: () => typeOfOrganizationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfOrganizationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TypeOfOrganization, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfOrganizationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.typeOfOrganizations[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TypeOfOrganization, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfOrganizationServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TypeOfOrganizationComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TypeOfOrganization, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeOfOrganizationServiceStub.retrieve.reset();
        typeOfOrganizationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        typeOfOrganizationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(typeOfOrganizationServiceStub.retrieve.called).toBeTruthy();
        expect(comp.typeOfOrganizations[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(typeOfOrganizationServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        typeOfOrganizationServiceStub.retrieve.reset();
        typeOfOrganizationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(typeOfOrganizationServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.typeOfOrganizations[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(typeOfOrganizationServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeOfOrganizationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTypeOfOrganization();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeOfOrganizationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeOfOrganizationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
