/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfEventUpdate from './type-of-event-update.vue';
import TypeOfEventService from './type-of-event.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfEventUpdateComponentType = InstanceType<typeof TypeOfEventUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfEventSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfEventUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfEvent Management Update Component', () => {
    let comp: TypeOfEventUpdateComponentType;
    let typeOfEventServiceStub: SinonStubbedInstance<TypeOfEventService>;

    beforeEach(() => {
      route = {};
      typeOfEventServiceStub = sinon.createStubInstance<TypeOfEventService>(TypeOfEventService);
      typeOfEventServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          typeOfEventService: () => typeOfEventServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfEventUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfEvent = typeOfEventSample;
        typeOfEventServiceStub.update.resolves(typeOfEventSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfEventServiceStub.update.calledWith(typeOfEventSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfEventServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfEventUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfEvent = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfEventServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfEventServiceStub.find.resolves(typeOfEventSample);
        typeOfEventServiceStub.retrieve.resolves([typeOfEventSample]);

        // WHEN
        route = {
          params: {
            typeOfEventId: '' + typeOfEventSample.id,
          },
        };
        const wrapper = shallowMount(TypeOfEventUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfEvent).toMatchObject(typeOfEventSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfEventServiceStub.find.resolves(typeOfEventSample);
        const wrapper = shallowMount(TypeOfEventUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
