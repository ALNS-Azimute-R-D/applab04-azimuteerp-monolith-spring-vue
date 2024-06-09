/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import AssetMetadata from './asset-metadata.vue';
import AssetMetadataService from './asset-metadata.service';
import AlertService from '@/shared/alert/alert.service';

type AssetMetadataComponentType = InstanceType<typeof AssetMetadata>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('AssetMetadata Management Component', () => {
    let assetMetadataServiceStub: SinonStubbedInstance<AssetMetadataService>;
    let mountOptions: MountingOptions<AssetMetadataComponentType>['global'];

    beforeEach(() => {
      assetMetadataServiceStub = sinon.createStubInstance<AssetMetadataService>(AssetMetadataService);
      assetMetadataServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          assetMetadataService: () => assetMetadataServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        assetMetadataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(AssetMetadata, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(assetMetadataServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.assetMetadata[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(AssetMetadata, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(assetMetadataServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: AssetMetadataComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(AssetMetadata, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        assetMetadataServiceStub.retrieve.reset();
        assetMetadataServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        assetMetadataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(assetMetadataServiceStub.retrieve.called).toBeTruthy();
        expect(comp.assetMetadata[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(assetMetadataServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        assetMetadataServiceStub.retrieve.reset();
        assetMetadataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(assetMetadataServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.assetMetadata[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(assetMetadataServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        assetMetadataServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAssetMetadata();
        await comp.$nextTick(); // clear components

        // THEN
        expect(assetMetadataServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(assetMetadataServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
