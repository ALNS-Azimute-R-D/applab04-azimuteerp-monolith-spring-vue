<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.district.home.createOrEditLabel"
          data-cy="DistrictCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.district.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="district.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="district.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.district.acronym')"
              for="district-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="district-acronym"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.district.name')" for="district-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="district-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.district.geoPolygonArea')"
              for="district-geoPolygonArea"
            ></label>
            <div>
              <div v-if="district.geoPolygonArea" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(district.geoPolygonAreaContentType, district.geoPolygonArea)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ district.geoPolygonAreaContentType }}, {{ byteSize(district.geoPolygonArea) }}</span>
                <button
                  type="button"
                  v-on:click="
                    district.geoPolygonArea = null;
                    district.geoPolygonAreaContentType = null;
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
                v-on:change="setFileData($event, district, 'geoPolygonArea', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonArea"
              id="district-geoPolygonArea"
              data-cy="geoPolygonArea"
              :class="{ valid: !v$.geoPolygonArea.$invalid, invalid: v$.geoPolygonArea.$invalid }"
              v-model="v$.geoPolygonArea.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonAreaContentType"
              id="district-geoPolygonAreaContentType"
              v-model="district.geoPolygonAreaContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.district.townCity')"
              for="district-townCity"
            ></label>
            <select class="form-control" id="district-townCity" data-cy="townCity" name="townCity" v-model="district.townCity" required>
              <option v-if="!district.townCity" v-bind:value="null" selected></option>
              <option
                v-bind:value="district.townCity && townCityOption.id === district.townCity.id ? district.townCity : townCityOption"
                v-for="townCityOption in townCities"
                :key="townCityOption.id"
              >
                {{ townCityOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.townCity.$anyDirty && v$.townCity.$invalid">
            <small class="form-text text-danger" v-for="error of v$.townCity.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./district-update.component.ts"></script>
