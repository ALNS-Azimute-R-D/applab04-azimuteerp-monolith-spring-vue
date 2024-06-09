/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetMetadataUpdate from './asset-metadata-update.vue';
import AssetMetadataService from './asset-metadata.service';
import AlertService from '@/shared/alert/alert.service';

import AssetService from '@/entities/asset/asset.service';

type AssetMetadataUpdateComponentType = InstanceType<typeof AssetMetadataUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetMetadataSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AssetMetadataUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('AssetMetadata Management Update Component', () => {
    let comp: AssetMetadataUpdateComponentType;
    let assetMetadataServiceStub: SinonStubbedInstance<AssetMetadataService>;

    beforeEach(() => {
      route = {};
      assetMetadataServiceStub = sinon.createStubInstance<AssetMetadataService>(AssetMetadataService);
      assetMetadataServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          assetMetadataService: () => assetMetadataServiceStub,
          assetService: () =>
            sinon.createStubInstance<AssetService>(AssetService, {
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
        const wrapper = shallowMount(AssetMetadataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.assetMetadata = assetMetadataSample;
        assetMetadataServiceStub.update.resolves(assetMetadataSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetMetadataServiceStub.update.calledWith(assetMetadataSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        assetMetadataServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AssetMetadataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.assetMetadata = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetMetadataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        assetMetadataServiceStub.find.resolves(assetMetadataSample);
        assetMetadataServiceStub.retrieve.resolves([assetMetadataSample]);

        // WHEN
        route = {
          params: {
            assetMetadataId: '' + assetMetadataSample.id,
          },
        };
        const wrapper = shallowMount(AssetMetadataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.assetMetadata).toMatchObject(assetMetadataSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetMetadataServiceStub.find.resolves(assetMetadataSample);
        const wrapper = shallowMount(AssetMetadataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
