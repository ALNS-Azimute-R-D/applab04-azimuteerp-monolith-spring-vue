/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RawAssetProcTmpDetails from './raw-asset-proc-tmp-details.vue';
import RawAssetProcTmpService from './raw-asset-proc-tmp.service';
import AlertService from '@/shared/alert/alert.service';

type RawAssetProcTmpDetailsComponentType = InstanceType<typeof RawAssetProcTmpDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rawAssetProcTmpSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RawAssetProcTmp Management Detail Component', () => {
    let rawAssetProcTmpServiceStub: SinonStubbedInstance<RawAssetProcTmpService>;
    let mountOptions: MountingOptions<RawAssetProcTmpDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rawAssetProcTmpServiceStub = sinon.createStubInstance<RawAssetProcTmpService>(RawAssetProcTmpService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          rawAssetProcTmpService: () => rawAssetProcTmpServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rawAssetProcTmpServiceStub.find.resolves(rawAssetProcTmpSample);
        route = {
          params: {
            rawAssetProcTmpId: '' + 123,
          },
        };
        const wrapper = shallowMount(RawAssetProcTmpDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rawAssetProcTmp).toMatchObject(rawAssetProcTmpSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rawAssetProcTmpServiceStub.find.resolves(rawAssetProcTmpSample);
        const wrapper = shallowMount(RawAssetProcTmpDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
