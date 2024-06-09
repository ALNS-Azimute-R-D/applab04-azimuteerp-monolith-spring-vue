/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import StockLevelUpdate from './stock-level-update.vue';
import StockLevelService from './stock-level.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import WarehouseService from '@/entities/warehouse/warehouse.service';
import ProductService from '@/entities/product/product.service';

type StockLevelUpdateComponentType = InstanceType<typeof StockLevelUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const stockLevelSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<StockLevelUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('StockLevel Management Update Component', () => {
    let comp: StockLevelUpdateComponentType;
    let stockLevelServiceStub: SinonStubbedInstance<StockLevelService>;

    beforeEach(() => {
      route = {};
      stockLevelServiceStub = sinon.createStubInstance<StockLevelService>(StockLevelService);
      stockLevelServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          stockLevelService: () => stockLevelServiceStub,
          warehouseService: () =>
            sinon.createStubInstance<WarehouseService>(WarehouseService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
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
        const wrapper = shallowMount(StockLevelUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(StockLevelUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.stockLevel = stockLevelSample;
        stockLevelServiceStub.update.resolves(stockLevelSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stockLevelServiceStub.update.calledWith(stockLevelSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        stockLevelServiceStub.create.resolves(entity);
        const wrapper = shallowMount(StockLevelUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.stockLevel = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(stockLevelServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        stockLevelServiceStub.find.resolves(stockLevelSample);
        stockLevelServiceStub.retrieve.resolves([stockLevelSample]);

        // WHEN
        route = {
          params: {
            stockLevelId: '' + stockLevelSample.id,
          },
        };
        const wrapper = shallowMount(StockLevelUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.stockLevel).toMatchObject(stockLevelSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        stockLevelServiceStub.find.resolves(stockLevelSample);
        const wrapper = shallowMount(StockLevelUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
