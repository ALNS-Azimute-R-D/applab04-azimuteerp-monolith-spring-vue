<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.supplier.home.createOrEditLabel"
          data-cy="SupplierCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.supplier.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="supplier.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="supplier.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.acronym')"
              for="supplier-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="supplier-acronym"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.companyName')"
              for="supplier-companyName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="companyName"
              id="supplier-companyName"
              data-cy="companyName"
              :class="{ valid: !v$.companyName.$invalid, invalid: v$.companyName.$invalid }"
              v-model="v$.companyName.$model"
              required
            />
            <div v-if="v$.companyName.$anyDirty && v$.companyName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.companyName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.streetAddress')"
              for="supplier-streetAddress"
            ></label>
            <input
              type="text"
              class="form-control"
              name="streetAddress"
              id="supplier-streetAddress"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.houseNumber')"
              for="supplier-houseNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="houseNumber"
              id="supplier-houseNumber"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.locationName')"
              for="supplier-locationName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="locationName"
              id="supplier-locationName"
              data-cy="locationName"
              :class="{ valid: !v$.locationName.$invalid, invalid: v$.locationName.$invalid }"
              v-model="v$.locationName.$model"
            />
            <div v-if="v$.locationName.$anyDirty && v$.locationName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.locationName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.supplier.city')" for="supplier-city"></label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="supplier-city"
              data-cy="city"
              :class="{ valid: !v$.city.$invalid, invalid: v$.city.$invalid }"
              v-model="v$.city.$model"
            />
            <div v-if="v$.city.$anyDirty && v$.city.$invalid">
              <small class="form-text text-danger" v-for="error of v$.city.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.stateProvince')"
              for="supplier-stateProvince"
            ></label>
            <input
              type="text"
              class="form-control"
              name="stateProvince"
              id="supplier-stateProvince"
              data-cy="stateProvince"
              :class="{ valid: !v$.stateProvince.$invalid, invalid: v$.stateProvince.$invalid }"
              v-model="v$.stateProvince.$model"
            />
            <div v-if="v$.stateProvince.$anyDirty && v$.stateProvince.$invalid">
              <small class="form-text text-danger" v-for="error of v$.stateProvince.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.zipPostalCode')"
              for="supplier-zipPostalCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="zipPostalCode"
              id="supplier-zipPostalCode"
              data-cy="zipPostalCode"
              :class="{ valid: !v$.zipPostalCode.$invalid, invalid: v$.zipPostalCode.$invalid }"
              v-model="v$.zipPostalCode.$model"
            />
            <div v-if="v$.zipPostalCode.$anyDirty && v$.zipPostalCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.zipPostalCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.countryRegion')"
              for="supplier-countryRegion"
            ></label>
            <input
              type="text"
              class="form-control"
              name="countryRegion"
              id="supplier-countryRegion"
              data-cy="countryRegion"
              :class="{ valid: !v$.countryRegion.$invalid, invalid: v$.countryRegion.$invalid }"
              v-model="v$.countryRegion.$model"
            />
            <div v-if="v$.countryRegion.$anyDirty && v$.countryRegion.$invalid">
              <small class="form-text text-danger" v-for="error of v$.countryRegion.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.pointLocation')"
              for="supplier-pointLocation"
            ></label>
            <div>
              <div v-if="supplier.pointLocation" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(supplier.pointLocationContentType, supplier.pointLocation)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ supplier.pointLocationContentType }}, {{ byteSize(supplier.pointLocation) }}</span>
                <button
                  type="button"
                  v-on:click="
                    supplier.pointLocation = null;
                    supplier.pointLocationContentType = null;
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
                v-on:change="setFileData($event, supplier, 'pointLocation', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="pointLocation"
              id="supplier-pointLocation"
              data-cy="pointLocation"
              :class="{ valid: !v$.pointLocation.$invalid, invalid: v$.pointLocation.$invalid }"
              v-model="v$.pointLocation.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="pointLocationContentType"
              id="supplier-pointLocationContentType"
              v-model="supplier.pointLocationContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.mainEmail')"
              for="supplier-mainEmail"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainEmail"
              id="supplier-mainEmail"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.phoneNumber1')"
              for="supplier-phoneNumber1"
            ></label>
            <input
              type="text"
              class="form-control"
              name="phoneNumber1"
              id="supplier-phoneNumber1"
              data-cy="phoneNumber1"
              :class="{ valid: !v$.phoneNumber1.$invalid, invalid: v$.phoneNumber1.$invalid }"
              v-model="v$.phoneNumber1.$model"
            />
            <div v-if="v$.phoneNumber1.$anyDirty && v$.phoneNumber1.$invalid">
              <small class="form-text text-danger" v-for="error of v$.phoneNumber1.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.phoneNumber2')"
              for="supplier-phoneNumber2"
            ></label>
            <input
              type="text"
              class="form-control"
              name="phoneNumber2"
              id="supplier-phoneNumber2"
              data-cy="phoneNumber2"
              :class="{ valid: !v$.phoneNumber2.$invalid, invalid: v$.phoneNumber2.$invalid }"
              v-model="v$.phoneNumber2.$model"
            />
            <div v-if="v$.phoneNumber2.$anyDirty && v$.phoneNumber2.$invalid">
              <small class="form-text text-danger" v-for="error of v$.phoneNumber2.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.customAttributesDetailsJSON')"
              for="supplier-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="supplier-customAttributesDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.activationStatus')"
              for="supplier-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="supplier-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.supplier.representativePerson')"
              for="supplier-representativePerson"
            ></label>
            <select
              class="form-control"
              id="supplier-representativePerson"
              data-cy="representativePerson"
              name="representativePerson"
              v-model="supplier.representativePerson"
              required
            >
              <option v-if="!supplier.representativePerson" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  supplier.representativePerson && personOption.id === supplier.representativePerson.id
                    ? supplier.representativePerson
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.lastname }}
              </option>
            </select>
          </div>
          <div v-if="v$.representativePerson.$anyDirty && v$.representativePerson.$invalid">
            <small class="form-text text-danger" v-for="error of v$.representativePerson.$errors" :key="error.$uid">{{
              error.$message
            }}</small>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.supplier.toProduct')" for="supplier-toProduct"></label>
            <select
              class="form-control"
              id="supplier-toProducts"
              data-cy="toProduct"
              multiple
              name="toProduct"
              v-if="supplier.toProducts !== undefined"
              v-model="supplier.toProducts"
            >
              <option
                v-bind:value="getSelected(supplier.toProducts, productOption, 'id')"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
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
<script lang="ts" src="./supplier-update.component.ts"></script>
