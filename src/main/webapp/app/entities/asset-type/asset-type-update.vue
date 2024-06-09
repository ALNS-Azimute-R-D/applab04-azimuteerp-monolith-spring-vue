<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.assetType.home.createOrEditLabel"
          data-cy="AssetTypeCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.assetType.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="assetType.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="assetType.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetType.acronym')"
              for="asset-type-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="asset-type-acronym"
              data-cy="acronym"
              :class="{ valid: !v$.acronym.$invalid, invalid: v$.acronym.$invalid }"
              v-model="v$.acronym.$model"
            />
            <div v-if="v$.acronym.$anyDirty && v$.acronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.assetType.name')" for="asset-type-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="asset-type-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.assetType.description')"
              for="asset-type-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="asset-type-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetType.handlerClazzName')"
              for="asset-type-handlerClazzName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="handlerClazzName"
              id="asset-type-handlerClazzName"
              data-cy="handlerClazzName"
              :class="{ valid: !v$.handlerClazzName.$invalid, invalid: v$.handlerClazzName.$invalid }"
              v-model="v$.handlerClazzName.$model"
            />
            <div v-if="v$.handlerClazzName.$anyDirty && v$.handlerClazzName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.handlerClazzName.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetType.customAttributesDetailsJSON')"
              for="asset-type-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="asset-type-customAttributesDetailsJSON"
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
<script lang="ts" src="./asset-type-update.component.ts"></script>
