<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.businessUnit.home.createOrEditLabel"
          data-cy="BusinessUnitCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="businessUnit.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="businessUnit.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.acronym')"
              for="business-unit-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="business-unit-acronym"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.hierarchicalLevel')"
              for="business-unit-hierarchicalLevel"
            ></label>
            <input
              type="text"
              class="form-control"
              name="hierarchicalLevel"
              id="business-unit-hierarchicalLevel"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.name')"
              for="business-unit-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="business-unit-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.activationStatus')"
              for="business-unit-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="business-unit-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.organization')"
              for="business-unit-organization"
            ></label>
            <select
              class="form-control"
              id="business-unit-organization"
              data-cy="organization"
              name="organization"
              v-model="businessUnit.organization"
              required
            >
              <option v-if="!businessUnit.organization" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  businessUnit.organization && organizationOption.id === businessUnit.organization.id
                    ? businessUnit.organization
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
              v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.businessUnitParent')"
              for="business-unit-businessUnitParent"
            ></label>
            <select
              class="form-control"
              id="business-unit-businessUnitParent"
              data-cy="businessUnitParent"
              name="businessUnitParent"
              v-model="businessUnit.businessUnitParent"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  businessUnit.businessUnitParent && businessUnitOption.id === businessUnit.businessUnitParent.id
                    ? businessUnit.businessUnitParent
                    : businessUnitOption
                "
                v-for="businessUnitOption in businessUnits"
                :key="businessUnitOption.id"
              >
                {{ businessUnitOption.name }}
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
<script lang="ts" src="./business-unit-update.component.ts"></script>
