import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ScheduledActivityService from './scheduled-activity.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProgramService from '@/entities/program/program.service';
import { type IProgram } from '@/shared/model/program.model';
import ActivityService from '@/entities/activity/activity.service';
import { type IActivity } from '@/shared/model/activity.model';
import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import AssetCollectionService from '@/entities/asset-collection/asset-collection.service';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';
import { type IScheduledActivity, ScheduledActivity } from '@/shared/model/scheduled-activity.model';
import { ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ScheduledActivityUpdate',
  setup() {
    const scheduledActivityService = inject('scheduledActivityService', () => new ScheduledActivityService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const scheduledActivity: Ref<IScheduledActivity> = ref(new ScheduledActivity());

    const programService = inject('programService', () => new ProgramService());

    const programs: Ref<IProgram[]> = ref([]);

    const activityService = inject('activityService', () => new ActivityService());

    const activities: Ref<IActivity[]> = ref([]);

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

    const retrieveScheduledActivity = async scheduledActivityId => {
      try {
        const res = await scheduledActivityService().find(scheduledActivityId);
        res.startTime = new Date(res.startTime);
        res.endTime = new Date(res.endTime);
        scheduledActivity.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.scheduledActivityId) {
      retrieveScheduledActivity(route.params.scheduledActivityId);
    }

    const initRelationships = () => {
      programService()
        .retrieve()
        .then(res => {
          programs.value = res.data;
        });
      activityService()
        .retrieve()
        .then(res => {
          activities.value = res.data;
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
      customizedName: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 150 }).toString(), 150),
      },
      startTime: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      endTime: {},
      numberOfAttendees: {},
      averageEvaluationInStars: {},
      customAttributtesDetailsJSON: {
        maxLength: validations.maxLength(t$('entity.validation.maxlength', { max: 4096 }).toString(), 4096),
      },
      activationStatus: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      program: {},
      activity: {},
      responsiblePerson: {},
      assetCollections: {},
    };
    const v$ = useVuelidate(validationRules, scheduledActivity as any);
    v$.value.$validate();

    return {
      scheduledActivityService,
      alertService,
      scheduledActivity,
      previousState,
      activationStatusEnumValues,
      isSaving,
      currentLanguage,
      programs,
      activities,
      people,
      assetCollections,
      v$,
      ...useDateFormat({ entityRef: scheduledActivity }),
      t$,
    };
  },
  created(): void {
    this.scheduledActivity.assetCollections = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.scheduledActivity.id) {
        this.scheduledActivityService()
          .update(this.scheduledActivity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.scheduledActivity.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.scheduledActivityService()
          .create(this.scheduledActivity)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('azimuteErpSpringVueMonolith04App.scheduledActivity.created', { param: param.id }).toString(),
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
