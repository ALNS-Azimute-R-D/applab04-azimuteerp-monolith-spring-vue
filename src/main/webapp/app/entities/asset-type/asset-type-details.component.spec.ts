/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetTypeDetails from './asset-type-details.vue';
import AssetTypeService from './asset-type.service';
import AlertService from '@/shared/alert/alert.service';

type AssetTypeDetailsComponentType = InstanceType<typeof AssetTypeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetTypeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('AssetType Management Detail Component', () => {
    let assetTypeServiceStub: SinonStubbedInstance<AssetTypeService>;
    let mountOptions: MountingOptions<AssetTypeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      assetTypeServiceStub = sinon.createStubInstance<AssetTypeService>(AssetTypeService);

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
          assetTypeService: () => assetTypeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        assetTypeServiceStub.find.resolves(assetTypeSample);
        route = {
          params: {
            assetTypeId: '' + 123,
          },
        };
        const wrapper = shallowMount(AssetTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.assetType).toMatchObject(assetTypeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetTypeServiceStub.find.resolves(assetTypeSample);
        const wrapper = shallowMount(AssetTypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
