<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.venue.home.createOrEditLabel"
          data-cy="VenueCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.venue.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="venue.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="venue.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.venue.acronym')" for="venue-acronym"></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="venue-acronym"
              data-cy="acronym"
              :class="{ valid: !v$.acronym.$invalid, invalid: v$.acronym.$invalid }"
              v-model="v$.acronym.$model"
            />
            <div v-if="v$.acronym.$anyDirty && v$.acronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.venue.name')" for="venue-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="venue-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.venue.description')"
              for="venue-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="venue-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.venue.geoPointLocation')"
              for="venue-geoPointLocation"
            ></label>
            <div>
              <div v-if="venue.geoPointLocation" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(venue.geoPointLocationContentType, venue.geoPointLocation)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ venue.geoPointLocationContentType }}, {{ byteSize(venue.geoPointLocation) }}</span>
                <button
                  type="button"
                  v-on:click="
                    venue.geoPointLocation = null;
                    venue.geoPointLocationContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_geoPointLocation" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_geoPointLocation"
                id="file_geoPointLocation"
                style="display: none"
                data-cy="geoPointLocation"
                v-on:change="setFileData($event, venue, 'geoPointLocation', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="geoPointLocation"
              id="venue-geoPointLocation"
              data-cy="geoPointLocation"
              :class="{ valid: !v$.geoPointLocation.$invalid, invalid: v$.geoPointLocation.$invalid }"
              v-model="v$.geoPointLocation.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="geoPointLocationContentType"
              id="venue-geoPointLocationContentType"
              v-model="venue.geoPointLocationContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.venue.typeOfVenue')"
              for="venue-typeOfVenue"
            ></label>
            <select
              class="form-control"
              id="venue-typeOfVenue"
              data-cy="typeOfVenue"
              name="typeOfVenue"
              v-model="venue.typeOfVenue"
              required
            >
              <option v-if="!venue.typeOfVenue" v-bind:value="null" selected></option>
              <option
                v-bind:value="venue.typeOfVenue && typeOfVenueOption.id === venue.typeOfVenue.id ? venue.typeOfVenue : typeOfVenueOption"
                v-for="typeOfVenueOption in typeOfVenues"
                :key="typeOfVenueOption.id"
              >
                {{ typeOfVenueOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfVenue.$anyDirty && v$.typeOfVenue.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfVenue.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.venue.commonLocality')"
              for="venue-commonLocality"
            ></label>
            <select
              class="form-control"
              id="venue-commonLocality"
              data-cy="commonLocality"
              name="commonLocality"
              v-model="venue.commonLocality"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  venue.commonLocality && commonLocalityOption.id === venue.commonLocality.id ? venue.commonLocality : commonLocalityOption
                "
                v-for="commonLocalityOption in commonLocalities"
                :key="commonLocalityOption.id"
              >
                {{ commonLocalityOption.name }}
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
<script lang="ts" src="./venue-update.component.ts"></script>
