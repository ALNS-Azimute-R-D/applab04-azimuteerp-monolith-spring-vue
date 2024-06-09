import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AssetCollectionService from './asset-collection.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AssetService from '@/entities/asset/asset.service';
import { type IAsset } from '@/shared/model/asset.model';
import ArticleService from '@/entities/article/article.service';
import { type IArticle } from '@/shared/model/article.model';
import EventService from '@/entities/event/event.service';
import { type IEvent } from '@/shared/model/event.model';
import ActivityService from '@/entities/activity/activity.service';
import { type IActivity } from '@/shared/model/activity.model';
import ScheduledActivityService from '@/entities/scheduled-activity/scheduled-activity.service';
import { type IScheduledActivity } from '@/shared/model/scheduled-activity.model';
import { type IAssetCollection, AssetCollection } from '@/shared/model/asset-collection.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AssetCollectionUpdate',
  setup() {
    const assetCollectionService = inject('assetCollectionService', () => new AssetCollectionService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const assetCollection: Ref<IAssetCollection> = ref(new AssetCollection());

    const assetService = inject('assetService', () => new AssetService());

    const assets: Ref<IAsset[]> = ref([]);

    const articleService = inject('articleService', () => new ArticleService());

    const articles: Ref<IArticle[]> = ref([]);

    const eventService = inject('eventService', () => new EventService());

    const events: Ref<IEvent[]> = ref([]);

    const activityService = inject('activityService', () => new ActivityService());

    const activities: Ref<IActivity[]> = ref([]);

    const scheduledActivityService = inject('scheduledActivityService', () => new ScheduledActivityService());

    const scheduledActivities: Ref<IScheduledActivity[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAssetCollection = async assetCollectionId => {
      try {
        const res = await assetCollectionService().find(assetCollectionId);
        assetCollection.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.assetCollectionId) {
      retrieveAssetCollection(route.params.assetCollectionId);
    }

    const initRelationships = () => {
      assetService()
        .retrieve()
        .then(res => {
          assets.value = res.data;
        });
      articleService()
        .retrieve()
        .then(res => {
          articles.value = res.data;
        });
      eventService()
        .retrieve()
        .then(res => {
          events.value = res.data;
        });
      activityService()
        .retrieve()
        .then(res => {
          activities.value = res.data;
        });
      scheduledActivityService()
        .retrieve()
        .then(res => {
          scheduledActivities.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      fullFilenamePath: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      assets: {},
      articles: {},
      events: {},
      activities: {},
      scheduledActivities: {},
    };
    const v$ = useVuelidate(validationRules, assetCollection as any);
    v$.value.$validate();

    return {
      assetCollectionService,
      alertService,
      assetCollection,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      assets,
      articles,
      events,
      activities,
      scheduledActivities,
      v$,
      t$,
    };
  },
  created(): void {
    this.assetCollection.assets = [];
    this.assetCollection.articles = [];
    this.assetCollection.events = [];
    this.assetCollection.activities = [];
    this.assetCollection.scheduledActivities = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.assetCollection.id) {
        this.assetCollectionService()
          .update(this.assetCollection)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.assetCollection.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.assetCollectionService()
          .create(this.assetCollection)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.assetCollection.created', { param: param.id }).toString(),
            );
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
