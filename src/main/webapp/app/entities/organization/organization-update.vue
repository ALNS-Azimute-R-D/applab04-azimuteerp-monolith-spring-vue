<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.organization.home.createOrEditLabel"
          data-cy="OrganizationCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organization.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="organization.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="organization.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.acronym')"
              for="organization-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="organization-acronym"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.businessCode')"
              for="organization-businessCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessCode"
              id="organization-businessCode"
              data-cy="businessCode"
              :class="{ valid: !v$.businessCode.$invalid, invalid: v$.businessCode.$invalid }"
              v-model="v$.businessCode.$model"
              required
            />
            <div v-if="v$.businessCode.$anyDirty && v$.businessCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.businessCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.hierarchicalLevel')"
              for="organization-hierarchicalLevel"
            ></label>
            <input
              type="text"
              class="form-control"
              name="hierarchicalLevel"
              id="organization-hierarchicalLevel"
              data-cy="hierarchicalLevel"
              :class="{ valid: !v$.hierarchicalLevel.$invalid, invalid: v$.hierarchicalLevel.$invalid }"
              v-model="v$.hierarchicalLevel.$model"
            />
            <div v-if="v$.hierarchicalLevel.$anyDirty && v$.hierarchicalLevel.$invalid">
              <small class="form-text text-danger" v-for="error of v$.hierarchicalLevel.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.name')"
              for="organization-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="organization-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.description')"
              for="organization-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="organization-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.businessHandlerClazz')"
              for="organization-businessHandlerClazz"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessHandlerClazz"
              id="organization-businessHandlerClazz"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.mainContactPersonDetailsJSON')"
              for="organization-mainContactPersonDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainContactPersonDetailsJSON"
              id="organization-mainContactPersonDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.technicalEnvironmentsDetailsJSON')"
              for="organization-technicalEnvironmentsDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="technicalEnvironmentsDetailsJSON"
              id="organization-technicalEnvironmentsDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.customAttributesDetailsJSON')"
              for="organization-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="organization-customAttributesDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.organizationStatus')"
              for="organization-organizationStatus"
            ></label>
            <select
              class="form-control"
              name="organizationStatus"
              :class="{ valid: !v$.organizationStatus.$invalid, invalid: v$.organizationStatus.$invalid }"
              v-model="v$.organizationStatus.$model"
              id="organization-organizationStatus"
              data-cy="organizationStatus"
              required
            >
              <option
                v-for="organizationStatusEnum in organizationStatusEnumValues"
                :key="organizationStatusEnum"
                v-bind:value="organizationStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.OrganizationStatusEnum.' + organizationStatusEnum)"
              >
                {{ organizationStatusEnum }}
              </option>
            </select>
            <div v-if="v$.organizationStatus.$anyDirty && v$.organizationStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.organizationStatus.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.activationStatus')"
              for="organization-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="organization-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.logoImg')"
              for="organization-logoImg"
            ></label>
            <div>
              <img
                v-bind:src="'data:' + organization.logoImgContentType + ';base64,' + organization.logoImg"
                style="max-height: 100px"
                v-if="organization.logoImg"
                alt="organization"
              />
              <div v-if="organization.logoImg" class="form-text text-danger clearfix">
                <span class="pull-left">{{ organization.logoImgContentType }}, {{ byteSize(organization.logoImg) }}</span>
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
                v-on:change="setFileData($event, organization, 'logoImg', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="logoImg"
              id="organization-logoImg"
              data-cy="logoImg"
              :class="{ valid: !v$.logoImg.$invalid, invalid: v$.logoImg.$invalid }"
              v-model="v$.logoImg.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="logoImgContentType"
              id="organization-logoImgContentType"
              v-model="organization.logoImgContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.tenant')"
              for="organization-tenant"
            ></label>
            <select class="form-control" id="organization-tenant" data-cy="tenant" name="tenant" v-model="organization.tenant" required>
              <option v-if="!organization.tenant" v-bind:value="null" selected></option>
              <option
                v-bind:value="organization.tenant && tenantOption.id === organization.tenant.id ? organization.tenant : tenantOption"
                v-for="tenantOption in tenants"
                :key="tenantOption.id"
              >
                {{ tenantOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.tenant.$anyDirty && v$.tenant.$invalid">
            <small class="form-text text-danger" v-for="error of v$.tenant.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.typeOfOrganization')"
              for="organization-typeOfOrganization"
            ></label>
            <select
              class="form-control"
              id="organization-typeOfOrganization"
              data-cy="typeOfOrganization"
              name="typeOfOrganization"
              v-model="organization.typeOfOrganization"
              required
            >
              <option v-if="!organization.typeOfOrganization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organization.typeOfOrganization && typeOfOrganizationOption.id === organization.typeOfOrganization.id
                    ? organization.typeOfOrganization
                    : typeOfOrganizationOption
                "
                v-for="typeOfOrganizationOption in typeOfOrganizations"
                :key="typeOfOrganizationOption.id"
              >
                {{ typeOfOrganizationOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfOrganization.$anyDirty && v$.typeOfOrganization.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfOrganization.$errors" :key="error.$uid">{{
              error.$message
            }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organization.organizationParent')"
              for="organization-organizationParent"
            ></label>
            <select
              class="form-control"
              id="organization-organizationParent"
              data-cy="organizationParent"
              name="organizationParent"
              v-model="organization.organizationParent"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  organization.organizationParent && organizationOption.id === organization.organizationParent.id
                    ? organization.organizationParent
                    : organizationOption
                "
                v-for="organizationOption in organizations"
                :key="organizationOption.id"
              >
                {{ organizationOption.name }}
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
<script lang="ts" src="./organization-update.component.ts"></script>
