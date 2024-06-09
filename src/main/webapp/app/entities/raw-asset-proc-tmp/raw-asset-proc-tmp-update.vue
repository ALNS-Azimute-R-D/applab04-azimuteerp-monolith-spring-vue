<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.rawAssetProcTmp.home.createOrEditLabel"
          data-cy="RawAssetProcTmpCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rawAssetProcTmp.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rawAssetProcTmp.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.name')"
              for="raw-asset-proc-tmp-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="raw-asset-proc-tmp-name"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.statusRawProcessing')"
              for="raw-asset-proc-tmp-statusRawProcessing"
            ></label>
            <select
              class="form-control"
              name="statusRawProcessing"
              :class="{ valid: !v$.statusRawProcessing.$invalid, invalid: v$.statusRawProcessing.$invalid }"
              v-model="v$.statusRawProcessing.$model"
              id="raw-asset-proc-tmp-statusRawProcessing"
              data-cy="statusRawProcessing"
            >
              <option
                v-for="statusRawProcessingEnum in statusRawProcessingEnumValues"
                :key="statusRawProcessingEnum"
                v-bind:value="statusRawProcessingEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.StatusRawProcessingEnum.' + statusRawProcessingEnum)"
              >
                {{ statusRawProcessingEnum }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.fullFilenamePath')"
              for="raw-asset-proc-tmp-fullFilenamePath"
            ></label>
            <input
              type="text"
              class="form-control"
              name="fullFilenamePath"
              id="raw-asset-proc-tmp-fullFilenamePath"
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
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.assetRawContentAsBlob')"
              for="raw-asset-proc-tmp-assetRawContentAsBlob"
            ></label>
            <div>
              <div v-if="rawAssetProcTmp.assetRawContentAsBlob" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(rawAssetProcTmp.assetRawContentAsBlobContentType, rawAssetProcTmp.assetRawContentAsBlob)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left"
                  >{{ rawAssetProcTmp.assetRawContentAsBlobContentType }}, {{ byteSize(rawAssetProcTmp.assetRawContentAsBlob) }}</span
                >
                <button
                  type="button"
                  v-on:click="
                    rawAssetProcTmp.assetRawContentAsBlob = null;
                    rawAssetProcTmp.assetRawContentAsBlobContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_assetRawContentAsBlob" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_assetRawContentAsBlob"
                id="file_assetRawContentAsBlob"
                style="display: none"
                data-cy="assetRawContentAsBlob"
                v-on:change="setFileData($event, rawAssetProcTmp, 'assetRawContentAsBlob', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="assetRawContentAsBlob"
              id="raw-asset-proc-tmp-assetRawContentAsBlob"
              data-cy="assetRawContentAsBlob"
              :class="{ valid: !v$.assetRawContentAsBlob.$invalid, invalid: v$.assetRawContentAsBlob.$invalid }"
              v-model="v$.assetRawContentAsBlob.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="assetRawContentAsBlobContentType"
              id="raw-asset-proc-tmp-assetRawContentAsBlobContentType"
              v-model="rawAssetProcTmp.assetRawContentAsBlobContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.customAttributesDetailsJSON')"
              for="raw-asset-proc-tmp-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="raw-asset-proc-tmp-customAttributesDetailsJSON"
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
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.assetType')"
              for="raw-asset-proc-tmp-assetType"
            ></label>
            <select
              class="form-control"
              id="raw-asset-proc-tmp-assetType"
              data-cy="assetType"
              name="assetType"
              v-model="rawAssetProcTmp.assetType"
              required
            >
              <option v-if="!rawAssetProcTmp.assetType" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  rawAssetProcTmp.assetType && assetTypeOption.id === rawAssetProcTmp.assetType.id
                    ? rawAssetProcTmp.assetType
                    : assetTypeOption
                "
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
<script lang="ts" src="./raw-asset-proc-tmp-update.component.ts"></script>
