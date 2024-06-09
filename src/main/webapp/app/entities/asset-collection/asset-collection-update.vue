<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.assetCollection.home.createOrEditLabel"
          data-cy="AssetCollectionCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="assetCollection.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="assetCollection.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.name')"
              for="asset-collection-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="asset-collection-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.fullFilenamePath')"
              for="asset-collection-fullFilenamePath"
            ></label>
            <input
              type="text"
              class="form-control"
              name="fullFilenamePath"
              id="asset-collection-fullFilenamePath"
              data-cy="fullFilenamePath"
              :class="{ valid: !v$.fullFilenamePath.$invalid, invalid: v$.fullFilenamePath.$invalid }"
              v-model="v$.fullFilenamePath.$model"
            />
            <div v-if="v$.fullFilenamePath.$anyDirty && v$.fullFilenamePath.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fullFilenamePath.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.activationStatus')"
              for="asset-collection-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="asset-collection-activationStatus"
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
            <label v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.asset')" for="asset-collection-asset"></label>
            <select
              class="form-control"
              id="asset-collection-assets"
              data-cy="asset"
              multiple
              name="asset"
              v-if="assetCollection.assets !== undefined"
              v-model="assetCollection.assets"
            >
              <option
                v-bind:value="getSelected(assetCollection.assets, assetOption, 'id')"
                v-for="assetOption in assets"
                :key="assetOption.id"
              >
                {{ assetOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.article')" for="asset-collection-article"></label>
            <select
              class="form-control"
              id="asset-collection-articles"
              data-cy="article"
              multiple
              name="article"
              v-if="assetCollection.articles !== undefined"
              v-model="assetCollection.articles"
            >
              <option
                v-bind:value="getSelected(assetCollection.articles, articleOption, 'id')"
                v-for="articleOption in articles"
                :key="articleOption.id"
              >
                {{ articleOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.event')" for="asset-collection-event"></label>
            <select
              class="form-control"
              id="asset-collection-events"
              data-cy="event"
              multiple
              name="event"
              v-if="assetCollection.events !== undefined"
              v-model="assetCollection.events"
            >
              <option
                v-bind:value="getSelected(assetCollection.events, eventOption, 'id')"
                v-for="eventOption in events"
                :key="eventOption.id"
              >
                {{ eventOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.activity')" for="asset-collection-activity"></label>
            <select
              class="form-control"
              id="asset-collection-activities"
              data-cy="activity"
              multiple
              name="activity"
              v-if="assetCollection.activities !== undefined"
              v-model="assetCollection.activities"
            >
              <option
                v-bind:value="getSelected(assetCollection.activities, activityOption, 'id')"
                v-for="activityOption in activities"
                :key="activityOption.id"
              >
                {{ activityOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.scheduledActivity')"
              for="asset-collection-scheduledActivity"
            ></label>
            <select
              class="form-control"
              id="asset-collection-scheduledActivities"
              data-cy="scheduledActivity"
              multiple
              name="scheduledActivity"
              v-if="assetCollection.scheduledActivities !== undefined"
              v-model="assetCollection.scheduledActivities"
            >
              <option
                v-bind:value="getSelected(assetCollection.scheduledActivities, scheduledActivityOption, 'id')"
                v-for="scheduledActivityOption in scheduledActivities"
                :key="scheduledActivityOption.id"
              >
                {{ scheduledActivityOption.id }}
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
<script lang="ts" src="./asset-collection-update.component.ts"></script>
