/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TypeOfPerson from './type-of-person.vue';
import TypeOfPersonService from './type-of-person.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfPersonComponentType = InstanceType<typeof TypeOfPerson>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TypeOfPerson Management Component', () => {
    let typeOfPersonServiceStub: SinonStubbedInstance<TypeOfPersonService>;
    let mountOptions: MountingOptions<TypeOfPersonComponentType>['global'];

    beforeEach(() => {
      typeOfPersonServiceStub = sinon.createStubInstance<TypeOfPersonService>(TypeOfPersonService);
      typeOfPersonServiceStub.retrieve.resolves({ headers: {} });

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
          typeOfPersonService: () => typeOfPersonServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfPersonServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TypeOfPerson, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfPersonServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.typeOfPeople[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TypeOfPerson, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeOfPersonServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TypeOfPersonComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TypeOfPerson, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeOfPersonServiceStub.retrieve.reset();
        typeOfPersonServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        typeOfPersonServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(typeOfPersonServiceStub.retrieve.called).toBeTruthy();
        expect(comp.typeOfPeople[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(typeOfPersonServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        typeOfPersonServiceStub.retrieve.reset();
        typeOfPersonServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(typeOfPersonServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.typeOfPeople[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(typeOfPersonServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeOfPersonServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTypeOfPerson();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeOfPersonServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeOfPersonServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
