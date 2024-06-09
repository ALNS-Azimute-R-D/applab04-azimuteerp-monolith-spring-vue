/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TypeOfArtist from './type-of-artist.vue';
import TypeOfArtistService from './type-of-artist.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfArtistComponentType = InstanceType<typeof TypeOfArtist>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TypeOfArtist Management Component', () => {
    let typeOfArtistServiceStub: SinonStubbedInstance<TypeOfArtistService>;
    let mountOptions: MountingOptions<TypeOfArtistComponentType>['global'];

    beforeEach(() => {
      typeOfArtistServiceStub = sinon.createStubInstance<TypeOfArtistService>(TypeOfArtistService);
      typeOfArtistServiceStub.retrieve.resolves({ headers: {} });

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
          typeOfArtistService: () => typeOfArtistServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfArtistServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TypeOfArtist, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfArtistServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.typeOfArtists[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TypeOfArtist, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfArtistServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TypeOfArtistComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TypeOfArtist, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeOfArtistServiceStub.retrieve.reset();
        typeOfArtistServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        typeOfArtistServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(typeOfArtistServiceStub.retrieve.called).toBeTruthy();
        expect(comp.typeOfArtists[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(typeOfArtistServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        typeOfArtistServiceStub.retrieve.reset();
        typeOfArtistServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(typeOfArtistServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.typeOfArtists[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(typeOfArtistServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeOfArtistServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTypeOfArtist();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeOfArtistServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeOfArtistServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
