<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.country.home.createOrEditLabel"
          data-cy="CountryCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.country.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="country.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="country.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.country.acronym')" for="country-acronym"></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="country-acronym"
              data-cy="acronym"
              :class="{ valid: !v$.acronym.$invalid, invalid: v$.acronym.$invalid }"
              v-model="v$.acronym.$model"
              required
            />
            <div v-if="v$.acronym.$anyDirty && v$.acronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.country.name')" for="country-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="country-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.country.continent')"
              for="country-continent"
            ></label>
            <select
              class="form-control"
              name="continent"
              :class="{ valid: !v$.continent.$invalid, invalid: v$.continent.$invalid }"
              v-model="v$.continent.$model"
              id="country-continent"
              data-cy="continent"
              required
            >
              <option
                v-for="continentEnum in continentEnumValues"
                :key="continentEnum"
                v-bind:value="continentEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.ContinentEnum.' + continentEnum)"
              >
                {{ continentEnum }}
              </option>
            </select>
            <div v-if="v$.continent.$anyDirty && v$.continent.$invalid">
              <small class="form-text text-danger" v-for="error of v$.continent.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.country.geoPolygonArea')"
              for="country-geoPolygonArea"
            ></label>
            <div>
              <div v-if="country.geoPolygonArea" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(country.geoPolygonAreaContentType, country.geoPolygonArea)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ country.geoPolygonAreaContentType }}, {{ byteSize(country.geoPolygonArea) }}</span>
                <button
                  type="button"
                  v-on:click="
                    country.geoPolygonArea = null;
                    country.geoPolygonAreaContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_geoPolygonArea" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_geoPolygonArea"
                id="file_geoPolygonArea"
                style="display: none"
                data-cy="geoPolygonArea"
                v-on:change="setFileData($event, country, 'geoPolygonArea', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonArea"
              id="country-geoPolygonArea"
              data-cy="geoPolygonArea"
              :class="{ valid: !v$.geoPolygonArea.$invalid, invalid: v$.geoPolygonArea.$invalid }"
              v-model="v$.geoPolygonArea.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonAreaContentType"
              id="country-geoPolygonAreaContentType"
              v-model="country.geoPolygonAreaContentType"
            />
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
<script lang="ts" src="./country-update.component.ts"></script>
