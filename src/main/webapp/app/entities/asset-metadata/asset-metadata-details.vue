<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="assetMetadata">
        <h2 class="jh-entity-heading" data-cy="assetMetadataDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.detail.title')"></span> {{ assetMetadata.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.metadataDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ assetMetadata.metadataDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.asset')"></span>
          </dt>
          <dd>
            <div v-if="assetMetadata.asset">
              <router-link :to="{ name: 'AssetView', params: { assetId: assetMetadata.asset.id } }">{{
                assetMetadata.asset.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="assetMetadata.id"
          :to="{ name: 'AssetMetadataEdit', params: { assetMetadataId: assetMetadata.id } }"
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

<script lang="ts" src="./asset-metadata-details.component.ts"></script>
