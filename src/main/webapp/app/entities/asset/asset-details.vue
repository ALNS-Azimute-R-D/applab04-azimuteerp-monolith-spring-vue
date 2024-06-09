<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="asset">
        <h2 class="jh-entity-heading" data-cy="assetDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.detail.title')"></span> {{ asset.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.name')"></span>
          </dt>
          <dd>
            <span>{{ asset.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.storageTypeUsed')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.StorageTypeEnum.' + asset.storageTypeUsed)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.fullFilenamePath')"></span>
          </dt>
          <dd>
            <span>{{ asset.fullFilenamePath }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.StatusAssetEnum.' + asset.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.preferredPurpose')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.PreferredPurposeEnum.' + asset.preferredPurpose)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetContentAsBlob')"></span>
          </dt>
          <dd>
            <div v-if="asset.assetContentAsBlob">
              <a v-on:click="openFile(asset.assetContentAsBlobContentType, asset.assetContentAsBlob)" v-text="t$('entity.action.open')"></a>
              {{ asset.assetContentAsBlobContentType }}, {{ byteSize(asset.assetContentAsBlob) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + asset.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetType')"></span>
          </dt>
          <dd>
            <div v-if="asset.assetType">
              <router-link :to="{ name: 'AssetTypeView', params: { assetTypeId: asset.assetType.id } }">{{
                asset.assetType.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.rawAssetProcTmp')"></span>
          </dt>
          <dd>
            <div v-if="asset.rawAssetProcTmp">
              <router-link :to="{ name: 'RawAssetProcTmpView', params: { rawAssetProcTmpId: asset.rawAssetProcTmp.id } }">{{
                asset.rawAssetProcTmp.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetCollection')"></span>
          </dt>
          <dd>
            <span v-for="(assetCollection, i) in asset.assetCollections" :key="assetCollection.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'AssetCollectionView', params: { assetCollectionId: assetCollection.id } }">{{
                assetCollection.id
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="asset.id" :to="{ name: 'AssetEdit', params: { assetId: asset.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./asset-details.component.ts"></script>
