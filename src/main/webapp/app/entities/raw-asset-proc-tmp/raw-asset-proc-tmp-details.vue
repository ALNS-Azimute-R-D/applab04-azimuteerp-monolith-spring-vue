<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rawAssetProcTmp">
        <h2 class="jh-entity-heading" data-cy="rawAssetProcTmpDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.detail.title')"></span> {{ rawAssetProcTmp.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.name')"></span>
          </dt>
          <dd>
            <span>{{ rawAssetProcTmp.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.statusRawProcessing')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.StatusRawProcessingEnum.' + rawAssetProcTmp.statusRawProcessing)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.fullFilenamePath')"></span>
          </dt>
          <dd>
            <span>{{ rawAssetProcTmp.fullFilenamePath }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.assetRawContentAsBlob')"></span>
          </dt>
          <dd>
            <div v-if="rawAssetProcTmp.assetRawContentAsBlob">
              <a
                v-on:click="openFile(rawAssetProcTmp.assetRawContentAsBlobContentType, rawAssetProcTmp.assetRawContentAsBlob)"
                v-text="t$('entity.action.open')"
              ></a>
              {{ rawAssetProcTmp.assetRawContentAsBlobContentType }}, {{ byteSize(rawAssetProcTmp.assetRawContentAsBlob) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ rawAssetProcTmp.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.assetType')"></span>
          </dt>
          <dd>
            <div v-if="rawAssetProcTmp.assetType">
              <router-link :to="{ name: 'AssetTypeView', params: { assetTypeId: rawAssetProcTmp.assetType.id } }">{{
                rawAssetProcTmp.assetType.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rawAssetProcTmp.id"
          :to="{ name: 'RawAssetProcTmpEdit', params: { rawAssetProcTmpId: rawAssetProcTmp.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./raw-asset-proc-tmp-details.component.ts"></script>
