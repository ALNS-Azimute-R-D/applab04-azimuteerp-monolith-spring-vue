<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.tenant.home.createOrEditLabel"
          data-cy="TenantCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.tenant.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="tenant.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="tenant.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.tenant.acronym')" for="tenant-acronym"></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="tenant-acronym"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.tenant.name')" for="tenant-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="tenant-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.description')"
              for="tenant-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="tenant-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.customerBusinessCode')"
              for="tenant-customerBusinessCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customerBusinessCode"
              id="tenant-customerBusinessCode"
              data-cy="customerBusinessCode"
              :class="{ valid: !v$.customerBusinessCode.$invalid, invalid: v$.customerBusinessCode.$invalid }"
              v-model="v$.customerBusinessCode.$model"
              required
            />
            <div v-if="v$.customerBusinessCode.$anyDirty && v$.customerBusinessCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customerBusinessCode.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.businessHandlerClazz')"
              for="tenant-businessHandlerClazz"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessHandlerClazz"
              id="tenant-businessHandlerClazz"
              data-cy="businessHandlerClazz"
              :class="{ valid: !v$.businessHandlerClazz.$invalid, invalid: v$.businessHandlerClazz.$invalid }"
              v-model="v$.businessHandlerClazz.$model"
            />
            <div v-if="v$.businessHandlerClazz.$anyDirty && v$.businessHandlerClazz.$invalid">
              <small class="form-text text-danger" v-for="error of v$.businessHandlerClazz.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.mainContactPersonDetailsJSON')"
              for="tenant-mainContactPersonDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainContactPersonDetailsJSON"
              id="tenant-mainContactPersonDetailsJSON"
              data-cy="mainContactPersonDetailsJSON"
              :class="{ valid: !v$.mainContactPersonDetailsJSON.$invalid, invalid: v$.mainContactPersonDetailsJSON.$invalid }"
              v-model="v$.mainContactPersonDetailsJSON.$model"
            />
            <div v-if="v$.mainContactPersonDetailsJSON.$anyDirty && v$.mainContactPersonDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainContactPersonDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.billingDetailsJSON')"
              for="tenant-billingDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="billingDetailsJSON"
              id="tenant-billingDetailsJSON"
              data-cy="billingDetailsJSON"
              :class="{ valid: !v$.billingDetailsJSON.$invalid, invalid: v$.billingDetailsJSON.$invalid }"
              v-model="v$.billingDetailsJSON.$model"
            />
            <div v-if="v$.billingDetailsJSON.$anyDirty && v$.billingDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.billingDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.technicalEnvironmentsDetailsJSON')"
              for="tenant-technicalEnvironmentsDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="technicalEnvironmentsDetailsJSON"
              id="tenant-technicalEnvironmentsDetailsJSON"
              data-cy="technicalEnvironmentsDetailsJSON"
              :class="{ valid: !v$.technicalEnvironmentsDetailsJSON.$invalid, invalid: v$.technicalEnvironmentsDetailsJSON.$invalid }"
              v-model="v$.technicalEnvironmentsDetailsJSON.$model"
            />
            <div v-if="v$.technicalEnvironmentsDetailsJSON.$anyDirty && v$.technicalEnvironmentsDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.technicalEnvironmentsDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.customAttributesDetailsJSON')"
              for="tenant-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="tenant-customAttributesDetailsJSON"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.tenant.logoImg')" for="tenant-logoImg"></label>
            <div>
              <img
                v-bind:src="'data:' + tenant.logoImgContentType + ';base64,' + tenant.logoImg"
                style="max-height: 100px"
                v-if="tenant.logoImg"
                alt="tenant"
              />
              <div v-if="tenant.logoImg" class="form-text text-danger clearfix">
                <span class="pull-left">{{ tenant.logoImgContentType }}, {{ byteSize(tenant.logoImg) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('logoImg', 'logoImgContentType', 'file_logoImg')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_logoImg" v-text="t$('entity.action.addimage')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_logoImg"
                id="file_logoImg"
                style="display: none"
                data-cy="logoImg"
                v-on:change="setFileData($event, tenant, 'logoImg', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="logoImg"
              id="tenant-logoImg"
              data-cy="logoImg"
              :class="{ valid: !v$.logoImg.$invalid, invalid: v$.logoImg.$invalid }"
              v-model="v$.logoImg.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="logoImgContentType"
              id="tenant-logoImgContentType"
              v-model="tenant.logoImgContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.tenant.activationStatus')"
              for="tenant-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="tenant-activationStatus"
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
<script lang="ts" src="./tenant-update.component.ts"></script>
