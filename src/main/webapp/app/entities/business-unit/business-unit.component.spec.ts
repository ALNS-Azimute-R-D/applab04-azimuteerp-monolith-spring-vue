/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import BusinessUnit from './business-unit.vue';
import BusinessUnitService from './business-unit.service';
import AlertService from '@/shared/alert/alert.service';

type BusinessUnitComponentType = InstanceType<typeof BusinessUnit>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('BusinessUnit Management Component', () => {
    let businessUnitServiceStub: SinonStubbedInstance<BusinessUnitService>;
    let mountOptions: MountingOptions<BusinessUnitComponentType>['global'];

    beforeEach(() => {
      businessUnitServiceStub = sinon.createStubInstance<BusinessUnitService>(BusinessUnitService);
      businessUnitServiceStub.retrieve.resolves({ headers: {} });

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
          businessUnitService: () => businessUnitServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(BusinessUnit, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(businessUnitServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.businessUnits[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(BusinessUnit, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(businessUnitServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: BusinessUnitComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(BusinessUnit, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        businessUnitServiceStub.retrieve.reset();
        businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(businessUnitServiceStub.retrieve.called).toBeTruthy();
        expect(comp.businessUnits[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(businessUnitServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        businessUnitServiceStub.retrieve.reset();
        businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(businessUnitServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.businessUnits[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(businessUnitServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        businessUnitServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeBusinessUnit();
        await comp.$nextTick(); // clear components

        // THEN
        expect(businessUnitServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(businessUnitServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
