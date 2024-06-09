<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.townCity.home.createOrEditLabel"
          data-cy="TownCityCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.townCity.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="townCity.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="townCity.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.townCity.acronym')"
              for="town-city-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="town-city-acronym"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.townCity.name')" for="town-city-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="town-city-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.townCity.geoPolygonArea')"
              for="town-city-geoPolygonArea"
            ></label>
            <div>
              <div v-if="townCity.geoPolygonArea" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(townCity.geoPolygonAreaContentType, townCity.geoPolygonArea)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ townCity.geoPolygonAreaContentType }}, {{ byteSize(townCity.geoPolygonArea) }}</span>
                <button
                  type="button"
                  v-on:click="
                    townCity.geoPolygonArea = null;
                    townCity.geoPolygonAreaContentType = null;
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
                v-on:change="setFileData($event, townCity, 'geoPolygonArea', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonArea"
              id="town-city-geoPolygonArea"
              data-cy="geoPolygonArea"
              :class="{ valid: !v$.geoPolygonArea.$invalid, invalid: v$.geoPolygonArea.$invalid }"
              v-model="v$.geoPolygonArea.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonAreaContentType"
              id="town-city-geoPolygonAreaContentType"
              v-model="townCity.geoPolygonAreaContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.townCity.province')"
              for="town-city-province"
            ></label>
            <select class="form-control" id="town-city-province" data-cy="province" name="province" v-model="townCity.province" required>
              <option v-if="!townCity.province" v-bind:value="null" selected></option>
              <option
                v-bind:value="townCity.province && provinceOption.id === townCity.province.id ? townCity.province : provinceOption"
                v-for="provinceOption in provinces"
                :key="provinceOption.id"
              >
                {{ provinceOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.province.$anyDirty && v$.province.$invalid">
            <small class="form-text text-danger" v-for="error of v$.province.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./town-city-update.component.ts"></script>
