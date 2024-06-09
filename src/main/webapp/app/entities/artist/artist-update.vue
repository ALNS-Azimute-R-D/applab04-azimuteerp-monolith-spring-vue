<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.artist.home.createOrEditLabel"
          data-cy="ArtistCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.artist.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="artist.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="artist.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.artist.acronym')" for="artist-acronym"></label>
            <input
              type="text"
              class="form-control"
              name="acronym"
              id="artist-acronym"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.artist.publicName')"
              for="artist-publicName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="publicName"
              id="artist-publicName"
              data-cy="publicName"
              :class="{ valid: !v$.publicName.$invalid, invalid: v$.publicName.$invalid }"
              v-model="v$.publicName.$model"
              required
            />
            <div v-if="v$.publicName.$anyDirty && v$.publicName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.publicName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.artist.realName')" for="artist-realName"></label>
            <input
              type="text"
              class="form-control"
              name="realName"
              id="artist-realName"
              data-cy="realName"
              :class="{ valid: !v$.realName.$invalid, invalid: v$.realName.$invalid }"
              v-model="v$.realName.$model"
            />
            <div v-if="v$.realName.$anyDirty && v$.realName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.realName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artist.biographyDetailsJSON')"
              for="artist-biographyDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="biographyDetailsJSON"
              id="artist-biographyDetailsJSON"
              data-cy="biographyDetailsJSON"
              :class="{ valid: !v$.biographyDetailsJSON.$invalid, invalid: v$.biographyDetailsJSON.$invalid }"
              v-model="v$.biographyDetailsJSON.$model"
            />
            <div v-if="v$.biographyDetailsJSON.$anyDirty && v$.biographyDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.biographyDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artist.typeOfArtmedia')"
              for="artist-typeOfArtmedia"
            ></label>
            <select
              class="form-control"
              id="artist-typeOfArtmedia"
              data-cy="typeOfArtmedia"
              name="typeOfArtmedia"
              v-model="artist.typeOfArtmedia"
              required
            >
              <option v-if="!artist.typeOfArtmedia" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  artist.typeOfArtmedia && typeOfArtmediaOption.id === artist.typeOfArtmedia.id
                    ? artist.typeOfArtmedia
                    : typeOfArtmediaOption
                "
                v-for="typeOfArtmediaOption in typeOfArtmedias"
                :key="typeOfArtmediaOption.id"
              >
                {{ typeOfArtmediaOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfArtmedia.$anyDirty && v$.typeOfArtmedia.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfArtmedia.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artist.typeOfArtist')"
              for="artist-typeOfArtist"
            ></label>
            <select
              class="form-control"
              id="artist-typeOfArtist"
              data-cy="typeOfArtist"
              name="typeOfArtist"
              v-model="artist.typeOfArtist"
              required
            >
              <option v-if="!artist.typeOfArtist" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  artist.typeOfArtist && typeOfArtistOption.id === artist.typeOfArtist.id ? artist.typeOfArtist : typeOfArtistOption
                "
                v-for="typeOfArtistOption in typeOfArtists"
                :key="typeOfArtistOption.id"
              >
                {{ typeOfArtistOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfArtist.$anyDirty && v$.typeOfArtist.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfArtist.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artist.artistAggregator')"
              for="artist-artistAggregator"
            ></label>
            <select
              class="form-control"
              id="artist-artistAggregator"
              data-cy="artistAggregator"
              name="artistAggregator"
              v-model="artist.artistAggregator"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  artist.artistAggregator && artistOption.id === artist.artistAggregator.id ? artist.artistAggregator : artistOption
                "
                v-for="artistOption in artists"
                :key="artistOption.id"
              >
                {{ artistOption.acronym }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.artist.artist')" for="artist-artist"></label>
            <select
              class="form-control"
              id="artist-artists"
              data-cy="artist"
              multiple
              name="artist"
              v-if="artist.artists !== undefined"
              v-model="artist.artists"
            >
              <option
                v-bind:value="getSelected(artist.artists, artisticGenreOption, 'id')"
                v-for="artisticGenreOption in artisticGenres"
                :key="artisticGenreOption.id"
              >
                {{ artisticGenreOption.id }}
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
<script lang="ts" src="./artist-update.component.ts"></script>
