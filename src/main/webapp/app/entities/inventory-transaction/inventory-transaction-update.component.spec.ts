/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import InventoryTransactionUpdate from './inventory-transaction-update.vue';
import InventoryTransactionService from './inventory-transaction.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import SupplierService from '@/entities/supplier/supplier.service';
import ProductService from '@/entities/product/product.service';
import WarehouseService from '@/entities/warehouse/warehouse.service';

type InventoryTransactionUpdateComponentType = InstanceType<typeof InventoryTransactionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const inventoryTransactionSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<InventoryTransactionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('InventoryTransaction Management Update Component', () => {
    let comp: InventoryTransactionUpdateComponentType;
    let inventoryTransactionServiceStub: SinonStubbedInstance<InventoryTransactionService>;

    beforeEach(() => {
      route = {};
      inventoryTransactionServiceStub = sinon.createStubInstance<InventoryTransactionService>(InventoryTransactionService);
      inventoryTransactionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          inventoryTransactionService: () => inventoryTransactionServiceStub,
          supplierService: () =>
            sinon.createStubInstance<SupplierService>(SupplierService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          warehouseService: () =>
            sinon.createStubInstance<WarehouseService>(WarehouseService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(InventoryTransactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(InventoryTransactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.inventoryTransaction = inventoryTransactionSample;
        inventoryTransactionServiceStub.update.resolves(inventoryTransactionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(inventoryTransactionServiceStub.update.calledWith(inventoryTransactionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        inventoryTransactionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(InventoryTransactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.inventoryTransaction = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(inventoryTransactionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        inventoryTransactionServiceStub.find.resolves(inventoryTransactionSample);
        inventoryTransactionServiceStub.retrieve.resolves([inventoryTransactionSample]);

        // WHEN
        route = {
          params: {
            inventoryTransactionId: '' + inventoryTransactionSample.id,
          },
        };
        const wrapper = shallowMount(InventoryTransactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.inventoryTransaction).toMatchObject(inventoryTransactionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        inventoryTransactionServiceStub.find.resolves(inventoryTransactionSample);
        const wrapper = shallowMount(InventoryTransactionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
