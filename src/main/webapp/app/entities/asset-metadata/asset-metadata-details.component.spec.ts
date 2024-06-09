/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetMetadataDetails from './asset-metadata-details.vue';
import AssetMetadataService from './asset-metadata.service';
import AlertService from '@/shared/alert/alert.service';

type AssetMetadataDetailsComponentType = InstanceType<typeof AssetMetadataDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetMetadataSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('AssetMetadata Management Detail Component', () => {
    let assetMetadataServiceStub: SinonStubbedInstance<AssetMetadataService>;
    let mountOptions: MountingOptions<AssetMetadataDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      assetMetadataServiceStub = sinon.createStubInstance<AssetMetadataService>(AssetMetadataService);

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
          assetMetadataService: () => assetMetadataServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        assetMetadataServiceStub.find.resolves(assetMetadataSample);
        route = {
          params: {
            assetMetadataId: '' + 123,
          },
        };
        const wrapper = shallowMount(AssetMetadataDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.assetMetadata).toMatchObject(assetMetadataSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetMetadataServiceStub.find.resolves(assetMetadataSample);
        const wrapper = shallowMount(AssetMetadataDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
