<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.organizationMemberRole.home.createOrEditLabel"
          data-cy="OrganizationMemberRoleCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="organizationMemberRole.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="organizationMemberRole.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.joinedAt')"
              for="organization-member-role-joinedAt"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="organization-member-role-joinedAt"
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
                id="organization-member-role-joinedAt"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.organizationMembership')"
              for="organization-member-role-organizationMembership"
            ></label>
            <select
              class="form-control"
              id="organization-member-role-organizationMembership"
              data-cy="organizationMembership"
              name="organizationMembership"
              v-model="organizationMemberRole.organizationMembership"
              required
            >
              <option v-if="!organizationMemberRole.organizationMembership" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationMemberRole.organizationMembership &&
                  organizationMembershipOption.id === organizationMemberRole.organizationMembership.id
                    ? organizationMemberRole.organizationMembership
                    : organizationMembershipOption
                "
                v-for="organizationMembershipOption in organizationMemberships"
                :key="organizationMembershipOption.id"
              >
                {{ organizationMembershipOption.id }}
              </option>
            </select>
          </div>
          <div v-if="v$.organizationMembership.$anyDirty && v$.organizationMembership.$invalid">
            <small class="form-text text-danger" v-for="error of v$.organizationMembership.$errors" :key="error.$uid">{{
              error.$message
            }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.organizationRole')"
              for="organization-member-role-organizationRole"
            ></label>
            <select
              class="form-control"
              id="organization-member-role-organizationRole"
              data-cy="organizationRole"
              name="organizationRole"
              v-model="organizationMemberRole.organizationRole"
              required
            >
              <option v-if="!organizationMemberRole.organizationRole" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationMemberRole.organizationRole && organizationRoleOption.id === organizationMemberRole.organizationRole.id
                    ? organizationMemberRole.organizationRole
                    : organizationRoleOption
                "
                v-for="organizationRoleOption in organizationRoles"
                :key="organizationRoleOption.id"
              >
                {{ organizationRoleOption.id }}
              </option>
            </select>
          </div>
          <div v-if="v$.organizationRole.$anyDirty && v$.organizationRole.$invalid">
            <small class="form-text text-danger" v-for="error of v$.organizationRole.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./organization-member-role-update.component.ts"></script>
