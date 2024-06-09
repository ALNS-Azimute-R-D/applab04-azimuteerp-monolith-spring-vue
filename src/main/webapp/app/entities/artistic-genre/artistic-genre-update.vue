<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.artisticGenre.home.createOrEditLabel"
          data-cy="ArtisticGenreCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="artisticGenre.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="artisticGenre.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.acronym')"
              for="artistic-genre-acronym"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="artistic-genre-acronym"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.name')"
              for="artistic-genre-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="artistic-genre-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.description')"
              for="artistic-genre-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="artistic-genre-description"
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
            <label v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.artisticGenre')" for="artistic-genre-artisticGenre"></label>
            <select
              class="form-control"
              id="artistic-genre-artisticGenres"
              data-cy="artisticGenre"
              multiple
              name="artisticGenre"
              v-if="artisticGenre.artisticGenres !== undefined"
              v-model="artisticGenre.artisticGenres"
            >
              <option
                v-bind:value="getSelected(artisticGenre.artisticGenres, artistOption, 'id')"
                v-for="artistOption in artists"
                :key="artistOption.id"
              >
                {{ artistOption.id }}
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
<script lang="ts" src="./artistic-genre-update.component.ts"></script>
