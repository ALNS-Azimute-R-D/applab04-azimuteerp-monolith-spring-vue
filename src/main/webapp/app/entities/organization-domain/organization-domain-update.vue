<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.organizationDomain.home.createOrEditLabel"
          data-cy="OrganizationDomainCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="organizationDomain.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="organizationDomain.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.domainAcronym')"
              for="organization-domain-domainAcronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="domainAcronym"
              id="organization-domain-domainAcronym"
              data-cy="domainAcronym"
              :class="{ valid: !v$.domainAcronym.$invalid, invalid: v$.domainAcronym.$invalid }"
              v-model="v$.domainAcronym.$model"
              required
            />
            <div v-if="v$.domainAcronym.$anyDirty && v$.domainAcronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.domainAcronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.name')"
              for="organization-domain-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="organization-domain-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.isVerified')"
              for="organization-domain-isVerified"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="isVerified"
              id="organization-domain-isVerified"
              data-cy="isVerified"
              :class="{ valid: !v$.isVerified.$invalid, invalid: v$.isVerified.$invalid }"
              v-model="v$.isVerified.$model"
              required
            />
            <div v-if="v$.isVerified.$anyDirty && v$.isVerified.$invalid">
              <small class="form-text text-danger" v-for="error of v$.isVerified.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.businessHandlerClazz')"
              for="organization-domain-businessHandlerClazz"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessHandlerClazz"
              id="organization-domain-businessHandlerClazz"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.activationStatus')"
              for="organization-domain-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="organization-domain-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.organization')"
              for="organization-domain-organization"
            ></label>
            <select
              class="form-control"
              id="organization-domain-organization"
              data-cy="organization"
              name="organization"
              v-model="organizationDomain.organization"
              required
            >
              <option v-if="!organizationDomain.organization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationDomain.organization && organizationOption.id === organizationDomain.organization.id
                    ? organizationDomain.organization
                    : organizationOption
                "
                v-for="organizationOption in organizations"
                :key="organizationOption.id"
              >
                {{ organizationOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.organization.$anyDirty && v$.organization.$invalid">
            <small class="form-text text-danger" v-for="error of v$.organization.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./organization-domain-update.component.ts"></script>
