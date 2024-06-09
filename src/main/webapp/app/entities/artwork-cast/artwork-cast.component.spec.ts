/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ArtworkCast from './artwork-cast.vue';
import ArtworkCastService from './artwork-cast.service';
import AlertService from '@/shared/alert/alert.service';

type ArtworkCastComponentType = InstanceType<typeof ArtworkCast>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ArtworkCast Management Component', () => {
    let artworkCastServiceStub: SinonStubbedInstance<ArtworkCastService>;
    let mountOptions: MountingOptions<ArtworkCastComponentType>['global'];

    beforeEach(() => {
      artworkCastServiceStub = sinon.createStubInstance<ArtworkCastService>(ArtworkCastService);
      artworkCastServiceStub.retrieve.resolves({ headers: {} });

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
          artworkCastService: () => artworkCastServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        artworkCastServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ArtworkCast, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(artworkCastServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.artworkCasts[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(ArtworkCast, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(artworkCastServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: ArtworkCastComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ArtworkCast, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        artworkCastServiceStub.retrieve.reset();
        artworkCastServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        artworkCastServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(artworkCastServiceStub.retrieve.called).toBeTruthy();
        expect(comp.artworkCasts[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(artworkCastServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        artworkCastServiceStub.retrieve.reset();
        artworkCastServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(artworkCastServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.artworkCasts[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(artworkCastServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        artworkCastServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeArtworkCast();
        await comp.$nextTick(); // clear components

        // THEN
        expect(artworkCastServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(artworkCastServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
