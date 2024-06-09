/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import InventoryTransaction from './inventory-transaction.vue';
import InventoryTransactionService from './inventory-transaction.service';
import AlertService from '@/shared/alert/alert.service';

type InventoryTransactionComponentType = InstanceType<typeof InventoryTransaction>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('InventoryTransaction Management Component', () => {
    let inventoryTransactionServiceStub: SinonStubbedInstance<InventoryTransactionService>;
    let mountOptions: MountingOptions<InventoryTransactionComponentType>['global'];

    beforeEach(() => {
      inventoryTransactionServiceStub = sinon.createStubInstance<InventoryTransactionService>(InventoryTransactionService);
      inventoryTransactionServiceStub.retrieve.resolves({ headers: {} });

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
          inventoryTransactionService: () => inventoryTransactionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        inventoryTransactionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(InventoryTransaction, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(inventoryTransactionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.inventoryTransactions[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(InventoryTransaction, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(inventoryTransactionServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: InventoryTransactionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(InventoryTransaction, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        inventoryTransactionServiceStub.retrieve.reset();
        inventoryTransactionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        inventoryTransactionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(inventoryTransactionServiceStub.retrieve.called).toBeTruthy();
        expect(comp.inventoryTransactions[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(inventoryTransactionServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        inventoryTransactionServiceStub.retrieve.reset();
        inventoryTransactionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(inventoryTransactionServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.inventoryTransactions[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(inventoryTransactionServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        inventoryTransactionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeInventoryTransaction();
        await comp.$nextTick(); // clear components

        // THEN
        expect(inventoryTransactionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(inventoryTransactionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
