<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.category.home.createOrEditLabel"
          data-cy="CategoryCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.category.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="category.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="category.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.category.acronym')"
              for="category-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="category-acronym"
              data-cy="acronym"
              :class="{ valid: !v$.acronym.$invalid, invalid: v$.acronym.$invalid }"
              v-model="v$.acronym.$model"
            />
            <div v-if="v$.acronym.$anyDirty && v$.acronym.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acronym.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.category.name')" for="category-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="category-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.category.description')"
              for="category-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="category-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.category.handlerClazzName')"
              for="category-handlerClazzName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="handlerClazzName"
              id="category-handlerClazzName"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.category.categoryParent')"
              for="category-categoryParent"
            ></label>
            <select
              class="form-control"
              id="category-categoryParent"
              data-cy="categoryParent"
              name="categoryParent"
              v-model="category.categoryParent"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  category.categoryParent && categoryOption.id === category.categoryParent.id ? category.categoryParent : categoryOption
                "
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.acronym }}
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
<script lang="ts" src="./category-update.component.ts"></script>
