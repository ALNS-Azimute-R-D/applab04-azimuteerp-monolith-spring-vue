/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TypeOfArtmedia from './type-of-artmedia.vue';
import TypeOfArtmediaService from './type-of-artmedia.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfArtmediaComponentType = InstanceType<typeof TypeOfArtmedia>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TypeOfArtmedia Management Component', () => {
    let typeOfArtmediaServiceStub: SinonStubbedInstance<TypeOfArtmediaService>;
    let mountOptions: MountingOptions<TypeOfArtmediaComponentType>['global'];

    beforeEach(() => {
      typeOfArtmediaServiceStub = sinon.createStubInstance<TypeOfArtmediaService>(TypeOfArtmediaService);
      typeOfArtmediaServiceStub.retrieve.resolves({ headers: {} });

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
          typeOfArtmediaService: () => typeOfArtmediaServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfArtmediaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TypeOfArtmedia, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfArtmediaServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.typeOfArtmedias[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TypeOfArtmedia, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfArtmediaServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TypeOfArtmediaComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TypeOfArtmedia, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeOfArtmediaServiceStub.retrieve.reset();
        typeOfArtmediaServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        typeOfArtmediaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(typeOfArtmediaServiceStub.retrieve.called).toBeTruthy();
        expect(comp.typeOfArtmedias[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(typeOfArtmediaServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        typeOfArtmediaServiceStub.retrieve.reset();
        typeOfArtmediaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(typeOfArtmediaServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.typeOfArtmedias[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(typeOfArtmediaServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeOfArtmediaServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTypeOfArtmedia();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeOfArtmediaServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeOfArtmediaServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
