<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.organizationRole.home.createOrEditLabel"
          data-cy="OrganizationRoleCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="organizationRole.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="organizationRole.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.roleName')"
              for="organization-role-roleName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="roleName"
              id="organization-role-roleName"
              data-cy="roleName"
              :class="{ valid: !v$.roleName.$invalid, invalid: v$.roleName.$invalid }"
              v-model="v$.roleName.$model"
              required
            />
            <div v-if="v$.roleName.$anyDirty && v$.roleName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.roleName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.activationStatus')"
              for="organization-role-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="organization-role-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.organization')"
              for="organization-role-organization"
            ></label>
            <select
              class="form-control"
              id="organization-role-organization"
              data-cy="organization"
              name="organization"
              v-model="organizationRole.organization"
              required
            >
              <option v-if="!organizationRole.organization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationRole.organization && organizationOption.id === organizationRole.organization.id
                    ? organizationRole.organization
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
<script lang="ts" src="./organization-role-update.component.ts"></script>
