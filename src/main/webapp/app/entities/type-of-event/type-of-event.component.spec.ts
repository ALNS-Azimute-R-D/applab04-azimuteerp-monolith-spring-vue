/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TypeOfEvent from './type-of-event.vue';
import TypeOfEventService from './type-of-event.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfEventComponentType = InstanceType<typeof TypeOfEvent>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TypeOfEvent Management Component', () => {
    let typeOfEventServiceStub: SinonStubbedInstance<TypeOfEventService>;
    let mountOptions: MountingOptions<TypeOfEventComponentType>['global'];

    beforeEach(() => {
      typeOfEventServiceStub = sinon.createStubInstance<TypeOfEventService>(TypeOfEventService);
      typeOfEventServiceStub.retrieve.resolves({ headers: {} });

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
          typeOfEventService: () => typeOfEventServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfEventServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TypeOfEvent, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfEventServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.typeOfEvents[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TypeOfEvent, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfEventServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TypeOfEventComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TypeOfEvent, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeOfEventServiceStub.retrieve.reset();
        typeOfEventServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        typeOfEventServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(typeOfEventServiceStub.retrieve.called).toBeTruthy();
        expect(comp.typeOfEvents[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(typeOfEventServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        typeOfEventServiceStub.retrieve.reset();
        typeOfEventServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(typeOfEventServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.typeOfEvents[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(typeOfEventServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeOfEventServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTypeOfEvent();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeOfEventServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeOfEventServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
