/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TypeOfVenue from './type-of-venue.vue';
import TypeOfVenueService from './type-of-venue.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfVenueComponentType = InstanceType<typeof TypeOfVenue>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TypeOfVenue Management Component', () => {
    let typeOfVenueServiceStub: SinonStubbedInstance<TypeOfVenueService>;
    let mountOptions: MountingOptions<TypeOfVenueComponentType>['global'];

    beforeEach(() => {
      typeOfVenueServiceStub = sinon.createStubInstance<TypeOfVenueService>(TypeOfVenueService);
      typeOfVenueServiceStub.retrieve.resolves({ headers: {} });

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
          typeOfVenueService: () => typeOfVenueServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfVenueServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TypeOfVenue, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfVenueServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.typeOfVenues[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TypeOfVenue, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfVenueServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TypeOfVenueComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TypeOfVenue, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeOfVenueServiceStub.retrieve.reset();
        typeOfVenueServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        typeOfVenueServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(typeOfVenueServiceStub.retrieve.called).toBeTruthy();
        expect(comp.typeOfVenues[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(typeOfVenueServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        typeOfVenueServiceStub.retrieve.reset();
        typeOfVenueServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(typeOfVenueServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.typeOfVenues[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(typeOfVenueServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeOfVenueServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTypeOfVenue();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeOfVenueServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeOfVenueServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
