<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.organizationAttribute.home.createOrEditLabel"
          data-cy="OrganizationAttributeCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="organizationAttribute.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="organizationAttribute.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.attributeName')"
              for="organization-attribute-attributeName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="attributeName"
              id="organization-attribute-attributeName"
              data-cy="attributeName"
              :class="{ valid: !v$.attributeName.$invalid, invalid: v$.attributeName.$invalid }"
              v-model="v$.attributeName.$model"
            />
            <div v-if="v$.attributeName.$anyDirty && v$.attributeName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.attributeName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.attributeValue')"
              for="organization-attribute-attributeValue"
            ></label>
            <input
              type="text"
              class="form-control"
              name="attributeValue"
              id="organization-attribute-attributeValue"
              data-cy="attributeValue"
              :class="{ valid: !v$.attributeValue.$invalid, invalid: v$.attributeValue.$invalid }"
              v-model="v$.attributeValue.$model"
            />
            <div v-if="v$.attributeValue.$anyDirty && v$.attributeValue.$invalid">
              <small class="form-text text-danger" v-for="error of v$.attributeValue.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.organization')"
              for="organization-attribute-organization"
            ></label>
            <select
              class="form-control"
              id="organization-attribute-organization"
              data-cy="organization"
              name="organization"
              v-model="organizationAttribute.organization"
              required
            >
              <option v-if="!organizationAttribute.organization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  organizationAttribute.organization && organizationOption.id === organizationAttribute.organization.id
                    ? organizationAttribute.organization
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
<script lang="ts" src="./organization-attribute-update.component.ts"></script>
