/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CustomerTypeUpdate from './customer-type-update.vue';
import CustomerTypeService from './customer-type.service';
import AlertService from '@/shared/alert/alert.service';

type CustomerTypeUpdateComponentType = InstanceType<typeof CustomerTypeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const customerTypeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CustomerTypeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CustomerType Management Update Component', () => {
    let comp: CustomerTypeUpdateComponentType;
    let customerTypeServiceStub: SinonStubbedInstance<CustomerTypeService>;

    beforeEach(() => {
      route = {};
      customerTypeServiceStub = sinon.createStubInstance<CustomerTypeService>(CustomerTypeService);
      customerTypeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          customerTypeService: () => customerTypeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CustomerTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.customerType = customerTypeSample;
        customerTypeServiceStub.update.resolves(customerTypeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(customerTypeServiceStub.update.calledWith(customerTypeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        customerTypeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CustomerTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.customerType = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(customerTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        customerTypeServiceStub.find.resolves(customerTypeSample);
        customerTypeServiceStub.retrieve.resolves([customerTypeSample]);

        // WHEN
        route = {
          params: {
            customerTypeId: '' + customerTypeSample.id,
          },
        };
        const wrapper = shallowMount(CustomerTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.customerType).toMatchObject(customerTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        customerTypeServiceStub.find.resolves(customerTypeSample);
        const wrapper = shallowMount(CustomerTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
