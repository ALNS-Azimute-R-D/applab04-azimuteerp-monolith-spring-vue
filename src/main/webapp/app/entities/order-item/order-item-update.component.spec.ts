/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrderItemUpdate from './order-item-update.vue';
import OrderItemService from './order-item.service';
import AlertService from '@/shared/alert/alert.service';

import ArticleService from '@/entities/article/article.service';
import OrderService from '@/entities/order/order.service';

type OrderItemUpdateComponentType = InstanceType<typeof OrderItemUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const orderItemSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OrderItemUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OrderItem Management Update Component', () => {
    let comp: OrderItemUpdateComponentType;
    let orderItemServiceStub: SinonStubbedInstance<OrderItemService>;

    beforeEach(() => {
      route = {};
      orderItemServiceStub = sinon.createStubInstance<OrderItemService>(OrderItemService);
      orderItemServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          orderItemService: () => orderItemServiceStub,
          articleService: () =>
            sinon.createStubInstance<ArticleService>(ArticleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          orderService: () =>
            sinon.createStubInstance<OrderService>(OrderService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(OrderItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.orderItem = orderItemSample;
        orderItemServiceStub.update.resolves(orderItemSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orderItemServiceStub.update.calledWith(orderItemSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        orderItemServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OrderItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.orderItem = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orderItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        orderItemServiceStub.find.resolves(orderItemSample);
        orderItemServiceStub.retrieve.resolves([orderItemSample]);

        // WHEN
        route = {
          params: {
            orderItemId: '' + orderItemSample.id,
          },
        };
        const wrapper = shallowMount(OrderItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.orderItem).toMatchObject(orderItemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        orderItemServiceStub.find.resolves(orderItemSample);
        const wrapper = shallowMount(OrderItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
