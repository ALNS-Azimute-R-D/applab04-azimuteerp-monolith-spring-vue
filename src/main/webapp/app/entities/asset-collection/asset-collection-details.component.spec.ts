/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetCollectionDetails from './asset-collection-details.vue';
import AssetCollectionService from './asset-collection.service';
import AlertService from '@/shared/alert/alert.service';

type AssetCollectionDetailsComponentType = InstanceType<typeof AssetCollectionDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetCollectionSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('AssetCollection Management Detail Component', () => {
    let assetCollectionServiceStub: SinonStubbedInstance<AssetCollectionService>;
    let mountOptions: MountingOptions<AssetCollectionDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      assetCollectionServiceStub = sinon.createStubInstance<AssetCollectionService>(AssetCollectionService);

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
          assetCollectionService: () => assetCollectionServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        assetCollectionServiceStub.find.resolves(assetCollectionSample);
        route = {
          params: {
            assetCollectionId: '' + 123,
          },
        };
        const wrapper = shallowMount(AssetCollectionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.assetCollection).toMatchObject(assetCollectionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetCollectionServiceStub.find.resolves(assetCollectionSample);
        const wrapper = shallowMount(AssetCollectionDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
