<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.activity.home.createOrEditLabel"
          data-cy="ActivityCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.activity.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="activity.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="activity.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.activity.name')" for="activity-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="activity-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
              required
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.shortDescription')"
              for="activity-shortDescription"
            ></label>
            <input
              type="text"
              class="form-control"
              name="shortDescription"
              id="activity-shortDescription"
              data-cy="shortDescription"
              :class="{ valid: !v$.shortDescription.$invalid, invalid: v$.shortDescription.$invalid }"
              v-model="v$.shortDescription.$model"
            />
            <div v-if="v$.shortDescription.$anyDirty && v$.shortDescription.$invalid">
              <small class="form-text text-danger" v-for="error of v$.shortDescription.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.fullDescription')"
              for="activity-fullDescription"
            ></label>
            <input
              type="text"
              class="form-control"
              name="fullDescription"
              id="activity-fullDescription"
              data-cy="fullDescription"
              :class="{ valid: !v$.fullDescription.$invalid, invalid: v$.fullDescription.$invalid }"
              v-model="v$.fullDescription.$model"
            />
            <div v-if="v$.fullDescription.$anyDirty && v$.fullDescription.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fullDescription.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.mainGoals')"
              for="activity-mainGoals"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainGoals"
              id="activity-mainGoals"
              data-cy="mainGoals"
              :class="{ valid: !v$.mainGoals.$invalid, invalid: v$.mainGoals.$invalid }"
              v-model="v$.mainGoals.$model"
            />
            <div v-if="v$.mainGoals.$anyDirty && v$.mainGoals.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainGoals.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.estimatedDurationTime')"
              for="activity-estimatedDurationTime"
            ></label>
            <input
              type="text"
              class="form-control"
              name="estimatedDurationTime"
              id="activity-estimatedDurationTime"
              data-cy="estimatedDurationTime"
              :class="{ valid: !v$.estimatedDurationTime.$invalid, invalid: v$.estimatedDurationTime.$invalid }"
              v-model="v$.estimatedDurationTime.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.lastPerformedDate')"
              for="activity-lastPerformedDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="activity-lastPerformedDate"
                  v-model="v$.lastPerformedDate.$model"
                  name="lastPerformedDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="activity-lastPerformedDate"
                data-cy="lastPerformedDate"
                type="text"
                class="form-control"
                name="lastPerformedDate"
                :class="{ valid: !v$.lastPerformedDate.$invalid, invalid: v$.lastPerformedDate.$invalid }"
                v-model="v$.lastPerformedDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.createdAt')"
              for="activity-createdAt"
            ></label>
            <div class="d-flex">
              <input
                id="activity-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !v$.createdAt.$invalid, invalid: v$.createdAt.$invalid }"
                :value="convertDateTimeFromServer(v$.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.activationStatus')"
              for="activity-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="activity-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.typeOfActivity')"
              for="activity-typeOfActivity"
            ></label>
            <select
              class="form-control"
              id="activity-typeOfActivity"
              data-cy="typeOfActivity"
              name="typeOfActivity"
              v-model="activity.typeOfActivity"
              required
            >
              <option v-if="!activity.typeOfActivity" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  activity.typeOfActivity && typeOfActivityOption.id === activity.typeOfActivity.id
                    ? activity.typeOfActivity
                    : typeOfActivityOption
                "
                v-for="typeOfActivityOption in typeOfActivities"
                :key="typeOfActivityOption.id"
              >
                {{ typeOfActivityOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfActivity.$anyDirty && v$.typeOfActivity.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfActivity.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.activity.createdByUser')"
              for="activity-createdByUser"
            ></label>
            <select
              class="form-control"
              id="activity-createdByUser"
              data-cy="createdByUser"
              name="createdByUser"
              v-model="activity.createdByUser"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  activity.createdByUser && personOption.id === activity.createdByUser.id ? activity.createdByUser : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.fullname }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.activity.assetCollection')" for="activity-assetCollection"></label>
            <select
              class="form-control"
              id="activity-assetCollections"
              data-cy="assetCollection"
              multiple
              name="assetCollection"
              v-if="activity.assetCollections !== undefined"
              v-model="activity.assetCollections"
            >
              <option
                v-bind:value="getSelected(activity.assetCollections, assetCollectionOption, 'id')"
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
<script lang="ts" src="./activity-update.component.ts"></script>
