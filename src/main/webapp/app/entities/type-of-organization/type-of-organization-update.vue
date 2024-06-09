<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.typeOfOrganization.home.createOrEditLabel"
          data-cy="TypeOfOrganizationCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="typeOfOrganization.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="typeOfOrganization.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.acronym')"
              for="type-of-organization-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="type-of-organization-acronym"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.name')"
              for="type-of-organization-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="type-of-organization-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.description')"
              for="type-of-organization-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="type-of-organization-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.businessHandlerClazz')"
              for="type-of-organization-businessHandlerClazz"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessHandlerClazz"
              id="type-of-organization-businessHandlerClazz"
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
<script lang="ts" src="./type-of-organization-update.component.ts"></script>
