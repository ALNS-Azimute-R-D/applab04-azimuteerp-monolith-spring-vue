/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import CommonLocality from './common-locality.vue';
import CommonLocalityService from './common-locality.service';
import AlertService from '@/shared/alert/alert.service';

type CommonLocalityComponentType = InstanceType<typeof CommonLocality>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('CommonLocality Management Component', () => {
    let commonLocalityServiceStub: SinonStubbedInstance<CommonLocalityService>;
    let mountOptions: MountingOptions<CommonLocalityComponentType>['global'];

    beforeEach(() => {
      commonLocalityServiceStub = sinon.createStubInstance<CommonLocalityService>(CommonLocalityService);
      commonLocalityServiceStub.retrieve.resolves({ headers: {} });

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
          commonLocalityService: () => commonLocalityServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        commonLocalityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(CommonLocality, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(commonLocalityServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.commonLocalities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(CommonLocality, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(commonLocalityServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: CommonLocalityComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(CommonLocality, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        commonLocalityServiceStub.retrieve.reset();
        commonLocalityServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        commonLocalityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(commonLocalityServiceStub.retrieve.called).toBeTruthy();
        expect(comp.commonLocalities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(commonLocalityServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        commonLocalityServiceStub.retrieve.reset();
        commonLocalityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(commonLocalityServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.commonLocalities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(commonLocalityServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        commonLocalityServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeCommonLocality();
        await comp.$nextTick(); // clear components

        // THEN
        expect(commonLocalityServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(commonLocalityServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
