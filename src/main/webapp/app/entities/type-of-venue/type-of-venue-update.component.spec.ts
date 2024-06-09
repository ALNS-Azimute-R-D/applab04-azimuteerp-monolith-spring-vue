/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfVenueUpdate from './type-of-venue-update.vue';
import TypeOfVenueService from './type-of-venue.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfVenueUpdateComponentType = InstanceType<typeof TypeOfVenueUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfVenueSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeOfVenueUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TypeOfVenue Management Update Component', () => {
    let comp: TypeOfVenueUpdateComponentType;
    let typeOfVenueServiceStub: SinonStubbedInstance<TypeOfVenueService>;

    beforeEach(() => {
      route = {};
      typeOfVenueServiceStub = sinon.createStubInstance<TypeOfVenueService>(TypeOfVenueService);
      typeOfVenueServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeOfVenueService: () => typeOfVenueServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeOfVenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfVenue = typeOfVenueSample;
        typeOfVenueServiceStub.update.resolves(typeOfVenueSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfVenueServiceStub.update.calledWith(typeOfVenueSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeOfVenueServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeOfVenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.typeOfVenue = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeOfVenueServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeOfVenueServiceStub.find.resolves(typeOfVenueSample);
        typeOfVenueServiceStub.retrieve.resolves([typeOfVenueSample]);

        // WHEN
        route = {
          params: {
            typeOfVenueId: '' + typeOfVenueSample.id,
          },
        };
        const wrapper = shallowMount(TypeOfVenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfVenue).toMatchObject(typeOfVenueSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfVenueServiceStub.find.resolves(typeOfVenueSample);
        const wrapper = shallowMount(TypeOfVenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
