<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.event.home.createOrEditLabel"
          data-cy="EventCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.event.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="event.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="event.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.event.acronym')" for="event-acronym"></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="event-acronym"
              data-cy="acronym"
              :class="{ valid: !v$.acronym.$invalid, invalid: v$.acronym.$invalid }"
              v-model="v$.acronym.$model"
            />
            <div v-if="v$.acronym.$anyDirty && v$.acronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.event.name')" for="event-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="event-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.event.description')"
              for="event-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="event-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
              required
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.event.fullDescription')"
              for="event-fullDescription"
            ></label>
            <input
              type="text"
              class="form-control"
              name="fullDescription"
              id="event-fullDescription"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.event.startTime')" for="event-startTime"></label>
            <div class="d-flex">
              <input
                id="event-startTime"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.event.endTime')" for="event-endTime"></label>
            <div class="d-flex">
              <input
                id="event-endTime"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.event.defaultTicketValue')"
              for="event-defaultTicketValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="defaultTicketValue"
              id="event-defaultTicketValue"
              data-cy="defaultTicketValue"
              :class="{ valid: !v$.defaultTicketValue.$invalid, invalid: v$.defaultTicketValue.$invalid }"
              v-model.number="v$.defaultTicketValue.$model"
              required
            />
            <div v-if="v$.defaultTicketValue.$anyDirty && v$.defaultTicketValue.$invalid">
              <small class="form-text text-danger" v-for="error of v$.defaultTicketValue.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.event.status')" for="event-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="event-status"
              data-cy="status"
              required
            >
              <option
                v-for="eventStatusEnum in eventStatusEnumValues"
                :key="eventStatusEnum"
                v-bind:value="eventStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.EventStatusEnum.' + eventStatusEnum)"
              >
                {{ eventStatusEnum }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.event.activationStatus')"
              for="event-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="event-activationStatus"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.event.mainVenue')" for="event-mainVenue"></label>
            <select class="form-control" id="event-mainVenue" data-cy="mainVenue" name="mainVenue" v-model="event.mainVenue">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="event.mainVenue && venueOption.id === event.mainVenue.id ? event.mainVenue : venueOption"
                v-for="venueOption in venues"
                :key="venueOption.id"
              >
                {{ venueOption.acronym }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.event.typeOfEvent')"
              for="event-typeOfEvent"
            ></label>
            <select
              class="form-control"
              id="event-typeOfEvent"
              data-cy="typeOfEvent"
              name="typeOfEvent"
              v-model="event.typeOfEvent"
              required
            >
              <option v-if="!event.typeOfEvent" v-bind:value="null" selected></option>
              <option
                v-bind:value="event.typeOfEvent && typeOfEventOption.id === event.typeOfEvent.id ? event.typeOfEvent : typeOfEventOption"
                v-for="typeOfEventOption in typeOfEvents"
                :key="typeOfEventOption.id"
              >
                {{ typeOfEventOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfEvent.$anyDirty && v$.typeOfEvent.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfEvent.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.event.promoteurPerson')"
              for="event-promoteurPerson"
            ></label>
            <select
              class="form-control"
              id="event-promoteurPerson"
              data-cy="promoteurPerson"
              name="promoteurPerson"
              v-model="event.promoteurPerson"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="event.promoteurPerson && personOption.id === event.promoteurPerson.id ? event.promoteurPerson : personOption"
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.fullname }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.event.assetCollection')" for="event-assetCollection"></label>
            <select
              class="form-control"
              id="event-assetCollections"
              data-cy="assetCollection"
              multiple
              name="assetCollection"
              v-if="event.assetCollections !== undefined"
              v-model="event.assetCollections"
            >
              <option
                v-bind:value="getSelected(event.assetCollections, assetCollectionOption, 'id')"
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
<script lang="ts" src="./event-update.component.ts"></script>
