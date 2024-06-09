/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TownCityUpdate from './town-city-update.vue';
import TownCityService from './town-city.service';
import AlertService from '@/shared/alert/alert.service';

import ProvinceService from '@/entities/province/province.service';

type TownCityUpdateComponentType = InstanceType<typeof TownCityUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const townCitySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TownCityUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TownCity Management Update Component', () => {
    let comp: TownCityUpdateComponentType;
    let townCityServiceStub: SinonStubbedInstance<TownCityService>;

    beforeEach(() => {
      route = {};
      townCityServiceStub = sinon.createStubInstance<TownCityService>(TownCityService);
      townCityServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          townCityService: () => townCityServiceStub,
          provinceService: () =>
            sinon.createStubInstance<ProvinceService>(ProvinceService, {
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
        const wrapper = shallowMount(TownCityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.townCity = townCitySample;
        townCityServiceStub.update.resolves(townCitySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(townCityServiceStub.update.calledWith(townCitySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        townCityServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TownCityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.townCity = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(townCityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        townCityServiceStub.find.resolves(townCitySample);
        townCityServiceStub.retrieve.resolves([townCitySample]);

        // WHEN
        route = {
          params: {
            townCityId: '' + townCitySample.id,
          },
        };
        const wrapper = shallowMount(TownCityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.townCity).toMatchObject(townCitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        townCityServiceStub.find.resolves(townCitySample);
        const wrapper = shallowMount(TownCityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
