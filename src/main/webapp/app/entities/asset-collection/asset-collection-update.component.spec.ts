/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AssetCollectionUpdate from './asset-collection-update.vue';
import AssetCollectionService from './asset-collection.service';
import AlertService from '@/shared/alert/alert.service';

import AssetService from '@/entities/asset/asset.service';
import ArticleService from '@/entities/article/article.service';
import EventService from '@/entities/event/event.service';
import ActivityService from '@/entities/activity/activity.service';
import ScheduledActivityService from '@/entities/scheduled-activity/scheduled-activity.service';

type AssetCollectionUpdateComponentType = InstanceType<typeof AssetCollectionUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const assetCollectionSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AssetCollectionUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('AssetCollection Management Update Component', () => {
    let comp: AssetCollectionUpdateComponentType;
    let assetCollectionServiceStub: SinonStubbedInstance<AssetCollectionService>;

    beforeEach(() => {
      route = {};
      assetCollectionServiceStub = sinon.createStubInstance<AssetCollectionService>(AssetCollectionService);
      assetCollectionServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          assetCollectionService: () => assetCollectionServiceStub,
          assetService: () =>
            sinon.createStubInstance<AssetService>(AssetService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          articleService: () =>
            sinon.createStubInstance<ArticleService>(ArticleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          eventService: () =>
            sinon.createStubInstance<EventService>(EventService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          activityService: () =>
            sinon.createStubInstance<ActivityService>(ActivityService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          scheduledActivityService: () =>
            sinon.createStubInstance<ScheduledActivityService>(ScheduledActivityService, {
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
        const wrapper = shallowMount(AssetCollectionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.assetCollection = assetCollectionSample;
        assetCollectionServiceStub.update.resolves(assetCollectionSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetCollectionServiceStub.update.calledWith(assetCollectionSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        assetCollectionServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AssetCollectionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.assetCollection = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(assetCollectionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        assetCollectionServiceStub.find.resolves(assetCollectionSample);
        assetCollectionServiceStub.retrieve.resolves([assetCollectionSample]);

        // WHEN
        route = {
          params: {
            assetCollectionId: '' + assetCollectionSample.id,
          },
        };
        const wrapper = shallowMount(AssetCollectionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.assetCollection).toMatchObject(assetCollectionSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        assetCollectionServiceStub.find.resolves(assetCollectionSample);
        const wrapper = shallowMount(AssetCollectionUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
