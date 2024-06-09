<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.warehouse.home.createOrEditLabel"
          data-cy="WarehouseCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="warehouse.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="warehouse.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.acronym')"
              for="warehouse-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="warehouse-acronym"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.name')" for="warehouse-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="warehouse-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.description')"
              for="warehouse-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="warehouse-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.streetAddress')"
              for="warehouse-streetAddress"
            ></label>
            <input
              type="text"
              class="form-control"
              name="streetAddress"
              id="warehouse-streetAddress"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.houseNumber')"
              for="warehouse-houseNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="houseNumber"
              id="warehouse-houseNumber"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.locationName')"
              for="warehouse-locationName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="locationName"
              id="warehouse-locationName"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.postalCode')"
              for="warehouse-postalCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="postalCode"
              id="warehouse-postalCode"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.pointLocation')"
              for="warehouse-pointLocation"
            ></label>
            <div>
              <div v-if="warehouse.pointLocation" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(warehouse.pointLocationContentType, warehouse.pointLocation)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ warehouse.pointLocationContentType }}, {{ byteSize(warehouse.pointLocation) }}</span>
                <button
                  type="button"
                  v-on:click="
                    warehouse.pointLocation = null;
                    warehouse.pointLocationContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_pointLocation" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_pointLocation"
                id="file_pointLocation"
                style="display: none"
                data-cy="pointLocation"
                v-on:change="setFileData($event, warehouse, 'pointLocation', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="pointLocation"
              id="warehouse-pointLocation"
              data-cy="pointLocation"
              :class="{ valid: !v$.pointLocation.$invalid, invalid: v$.pointLocation.$invalid }"
              v-model="v$.pointLocation.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="pointLocationContentType"
              id="warehouse-pointLocationContentType"
              v-model="warehouse.pointLocationContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.mainEmail')"
              for="warehouse-mainEmail"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainEmail"
              id="warehouse-mainEmail"
              data-cy="mainEmail"
              :class="{ valid: !v$.mainEmail.$invalid, invalid: v$.mainEmail.$invalid }"
              v-model="v$.mainEmail.$model"
            />
            <div v-if="v$.mainEmail.$anyDirty && v$.mainEmail.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainEmail.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.landPhoneNumber')"
              for="warehouse-landPhoneNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="landPhoneNumber"
              id="warehouse-landPhoneNumber"
              data-cy="landPhoneNumber"
              :class="{ valid: !v$.landPhoneNumber.$invalid, invalid: v$.landPhoneNumber.$invalid }"
              v-model="v$.landPhoneNumber.$model"
            />
            <div v-if="v$.landPhoneNumber.$anyDirty && v$.landPhoneNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.landPhoneNumber.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.mobilePhoneNumber')"
              for="warehouse-mobilePhoneNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mobilePhoneNumber"
              id="warehouse-mobilePhoneNumber"
              data-cy="mobilePhoneNumber"
              :class="{ valid: !v$.mobilePhoneNumber.$invalid, invalid: v$.mobilePhoneNumber.$invalid }"
              v-model="v$.mobilePhoneNumber.$model"
            />
            <div v-if="v$.mobilePhoneNumber.$anyDirty && v$.mobilePhoneNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mobilePhoneNumber.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.faxNumber')"
              for="warehouse-faxNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="faxNumber"
              id="warehouse-faxNumber"
              data-cy="faxNumber"
              :class="{ valid: !v$.faxNumber.$invalid, invalid: v$.faxNumber.$invalid }"
              v-model="v$.faxNumber.$model"
            />
            <div v-if="v$.faxNumber.$anyDirty && v$.faxNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.faxNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.customAttributesDetailsJSON')"
              for="warehouse-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="warehouse-customAttributesDetailsJSON"
              data-cy="customAttributesDetailsJSON"
              :class="{ valid: !v$.customAttributesDetailsJSON.$invalid, invalid: v$.customAttributesDetailsJSON.$invalid }"
              v-model="v$.customAttributesDetailsJSON.$model"
            />
            <div v-if="v$.customAttributesDetailsJSON.$anyDirty && v$.customAttributesDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customAttributesDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.activationStatus')"
              for="warehouse-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="warehouse-activationStatus"
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
<script lang="ts" src="./warehouse-update.component.ts"></script>
