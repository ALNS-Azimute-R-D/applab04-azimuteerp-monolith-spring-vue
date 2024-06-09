import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ActivityService from './activity.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import TypeOfActivityService from '@/entities/type-of-activity/type-of-activity.service';
import { type ITypeOfActivity } from '@/shared/model/type-of-activity.model';
import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import { type IActivity, Activity } from '@/shared/model/activity.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ActivityUpdate',
  setup() {
    const activityService = inject('activityService', () => new ActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const activity: Ref<IActivity> = ref(new Activity());

    const typeOfActivityService = inject('typeOfActivityService', () => new TypeOfActivityService());

    const typeOfActivities: Ref<ITypeOfActivity[]> = ref([]);

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);

    const assetCollectionService = inject('assetCollectionService', () => new AssetCollectionService());

    const assetCollections: Ref<IAssetCollection[]> = ref([]);
    const activationStatusEnumValues: Ref<string[]> = ref(Object.keys(ActivationStatusEnum));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveActivity = async activityId => {
      try {
        const res = await activityService().find(activityId);
        res.createdAt = new Date(res.createdAt);
        activity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.activityId) {
      retrieveActivity(route.params.activityId);
    }

    const initRelationships = () => {
      typeOfActivityService()
        .retrieve()
        .then(res => {
          typeOfActivities.value = res.data;
        });
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
      assetCollectionService()
        .retrieve()
        .then(res => {
          assetCollections.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 120 }).toString(), 120),
      },
      shortDescription: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 512 }).toString(), 512),
      },
      fullDescription: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 2048 }).toString(), 2048),
      },
      mainGoals: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 1024 }).toString(), 1024),
      },
      estimatedDurationTime: {},
      lastPerformedDate: {},
      createdAt: {},
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      typeOfActivity: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      createdByUser: {},
      assetCollections: {},
    };
    const v$ = useVuelidate(validationRules, activity as any);
    v$.value.$validate();

    return {
      activityService,
      alertService,
      activity,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      typeOfActivities,
      people,
      assetCollections,
      v$,
      ...useDateFormat({ entityRef: activity }),
      t$,
    };
  },
  created(): void {
    this.activity.assetCollections = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.activity.id) {
        this.activityService()
          .update(this.activity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.activity.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.activityService()
          .create(this.activity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.activity.created', { param: param.id }).toString());
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
