/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import AssetCollection from './asset-collection.vue';
import AssetCollectionService from './asset-collection.service';
import AlertService from '@/shared/alert/alert.service';

type AssetCollectionComponentType = InstanceType<typeof AssetCollection>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('AssetCollection Management Component', () => {
    let assetCollectionServiceStub: SinonStubbedInstance<AssetCollectionService>;
    let mountOptions: MountingOptions<AssetCollectionComponentType>['global'];

    beforeEach(() => {
      assetCollectionServiceStub = sinon.createStubInstance<AssetCollectionService>(AssetCollectionService);
      assetCollectionServiceStub.retrieve.resolves({ headers: {} });

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
          assetCollectionService: () => assetCollectionServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        assetCollectionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(AssetCollection, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(assetCollectionServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.assetCollections[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(AssetCollection, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(assetCollectionServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: AssetCollectionComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(AssetCollection, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        assetCollectionServiceStub.retrieve.reset();
        assetCollectionServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        assetCollectionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(assetCollectionServiceStub.retrieve.called).toBeTruthy();
        expect(comp.assetCollections[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(assetCollectionServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        assetCollectionServiceStub.retrieve.reset();
        assetCollectionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(assetCollectionServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.assetCollections[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(assetCollectionServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        assetCollectionServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAssetCollection();
        await comp.$nextTick(); // clear components

        // THEN
        expect(assetCollectionServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(assetCollectionServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
