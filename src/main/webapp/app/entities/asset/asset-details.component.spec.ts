/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetDetails from './asset-details.vue';
import AssetService from './asset.service';
import AlertService from '@/shared/alert/alert.service';

type AssetDetailsComponentType = InstanceType<typeof AssetDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Asset Management Detail Component', () => {
    let assetServiceStub: SinonStubbedInstance<AssetService>;
    let mountOptions: MountingOptions<AssetDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      assetServiceStub = sinon.createStubInstance<AssetService>(AssetService);

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
          assetService: () => assetServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        assetServiceStub.find.resolves(assetSample);
        route = {
          params: {
            assetId: '' + 123,
          },
        };
        const wrapper = shallowMount(AssetDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.asset).toMatchObject(assetSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetServiceStub.find.resolves(assetSample);
        const wrapper = shallowMount(AssetDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
