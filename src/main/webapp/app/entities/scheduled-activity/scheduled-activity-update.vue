<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.scheduledActivity.home.createOrEditLabel"
          data-cy="ScheduledActivityCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="scheduledActivity.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="scheduledActivity.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.customizedName')"
              for="scheduled-activity-customizedName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customizedName"
              id="scheduled-activity-customizedName"
              data-cy="customizedName"
              :class="{ valid: !v$.customizedName.$invalid, invalid: v$.customizedName.$invalid }"
              v-model="v$.customizedName.$model"
            />
            <div v-if="v$.customizedName.$anyDirty && v$.customizedName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customizedName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.startTime')"
              for="scheduled-activity-startTime"
            ></label>
            <div class="d-flex">
              <input
                id="scheduled-activity-startTime"
                data-cy="startTime"
                type="datetime-local"
                class="form-control"
                name="startTime"
                :class="{ valid: !v$.startTime.$invalid, invalid: v$.startTime.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.startTime.$model)"
                @change="updateInstantField('startTime', $event)"
              />
            </div>
            <div v-if="v$.startTime.$anyDirty && v$.startTime.$invalid">
              <small class="form-text text-danger" v-for="error of v$.startTime.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.endTime')"
              for="scheduled-activity-endTime"
            ></label>
            <div class="d-flex">
              <input
                id="scheduled-activity-endTime"
                data-cy="endTime"
                type="datetime-local"
                class="form-control"
                name="endTime"
                :class="{ valid: !v$.endTime.$invalid, invalid: v$.endTime.$invalid }"
                :value="convertDateTimeFromServer(v$.endTime.$model)"
                @change="updateInstantField('endTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.numberOfAttendees')"
              for="scheduled-activity-numberOfAttendees"
            ></label>
            <input
              type="number"
              class="form-control"
              name="numberOfAttendees"
              id="scheduled-activity-numberOfAttendees"
              data-cy="numberOfAttendees"
              :class="{ valid: !v$.numberOfAttendees.$invalid, invalid: v$.numberOfAttendees.$invalid }"
              v-model.number="v$.numberOfAttendees.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.averageEvaluationInStars')"
              for="scheduled-activity-averageEvaluationInStars"
            ></label>
            <input
              type="number"
              class="form-control"
              name="averageEvaluationInStars"
              id="scheduled-activity-averageEvaluationInStars"
              data-cy="averageEvaluationInStars"
              :class="{ valid: !v$.averageEvaluationInStars.$invalid, invalid: v$.averageEvaluationInStars.$invalid }"
              v-model.number="v$.averageEvaluationInStars.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.customAttributtesDetailsJSON')"
              for="scheduled-activity-customAttributtesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributtesDetailsJSON"
              id="scheduled-activity-customAttributtesDetailsJSON"
              data-cy="customAttributtesDetailsJSON"
              :class="{ valid: !v$.customAttributtesDetailsJSON.$invalid, invalid: v$.customAttributtesDetailsJSON.$invalid }"
              v-model="v$.customAttributtesDetailsJSON.$model"
            />
            <div v-if="v$.customAttributtesDetailsJSON.$anyDirty && v$.customAttributtesDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customAttributtesDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.activationStatus')"
              for="scheduled-activity-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="scheduled-activity-activationStatus"
              data-cy="activationStatus"
              required
            >
              <option
                v-for="activationStatusEnum in activationStatusEnumValues"
                :key="activationStatusEnum"
                v-bind:value="activationStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + activationStatusEnum)"
              >
                {{ activationStatusEnum }}
              </option>
            </select>
            <div v-if="v$.activationStatus.$anyDirty && v$.activationStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.activationStatus.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.program')"
              for="scheduled-activity-program"
            ></label>
            <select
              class="form-control"
              id="scheduled-activity-program"
              data-cy="program"
              name="program"
              v-model="scheduledActivity.program"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  scheduledActivity.program && programOption.id === scheduledActivity.program.id ? scheduledActivity.program : programOption
                "
                v-for="programOption in programs"
                :key="programOption.id"
              >
                {{ programOption.acronym }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.activity')"
              for="scheduled-activity-activity"
            ></label>
            <select
              class="form-control"
              id="scheduled-activity-activity"
              data-cy="activity"
              name="activity"
              v-model="scheduledActivity.activity"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  scheduledActivity.activity && activityOption.id === scheduledActivity.activity.id
                    ? scheduledActivity.activity
                    : activityOption
                "
                v-for="activityOption in activities"
                :key="activityOption.id"
              >
                {{ activityOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.responsiblePerson')"
              for="scheduled-activity-responsiblePerson"
            ></label>
            <select
              class="form-control"
              id="scheduled-activity-responsiblePerson"
              data-cy="responsiblePerson"
              name="responsiblePerson"
              v-model="scheduledActivity.responsiblePerson"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  scheduledActivity.responsiblePerson && personOption.id === scheduledActivity.responsiblePerson.id
                    ? scheduledActivity.responsiblePerson
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.fullname }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.assetCollection')"
              for="scheduled-activity-assetCollection"
            ></label>
            <select
              class="form-control"
              id="scheduled-activity-assetCollections"
              data-cy="assetCollection"
              multiple
              name="assetCollection"
              v-if="scheduledActivity.assetCollections !== undefined"
              v-model="scheduledActivity.assetCollections"
            >
              <option
                v-bind:value="getSelected(scheduledActivity.assetCollections, assetCollectionOption, 'id')"
                v-for="assetCollectionOption in assetCollections"
                :key="assetCollectionOption.id"
              >
                {{ assetCollectionOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./scheduled-activity-update.component.ts"></script>
