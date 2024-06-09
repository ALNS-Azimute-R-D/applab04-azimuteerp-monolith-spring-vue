/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetUpdate from './asset-update.vue';
import AssetService from './asset.service';
import AlertService from '@/shared/alert/alert.service';

import AssetTypeService from '@/entities/asset-type/asset-type.service';
import RawAssetProcTmpService from '@/entities/raw-asset-proc-tmp/raw-asset-proc-tmp.service';
import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';

type AssetUpdateComponentType = InstanceType<typeof AssetUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AssetUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Asset Management Update Component', () => {
    let comp: AssetUpdateComponentType;
    let assetServiceStub: SinonStubbedInstance<AssetService>;

    beforeEach(() => {
      route = {};
      assetServiceStub = sinon.createStubInstance<AssetService>(AssetService);
      assetServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          assetService: () => assetServiceStub,
          assetTypeService: () =>
            sinon.createStubInstance<AssetTypeService>(AssetTypeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          rawAssetProcTmpService: () =>
            sinon.createStubInstance<RawAssetProcTmpService>(RawAssetProcTmpService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          assetCollectionService: () =>
            sinon.createStubInstance<AssetCollectionService>(AssetCollectionService, {
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
        const wrapper = shallowMount(AssetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.asset = assetSample;
        assetServiceStub.update.resolves(assetSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetServiceStub.update.calledWith(assetSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        assetServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AssetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.asset = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        assetServiceStub.find.resolves(assetSample);
        assetServiceStub.retrieve.resolves([assetSample]);

        // WHEN
        route = {
          params: {
            assetId: '' + assetSample.id,
          },
        };
        const wrapper = shallowMount(AssetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.asset).toMatchObject(assetSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetServiceStub.find.resolves(assetSample);
        const wrapper = shallowMount(AssetUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
