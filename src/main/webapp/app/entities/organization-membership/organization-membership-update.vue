<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.organizationMembership.home.createOrEditLabel"
          data-cy="OrganizationMembershipCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="organizationMembership.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="organizationMembership.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.joinedAt')"
              for="organization-membership-joinedAt"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="organization-membership-joinedAt"
                  v-model="v$.joinedAt.$model"
                  name="joinedAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="organization-membership-joinedAt"
                data-cy="joinedAt"
                type="text"
                class="form-control"
                name="joinedAt"
                :class="{ valid: !v$.joinedAt.$invalid, invalid: v$.joinedAt.$invalid }"
                v-model="v$.joinedAt.$model"
                required
              />
            </b-input-group>
            <div v-if="v$.joinedAt.$anyDirty && v$.joinedAt.$invalid">
              <small class="form-text text-danger" v-for="error of v$.joinedAt.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.activationStatus')"
              for="organization-membership-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="organization-membership-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.organization')"
              for="organization-membership-organization"
            ></label>
            <select
              class="form-control"
              id="organization-membership-organization"
              data-cy="organization"
              name="organization"
              v-model="organizationMembership.organization"
              required
            >
              <option v-if="!organizationMembership.organization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationMembership.organization && organizationOption.id === organizationMembership.organization.id
                    ? organizationMembership.organization
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
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.person')"
              for="organization-membership-person"
            ></label>
            <select
              class="form-control"
              id="organization-membership-person"
              data-cy="person"
              name="person"
              v-model="organizationMembership.person"
              required
            >
              <option v-if="!organizationMembership.person" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationMembership.person && personOption.id === organizationMembership.person.id
                    ? organizationMembership.person
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.lastname }}
              </option>
            </select>
          </div>
          <div v-if="v$.person.$anyDirty && v$.person.$invalid">
            <small class="form-text text-danger" v-for="error of v$.person.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./organization-membership-update.component.ts"></script>
