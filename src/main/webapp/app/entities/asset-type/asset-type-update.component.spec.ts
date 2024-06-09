/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetTypeUpdate from './asset-type-update.vue';
import AssetTypeService from './asset-type.service';
import AlertService from '@/shared/alert/alert.service';

type AssetTypeUpdateComponentType = InstanceType<typeof AssetTypeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetTypeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AssetTypeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('AssetType Management Update Component', () => {
    let comp: AssetTypeUpdateComponentType;
    let assetTypeServiceStub: SinonStubbedInstance<AssetTypeService>;

    beforeEach(() => {
      route = {};
      assetTypeServiceStub = sinon.createStubInstance<AssetTypeService>(AssetTypeService);
      assetTypeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          assetTypeService: () => assetTypeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AssetTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.assetType = assetTypeSample;
        assetTypeServiceStub.update.resolves(assetTypeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetTypeServiceStub.update.calledWith(assetTypeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        assetTypeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AssetTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.assetType = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        assetTypeServiceStub.find.resolves(assetTypeSample);
        assetTypeServiceStub.retrieve.resolves([assetTypeSample]);

        // WHEN
        route = {
          params: {
            assetTypeId: '' + assetTypeSample.id,
          },
        };
        const wrapper = shallowMount(AssetTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.assetType).toMatchObject(assetTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetTypeServiceStub.find.resolves(assetTypeSample);
        const wrapper = shallowMount(AssetTypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
