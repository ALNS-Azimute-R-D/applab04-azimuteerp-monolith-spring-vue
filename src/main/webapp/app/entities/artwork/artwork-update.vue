<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.artwork.home.createOrEditLabel"
          data-cy="ArtworkCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.artwork.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="artwork.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="artwork.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.artworkTitle')"
              for="artwork-artworkTitle"
            ></label>
            <input
              type="text"
              class="form-control"
              name="artworkTitle"
              id="artwork-artworkTitle"
              data-cy="artworkTitle"
              :class="{ valid: !v$.artworkTitle.$invalid, invalid: v$.artworkTitle.$invalid }"
              v-model="v$.artworkTitle.$model"
              required
            />
            <div v-if="v$.artworkTitle.$anyDirty && v$.artworkTitle.$invalid">
              <small class="form-text text-danger" v-for="error of v$.artworkTitle.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.productionYear')"
              for="artwork-productionYear"
            ></label>
            <input
              type="number"
              class="form-control"
              name="productionYear"
              id="artwork-productionYear"
              data-cy="productionYear"
              :class="{ valid: !v$.productionYear.$invalid, invalid: v$.productionYear.$invalid }"
              v-model.number="v$.productionYear.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.seasonNumber')"
              for="artwork-seasonNumber"
            ></label>
            <input
              type="number"
              class="form-control"
              name="seasonNumber"
              id="artwork-seasonNumber"
              data-cy="seasonNumber"
              :class="{ valid: !v$.seasonNumber.$invalid, invalid: v$.seasonNumber.$invalid }"
              v-model.number="v$.seasonNumber.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.episodeOrSequenceNumber')"
              for="artwork-episodeOrSequenceNumber"
            ></label>
            <input
              type="number"
              class="form-control"
              name="episodeOrSequenceNumber"
              id="artwork-episodeOrSequenceNumber"
              data-cy="episodeOrSequenceNumber"
              :class="{ valid: !v$.episodeOrSequenceNumber.$invalid, invalid: v$.episodeOrSequenceNumber.$invalid }"
              v-model.number="v$.episodeOrSequenceNumber.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.registerIdInIMDB')"
              for="artwork-registerIdInIMDB"
            ></label>
            <input
              type="text"
              class="form-control"
              name="registerIdInIMDB"
              id="artwork-registerIdInIMDB"
              data-cy="registerIdInIMDB"
              :class="{ valid: !v$.registerIdInIMDB.$invalid, invalid: v$.registerIdInIMDB.$invalid }"
              v-model="v$.registerIdInIMDB.$model"
            />
            <div v-if="v$.registerIdInIMDB.$anyDirty && v$.registerIdInIMDB.$invalid">
              <small class="form-text text-danger" v-for="error of v$.registerIdInIMDB.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.assetsCollectionUUID')"
              for="artwork-assetsCollectionUUID"
            ></label>
            <input
              type="text"
              class="form-control"
              name="assetsCollectionUUID"
              id="artwork-assetsCollectionUUID"
              data-cy="assetsCollectionUUID"
              :class="{ valid: !v$.assetsCollectionUUID.$invalid, invalid: v$.assetsCollectionUUID.$invalid }"
              v-model="v$.assetsCollectionUUID.$model"
            />
            <div v-if="v$.assetsCollectionUUID.$anyDirty && v$.assetsCollectionUUID.$invalid">
              <small class="form-text text-danger" v-for="error of v$.assetsCollectionUUID.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.contentDetailsJSON')"
              for="artwork-contentDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="contentDetailsJSON"
              id="artwork-contentDetailsJSON"
              data-cy="contentDetailsJSON"
              :class="{ valid: !v$.contentDetailsJSON.$invalid, invalid: v$.contentDetailsJSON.$invalid }"
              v-model="v$.contentDetailsJSON.$model"
            />
            <div v-if="v$.contentDetailsJSON.$anyDirty && v$.contentDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.contentDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.typeOfArtmedia')"
              for="artwork-typeOfArtmedia"
            ></label>
            <select
              class="form-control"
              id="artwork-typeOfArtmedia"
              data-cy="typeOfArtmedia"
              name="typeOfArtmedia"
              v-model="artwork.typeOfArtmedia"
              required
            >
              <option v-if="!artwork.typeOfArtmedia" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  artwork.typeOfArtmedia && typeOfArtmediaOption.id === artwork.typeOfArtmedia.id
                    ? artwork.typeOfArtmedia
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
              v-text="t$('azimuteErpSpringVueMonolith04App.artwork.artworkAggregator')"
              for="artwork-artworkAggregator"
            ></label>
            <select
              class="form-control"
              id="artwork-artworkAggregator"
              data-cy="artworkAggregator"
              name="artworkAggregator"
              v-model="artwork.artworkAggregator"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  artwork.artworkAggregator && artworkOption.id === artwork.artworkAggregator.id ? artwork.artworkAggregator : artworkOption
                "
                v-for="artworkOption in artworks"
                :key="artworkOption.id"
              >
                {{ artworkOption.artworkTitle }}
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
<script lang="ts" src="./artwork-update.component.ts"></script>
