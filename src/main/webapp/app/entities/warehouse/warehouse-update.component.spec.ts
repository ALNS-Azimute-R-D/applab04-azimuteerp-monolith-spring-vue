/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import WarehouseUpdate from './warehouse-update.vue';
import WarehouseService from './warehouse.service';
import AlertService from '@/shared/alert/alert.service';

type WarehouseUpdateComponentType = InstanceType<typeof WarehouseUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const warehouseSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<WarehouseUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Warehouse Management Update Component', () => {
    let comp: WarehouseUpdateComponentType;
    let warehouseServiceStub: SinonStubbedInstance<WarehouseService>;

    beforeEach(() => {
      route = {};
      warehouseServiceStub = sinon.createStubInstance<WarehouseService>(WarehouseService);
      warehouseServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          warehouseService: () => warehouseServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(WarehouseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.warehouse = warehouseSample;
        warehouseServiceStub.update.resolves(warehouseSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(warehouseServiceStub.update.calledWith(warehouseSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        warehouseServiceStub.create.resolves(entity);
        const wrapper = shallowMount(WarehouseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.warehouse = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(warehouseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        warehouseServiceStub.find.resolves(warehouseSample);
        warehouseServiceStub.retrieve.resolves([warehouseSample]);

        // WHEN
        route = {
          params: {
            warehouseId: '' + warehouseSample.id,
          },
        };
        const wrapper = shallowMount(WarehouseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.warehouse).toMatchObject(warehouseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        warehouseServiceStub.find.resolves(warehouseSample);
        const wrapper = shallowMount(WarehouseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
