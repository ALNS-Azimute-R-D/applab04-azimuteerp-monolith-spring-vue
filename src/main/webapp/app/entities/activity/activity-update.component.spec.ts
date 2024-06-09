/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import ActivityUpdate from './activity-update.vue';
import ActivityService from './activity.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import TypeOfActivityService from '@/entities/type-of-activity/type-of-activity.service';
import PersonService from '@/entities/person/person.service';
import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';

type ActivityUpdateComponentType = InstanceType<typeof ActivityUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const activitySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ActivityUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Activity Management Update Component', () => {
    let comp: ActivityUpdateComponentType;
    let activityServiceStub: SinonStubbedInstance<ActivityService>;

    beforeEach(() => {
      route = {};
      activityServiceStub = sinon.createStubInstance<ActivityService>(ActivityService);
      activityServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          activityService: () => activityServiceStub,
          typeOfActivityService: () =>
            sinon.createStubInstance<TypeOfActivityService>(TypeOfActivityService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          personService: () =>
            sinon.createStubInstance<PersonService>(PersonService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          assetCollectionService: () =>
            sinon.createStubInstance<AssetCollectionService>(AssetCollectionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(ActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.activity = activitySample;
        activityServiceStub.update.resolves(activitySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(activityServiceStub.update.calledWith(activitySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        activityServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.activity = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(activityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        activityServiceStub.find.resolves(activitySample);
        activityServiceStub.retrieve.resolves([activitySample]);

        // WHEN
        route = {
          params: {
            activityId: '' + activitySample.id,
          },
        };
        const wrapper = shallowMount(ActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.activity).toMatchObject(activitySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        activityServiceStub.find.resolves(activitySample);
        const wrapper = shallowMount(ActivityUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
