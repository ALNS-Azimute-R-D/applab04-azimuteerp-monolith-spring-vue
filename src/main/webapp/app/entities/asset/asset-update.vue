<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.asset.home.createOrEditLabel"
          data-cy="AssetCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.asset.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="asset.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="asset.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.asset.name')" for="asset-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="asset-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.asset.storageTypeUsed')"
              for="asset-storageTypeUsed"
            ></label>
            <select
              class="form-control"
              name="storageTypeUsed"
              :class="{ valid: !v$.storageTypeUsed.$invalid, invalid: v$.storageTypeUsed.$invalid }"
              v-model="v$.storageTypeUsed.$model"
              id="asset-storageTypeUsed"
              data-cy="storageTypeUsed"
            >
              <option
                v-for="storageTypeEnum in storageTypeEnumValues"
                :key="storageTypeEnum"
                v-bind:value="storageTypeEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.StorageTypeEnum.' + storageTypeEnum)"
              >
                {{ storageTypeEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.asset.fullFilenamePath')"
              for="asset-fullFilenamePath"
            ></label>
            <input
              type="text"
              class="form-control"
              name="fullFilenamePath"
              id="asset-fullFilenamePath"
              data-cy="fullFilenamePath"
              :class="{ valid: !v$.fullFilenamePath.$invalid, invalid: v$.fullFilenamePath.$invalid }"
              v-model="v$.fullFilenamePath.$model"
            />
            <div v-if="v$.fullFilenamePath.$anyDirty && v$.fullFilenamePath.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fullFilenamePath.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.asset.status')" for="asset-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="asset-status"
              data-cy="status"
            >
              <option
                v-for="statusAssetEnum in statusAssetEnumValues"
                :key="statusAssetEnum"
                v-bind:value="statusAssetEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.StatusAssetEnum.' + statusAssetEnum)"
              >
                {{ statusAssetEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.asset.preferredPurpose')"
              for="asset-preferredPurpose"
            ></label>
            <select
              class="form-control"
              name="preferredPurpose"
              :class="{ valid: !v$.preferredPurpose.$invalid, invalid: v$.preferredPurpose.$invalid }"
              v-model="v$.preferredPurpose.$model"
              id="asset-preferredPurpose"
              data-cy="preferredPurpose"
            >
              <option
                v-for="preferredPurposeEnum in preferredPurposeEnumValues"
                :key="preferredPurposeEnum"
                v-bind:value="preferredPurposeEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.PreferredPurposeEnum.' + preferredPurposeEnum)"
              >
                {{ preferredPurposeEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetContentAsBlob')"
              for="asset-assetContentAsBlob"
            ></label>
            <div>
              <div v-if="asset.assetContentAsBlob" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(asset.assetContentAsBlobContentType, asset.assetContentAsBlob)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ asset.assetContentAsBlobContentType }}, {{ byteSize(asset.assetContentAsBlob) }}</span>
                <button
                  type="button"
                  v-on:click="
                    asset.assetContentAsBlob = null;
                    asset.assetContentAsBlobContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_assetContentAsBlob" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_assetContentAsBlob"
                id="file_assetContentAsBlob"
                style="display: none"
                data-cy="assetContentAsBlob"
                v-on:change="setFileData($event, asset, 'assetContentAsBlob', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="assetContentAsBlob"
              id="asset-assetContentAsBlob"
              data-cy="assetContentAsBlob"
              :class="{ valid: !v$.assetContentAsBlob.$invalid, invalid: v$.assetContentAsBlob.$invalid }"
              v-model="v$.assetContentAsBlob.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="assetContentAsBlobContentType"
              id="asset-assetContentAsBlobContentType"
              v-model="asset.assetContentAsBlobContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.asset.activationStatus')"
              for="asset-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="asset-activationStatus"
              data-cy="activationStatus"
              required
            >
              <option
                v-for="activationStatusEnum in activationStatusEnumValues"
                :key="activationStatusEnum"
                v-bind:value="activationStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + activationStatusEnum)"
              >
                {{ activationStatusEnum }}
              </option>
            </select>
            <div v-if="v$.activationStatus.$anyDirty && v$.activationStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.activationStatus.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetType')" for="asset-assetType"></label>
            <select class="form-control" id="asset-assetType" data-cy="assetType" name="assetType" v-model="asset.assetType" required>
              <option v-if="!asset.assetType" v-bind:value="null" selected></option>
              <option
                v-bind:value="asset.assetType && assetTypeOption.id === asset.assetType.id ? asset.assetType : assetTypeOption"
                v-for="assetTypeOption in assetTypes"
                :key="assetTypeOption.id"
              >
                {{ assetTypeOption.name }}
              </option>
            </select>
          </div>
          <div v-if="v$.assetType.$anyDirty && v$.assetType.$invalid">
            <small class="form-text text-danger" v-for="error of v$.assetType.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.asset.rawAssetProcTmp')"
              for="asset-rawAssetProcTmp"
            ></label>
            <select
              class="form-control"
              id="asset-rawAssetProcTmp"
              data-cy="rawAssetProcTmp"
              name="rawAssetProcTmp"
              v-model="asset.rawAssetProcTmp"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  asset.rawAssetProcTmp && rawAssetProcTmpOption.id === asset.rawAssetProcTmp.id
                    ? asset.rawAssetProcTmp
                    : rawAssetProcTmpOption
                "
                v-for="rawAssetProcTmpOption in rawAssetProcTmps"
                :key="rawAssetProcTmpOption.id"
              >
                {{ rawAssetProcTmpOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetCollection')" for="asset-assetCollection"></label>
            <select
              class="form-control"
              id="asset-assetCollections"
              data-cy="assetCollection"
              multiple
              name="assetCollection"
              v-if="asset.assetCollections !== undefined"
              v-model="asset.assetCollections"
            >
              <option
                v-bind:value="getSelected(asset.assetCollections, assetCollectionOption, 'id')"
                v-for="assetCollectionOption in assetCollections"
                :key="assetCollectionOption.id"
              >
                {{ assetCollectionOption.id }}
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
<script lang="ts" src="./asset-update.component.ts"></script>
