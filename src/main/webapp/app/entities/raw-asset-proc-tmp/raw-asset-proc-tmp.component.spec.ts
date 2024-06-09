/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RawAssetProcTmp from './raw-asset-proc-tmp.vue';
import RawAssetProcTmpService from './raw-asset-proc-tmp.service';
import AlertService from '@/shared/alert/alert.service';

type RawAssetProcTmpComponentType = InstanceType<typeof RawAssetProcTmp>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RawAssetProcTmp Management Component', () => {
    let rawAssetProcTmpServiceStub: SinonStubbedInstance<RawAssetProcTmpService>;
    let mountOptions: MountingOptions<RawAssetProcTmpComponentType>['global'];

    beforeEach(() => {
      rawAssetProcTmpServiceStub = sinon.createStubInstance<RawAssetProcTmpService>(RawAssetProcTmpService);
      rawAssetProcTmpServiceStub.retrieve.resolves({ headers: {} });

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
          rawAssetProcTmpService: () => rawAssetProcTmpServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rawAssetProcTmpServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(RawAssetProcTmp, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rawAssetProcTmpServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rawAssetProcTmps[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(RawAssetProcTmp, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rawAssetProcTmpServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: RawAssetProcTmpComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RawAssetProcTmp, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rawAssetProcTmpServiceStub.retrieve.reset();
        rawAssetProcTmpServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        rawAssetProcTmpServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(rawAssetProcTmpServiceStub.retrieve.called).toBeTruthy();
        expect(comp.rawAssetProcTmps[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(rawAssetProcTmpServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        rawAssetProcTmpServiceStub.retrieve.reset();
        rawAssetProcTmpServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(rawAssetProcTmpServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.rawAssetProcTmps[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(rawAssetProcTmpServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rawAssetProcTmpServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRawAssetProcTmp();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rawAssetProcTmpServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rawAssetProcTmpServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
