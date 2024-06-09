<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.assetMetadata.home.createOrEditLabel"
          data-cy="AssetMetadataCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="assetMetadata.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="assetMetadata.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.metadataDetailsJSON')"
              for="asset-metadata-metadataDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="metadataDetailsJSON"
              id="asset-metadata-metadataDetailsJSON"
              data-cy="metadataDetailsJSON"
              :class="{ valid: !v$.metadataDetailsJSON.$invalid, invalid: v$.metadataDetailsJSON.$invalid }"
              v-model="v$.metadataDetailsJSON.$model"
            />
            <div v-if="v$.metadataDetailsJSON.$anyDirty && v$.metadataDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.metadataDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.asset')"
              for="asset-metadata-asset"
            ></label>
            <select class="form-control" id="asset-metadata-asset" data-cy="asset" name="asset" v-model="assetMetadata.asset" required>
              <option v-if="!assetMetadata.asset" v-bind:value="null" selected></option>
              <option
                v-bind:value="assetMetadata.asset && assetOption.id === assetMetadata.asset.id ? assetMetadata.asset : assetOption"
                v-for="assetOption in assets"
                :key="assetOption.id"
              >
                {{ assetOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.asset.$anyDirty && v$.asset.$invalid">
            <small class="form-text text-danger" v-for="error of v$.asset.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./asset-metadata-update.component.ts"></script>
