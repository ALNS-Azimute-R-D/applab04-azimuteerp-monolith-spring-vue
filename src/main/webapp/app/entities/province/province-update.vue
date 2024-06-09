<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.province.home.createOrEditLabel"
          data-cy="ProvinceCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.province.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="province.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="province.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.province.acronym')"
              for="province-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="province-acronym"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.province.name')" for="province-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="province-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.province.geoPolygonArea')"
              for="province-geoPolygonArea"
            ></label>
            <div>
              <div v-if="province.geoPolygonArea" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(province.geoPolygonAreaContentType, province.geoPolygonArea)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ province.geoPolygonAreaContentType }}, {{ byteSize(province.geoPolygonArea) }}</span>
                <button
                  type="button"
                  v-on:click="
                    province.geoPolygonArea = null;
                    province.geoPolygonAreaContentType = null;
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
                v-on:change="setFileData($event, province, 'geoPolygonArea', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonArea"
              id="province-geoPolygonArea"
              data-cy="geoPolygonArea"
              :class="{ valid: !v$.geoPolygonArea.$invalid, invalid: v$.geoPolygonArea.$invalid }"
              v-model="v$.geoPolygonArea.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonAreaContentType"
              id="province-geoPolygonAreaContentType"
              v-model="province.geoPolygonAreaContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.province.country')"
              for="province-country"
            ></label>
            <select class="form-control" id="province-country" data-cy="country" name="country" v-model="province.country" required>
              <option v-if="!province.country" v-bind:value="null" selected></option>
              <option
                v-bind:value="province.country && countryOption.id === province.country.id ? province.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.country.$anyDirty && v$.country.$invalid">
            <small class="form-text text-danger" v-for="error of v$.country.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./province-update.component.ts"></script>
