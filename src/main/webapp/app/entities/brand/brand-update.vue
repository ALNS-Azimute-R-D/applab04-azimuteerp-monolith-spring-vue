<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.brand.home.createOrEditLabel"
          data-cy="BrandCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.brand.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="brand.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="brand.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.brand.acronym')" for="brand-acronym"></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="brand-acronym"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.brand.name')" for="brand-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="brand-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.brand.description')"
              for="brand-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="brand-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.brand.logoBrand')" for="brand-logoBrand"></label>
            <div>
              <img
                v-bind:src="'data:' + brand.logoBrandContentType + ';base64,' + brand.logoBrand"
                style="max-height: 100px"
                v-if="brand.logoBrand"
                alt="brand"
              />
              <div v-if="brand.logoBrand" class="form-text text-danger clearfix">
                <span class="pull-left">{{ brand.logoBrandContentType }}, {{ byteSize(brand.logoBrand) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('logoBrand', 'logoBrandContentType', 'file_logoBrand')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_logoBrand" v-text="t$('entity.action.addimage')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_logoBrand"
                id="file_logoBrand"
                style="display: none"
                data-cy="logoBrand"
                v-on:change="setFileData($event, brand, 'logoBrand', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="logoBrand"
              id="brand-logoBrand"
              data-cy="logoBrand"
              :class="{ valid: !v$.logoBrand.$invalid, invalid: v$.logoBrand.$invalid }"
              v-model="v$.logoBrand.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="logoBrandContentType"
              id="brand-logoBrandContentType"
              v-model="brand.logoBrandContentType"
            />
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
<script lang="ts" src="./brand-update.component.ts"></script>
