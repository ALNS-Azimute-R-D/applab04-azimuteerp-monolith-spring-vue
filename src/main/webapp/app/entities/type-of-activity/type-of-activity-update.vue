<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.typeOfActivity.home.createOrEditLabel"
          data-cy="TypeOfActivityCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="typeOfActivity.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="typeOfActivity.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.acronym')"
              for="type-of-activity-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="type-of-activity-acronym"
              data-cy="acronym"
              :class="{ valid: !v$.acronym.$invalid, invalid: v$.acronym.$invalid }"
              v-model="v$.acronym.$model"
            />
            <div v-if="v$.acronym.$anyDirty && v$.acronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.name')"
              for="type-of-activity-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="type-of-activity-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.description')"
              for="type-of-activity-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="type-of-activity-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.handlerClazzName')"
              for="type-of-activity-handlerClazzName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="handlerClazzName"
              id="type-of-activity-handlerClazzName"
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
<script lang="ts" src="./type-of-activity-update.component.ts"></script>
