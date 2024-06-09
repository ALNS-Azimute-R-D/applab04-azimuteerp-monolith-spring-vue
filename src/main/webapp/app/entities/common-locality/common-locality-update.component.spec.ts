/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CommonLocalityUpdate from './common-locality-update.vue';
import CommonLocalityService from './common-locality.service';
import AlertService from '@/shared/alert/alert.service';

import DistrictService from '@/entities/district/district.service';

type CommonLocalityUpdateComponentType = InstanceType<typeof CommonLocalityUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const commonLocalitySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CommonLocalityUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CommonLocality Management Update Component', () => {
    let comp: CommonLocalityUpdateComponentType;
    let commonLocalityServiceStub: SinonStubbedInstance<CommonLocalityService>;

    beforeEach(() => {
      route = {};
      commonLocalityServiceStub = sinon.createStubInstance<CommonLocalityService>(CommonLocalityService);
      commonLocalityServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          commonLocalityService: () => commonLocalityServiceStub,
          districtService: () =>
            sinon.createStubInstance<DistrictService>(DistrictService, {
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
        const wrapper = shallowMount(CommonLocalityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.commonLocality = commonLocalitySample;
        commonLocalityServiceStub.update.resolves(commonLocalitySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commonLocalityServiceStub.update.calledWith(commonLocalitySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        commonLocalityServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CommonLocalityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.commonLocality = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commonLocalityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        commonLocalityServiceStub.find.resolves(commonLocalitySample);
        commonLocalityServiceStub.retrieve.resolves([commonLocalitySample]);

        // WHEN
        route = {
          params: {
            commonLocalityId: '' + commonLocalitySample.id,
          },
        };
        const wrapper = shallowMount(CommonLocalityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.commonLocality).toMatchObject(commonLocalitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        commonLocalityServiceStub.find.resolves(commonLocalitySample);
        const wrapper = shallowMount(CommonLocalityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
