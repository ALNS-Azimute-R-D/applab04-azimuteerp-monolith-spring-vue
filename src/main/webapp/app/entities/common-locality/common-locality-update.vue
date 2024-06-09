<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.commonLocality.home.createOrEditLabel"
          data-cy="CommonLocalityCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="commonLocality.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="commonLocality.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.acronym')"
              for="common-locality-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="common-locality-acronym"
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
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.name')"
              for="common-locality-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="common-locality-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.description')"
              for="common-locality-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="common-locality-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.streetAddress')"
              for="common-locality-streetAddress"
            ></label>
            <input
              type="text"
              class="form-control"
              name="streetAddress"
              id="common-locality-streetAddress"
              data-cy="streetAddress"
              :class="{ valid: !v$.streetAddress.$invalid, invalid: v$.streetAddress.$invalid }"
              v-model="v$.streetAddress.$model"
              required
            />
            <div v-if="v$.streetAddress.$anyDirty && v$.streetAddress.$invalid">
              <small class="form-text text-danger" v-for="error of v$.streetAddress.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.houseNumber')"
              for="common-locality-houseNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="houseNumber"
              id="common-locality-houseNumber"
              data-cy="houseNumber"
              :class="{ valid: !v$.houseNumber.$invalid, invalid: v$.houseNumber.$invalid }"
              v-model="v$.houseNumber.$model"
            />
            <div v-if="v$.houseNumber.$anyDirty && v$.houseNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.houseNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.locationName')"
              for="common-locality-locationName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="locationName"
              id="common-locality-locationName"
              data-cy="locationName"
              :class="{ valid: !v$.locationName.$invalid, invalid: v$.locationName.$invalid }"
              v-model="v$.locationName.$model"
            />
            <div v-if="v$.locationName.$anyDirty && v$.locationName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.locationName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.postalCode')"
              for="common-locality-postalCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="postalCode"
              id="common-locality-postalCode"
              data-cy="postalCode"
              :class="{ valid: !v$.postalCode.$invalid, invalid: v$.postalCode.$invalid }"
              v-model="v$.postalCode.$model"
              required
            />
            <div v-if="v$.postalCode.$anyDirty && v$.postalCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.postalCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.geoPolygonArea')"
              for="common-locality-geoPolygonArea"
            ></label>
            <div>
              <div v-if="commonLocality.geoPolygonArea" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(commonLocality.geoPolygonAreaContentType, commonLocality.geoPolygonArea)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ commonLocality.geoPolygonAreaContentType }}, {{ byteSize(commonLocality.geoPolygonArea) }}</span>
                <button
                  type="button"
                  v-on:click="
                    commonLocality.geoPolygonArea = null;
                    commonLocality.geoPolygonAreaContentType = null;
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
                v-on:change="setFileData($event, commonLocality, 'geoPolygonArea', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonArea"
              id="common-locality-geoPolygonArea"
              data-cy="geoPolygonArea"
              :class="{ valid: !v$.geoPolygonArea.$invalid, invalid: v$.geoPolygonArea.$invalid }"
              v-model="v$.geoPolygonArea.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="geoPolygonAreaContentType"
              id="common-locality-geoPolygonAreaContentType"
              v-model="commonLocality.geoPolygonAreaContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.district')"
              for="common-locality-district"
            ></label>
            <select
              class="form-control"
              id="common-locality-district"
              data-cy="district"
              name="district"
              v-model="commonLocality.district"
              required
            >
              <option v-if="!commonLocality.district" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  commonLocality.district && districtOption.id === commonLocality.district.id ? commonLocality.district : districtOption
                "
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.district.$anyDirty && v$.district.$invalid">
            <small class="form-text text-danger" v-for="error of v$.district.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./common-locality-update.component.ts"></script>
