<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.artworkCast.home.createOrEditLabel"
          data-cy="ArtworkCastCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="artworkCast.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="artworkCast.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.orderOfAppearance')"
              for="artwork-cast-orderOfAppearance"
            ></label>
            <input
              type="number"
              class="form-control"
              name="orderOfAppearance"
              id="artwork-cast-orderOfAppearance"
              data-cy="orderOfAppearance"
              :class="{ valid: !v$.orderOfAppearance.$invalid, invalid: v$.orderOfAppearance.$invalid }"
              v-model.number="v$.orderOfAppearance.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.characterName')"
              for="artwork-cast-characterName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="characterName"
              id="artwork-cast-characterName"
              data-cy="characterName"
              :class="{ valid: !v$.characterName.$invalid, invalid: v$.characterName.$invalid }"
              v-model="v$.characterName.$model"
            />
            <div v-if="v$.characterName.$anyDirty && v$.characterName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.characterName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.mainAssetUUID')"
              for="artwork-cast-mainAssetUUID"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainAssetUUID"
              id="artwork-cast-mainAssetUUID"
              data-cy="mainAssetUUID"
              :class="{ valid: !v$.mainAssetUUID.$invalid, invalid: v$.mainAssetUUID.$invalid }"
              v-model="v$.mainAssetUUID.$model"
            />
            <div v-if="v$.mainAssetUUID.$anyDirty && v$.mainAssetUUID.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainAssetUUID.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.characterDetailsJSON')"
              for="artwork-cast-characterDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="characterDetailsJSON"
              id="artwork-cast-characterDetailsJSON"
              data-cy="characterDetailsJSON"
              :class="{ valid: !v$.characterDetailsJSON.$invalid, invalid: v$.characterDetailsJSON.$invalid }"
              v-model="v$.characterDetailsJSON.$model"
            />
            <div v-if="v$.characterDetailsJSON.$anyDirty && v$.characterDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.characterDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.artwork')"
              for="artwork-cast-artwork"
            ></label>
            <select class="form-control" id="artwork-cast-artwork" data-cy="artwork" name="artwork" v-model="artworkCast.artwork" required>
              <option v-if="!artworkCast.artwork" v-bind:value="null" selected></option>
              <option
                v-bind:value="artworkCast.artwork && artworkOption.id === artworkCast.artwork.id ? artworkCast.artwork : artworkOption"
                v-for="artworkOption in artworks"
                :key="artworkOption.id"
              >
                {{ artworkOption.artworkTitle }}
              </option>
            </select>
          </div>
          <div v-if="v$.artwork.$anyDirty && v$.artwork.$invalid">
            <small class="form-text text-danger" v-for="error of v$.artwork.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.artist')"
              for="artwork-cast-artist"
            ></label>
            <select class="form-control" id="artwork-cast-artist" data-cy="artist" name="artist" v-model="artworkCast.artist" required>
              <option v-if="!artworkCast.artist" v-bind:value="null" selected></option>
              <option
                v-bind:value="artworkCast.artist && artistOption.id === artworkCast.artist.id ? artworkCast.artist : artistOption"
                v-for="artistOption in artists"
                :key="artistOption.id"
              >
                {{ artistOption.publicName }}
              </option>
            </select>
          </div>
          <div v-if="v$.artist.$anyDirty && v$.artist.$invalid">
            <small class="form-text text-danger" v-for="error of v$.artist.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./artwork-cast-update.component.ts"></script>
