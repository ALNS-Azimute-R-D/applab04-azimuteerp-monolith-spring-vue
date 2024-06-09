/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import VenueUpdate from './venue-update.vue';
import VenueService from './venue.service';
import AlertService from '@/shared/alert/alert.service';

import TypeOfVenueService from '@/entities/type-of-venue/type-of-venue.service';
import CommonLocalityService from '@/entities/common-locality/common-locality.service';

type VenueUpdateComponentType = InstanceType<typeof VenueUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const venueSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<VenueUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Venue Management Update Component', () => {
    let comp: VenueUpdateComponentType;
    let venueServiceStub: SinonStubbedInstance<VenueService>;

    beforeEach(() => {
      route = {};
      venueServiceStub = sinon.createStubInstance<VenueService>(VenueService);
      venueServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          venueService: () => venueServiceStub,
          typeOfVenueService: () =>
            sinon.createStubInstance<TypeOfVenueService>(TypeOfVenueService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          commonLocalityService: () =>
            sinon.createStubInstance<CommonLocalityService>(CommonLocalityService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(VenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.venue = venueSample;
        venueServiceStub.update.resolves(venueSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(venueServiceStub.update.calledWith(venueSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        venueServiceStub.create.resolves(entity);
        const wrapper = shallowMount(VenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.venue = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(venueServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        venueServiceStub.find.resolves(venueSample);
        venueServiceStub.retrieve.resolves([venueSample]);

        // WHEN
        route = {
          params: {
            venueId: '' + venueSample.id,
          },
        };
        const wrapper = shallowMount(VenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.venue).toMatchObject(venueSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        venueServiceStub.find.resolves(venueSample);
        const wrapper = shallowMount(VenueUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
