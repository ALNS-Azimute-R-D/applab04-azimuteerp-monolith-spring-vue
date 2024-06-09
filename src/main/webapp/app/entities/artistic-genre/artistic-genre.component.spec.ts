/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ArtisticGenre from './artistic-genre.vue';
import ArtisticGenreService from './artistic-genre.service';
import AlertService from '@/shared/alert/alert.service';

type ArtisticGenreComponentType = InstanceType<typeof ArtisticGenre>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ArtisticGenre Management Component', () => {
    let artisticGenreServiceStub: SinonStubbedInstance<ArtisticGenreService>;
    let mountOptions: MountingOptions<ArtisticGenreComponentType>['global'];

    beforeEach(() => {
      artisticGenreServiceStub = sinon.createStubInstance<ArtisticGenreService>(ArtisticGenreService);
      artisticGenreServiceStub.retrieve.resolves({ headers: {} });

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
          artisticGenreService: () => artisticGenreServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        artisticGenreServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ArtisticGenre, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(artisticGenreServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.artisticGenres[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(ArtisticGenre, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(artisticGenreServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: ArtisticGenreComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ArtisticGenre, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        artisticGenreServiceStub.retrieve.reset();
        artisticGenreServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        artisticGenreServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(artisticGenreServiceStub.retrieve.called).toBeTruthy();
        expect(comp.artisticGenres[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(artisticGenreServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        artisticGenreServiceStub.retrieve.reset();
        artisticGenreServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(artisticGenreServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.artisticGenres[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(artisticGenreServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        artisticGenreServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeArtisticGenre();
        await comp.$nextTick(); // clear components

        // THEN
        expect(artisticGenreServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(artisticGenreServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
