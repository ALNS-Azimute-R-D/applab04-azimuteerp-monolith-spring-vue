/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RawAssetProcTmpUpdate from './raw-asset-proc-tmp-update.vue';
import RawAssetProcTmpService from './raw-asset-proc-tmp.service';
import AlertService from '@/shared/alert/alert.service';

import AssetTypeService from '@/entities/asset-type/asset-type.service';

type RawAssetProcTmpUpdateComponentType = InstanceType<typeof RawAssetProcTmpUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rawAssetProcTmpSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RawAssetProcTmpUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RawAssetProcTmp Management Update Component', () => {
    let comp: RawAssetProcTmpUpdateComponentType;
    let rawAssetProcTmpServiceStub: SinonStubbedInstance<RawAssetProcTmpService>;

    beforeEach(() => {
      route = {};
      rawAssetProcTmpServiceStub = sinon.createStubInstance<RawAssetProcTmpService>(RawAssetProcTmpService);
      rawAssetProcTmpServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rawAssetProcTmpService: () => rawAssetProcTmpServiceStub,
          assetTypeService: () =>
            sinon.createStubInstance<AssetTypeService>(AssetTypeService, {
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
        const wrapper = shallowMount(RawAssetProcTmpUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rawAssetProcTmp = rawAssetProcTmpSample;
        rawAssetProcTmpServiceStub.update.resolves(rawAssetProcTmpSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rawAssetProcTmpServiceStub.update.calledWith(rawAssetProcTmpSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rawAssetProcTmpServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RawAssetProcTmpUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rawAssetProcTmp = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rawAssetProcTmpServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rawAssetProcTmpServiceStub.find.resolves(rawAssetProcTmpSample);
        rawAssetProcTmpServiceStub.retrieve.resolves([rawAssetProcTmpSample]);

        // WHEN
        route = {
          params: {
            rawAssetProcTmpId: '' + rawAssetProcTmpSample.id,
          },
        };
        const wrapper = shallowMount(RawAssetProcTmpUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rawAssetProcTmp).toMatchObject(rawAssetProcTmpSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rawAssetProcTmpServiceStub.find.resolves(rawAssetProcTmpSample);
        const wrapper = shallowMount(RawAssetProcTmpUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
