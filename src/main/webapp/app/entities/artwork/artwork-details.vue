<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="artwork">
        <h2 class="jh-entity-heading" data-cy="artworkDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.detail.title')"></span> {{ artwork.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.artworkTitle')"></span>
          </dt>
          <dd>
            <span>{{ artwork.artworkTitle }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.productionYear')"></span>
          </dt>
          <dd>
            <span>{{ artwork.productionYear }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.seasonNumber')"></span>
          </dt>
          <dd>
            <span>{{ artwork.seasonNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.episodeOrSequenceNumber')"></span>
          </dt>
          <dd>
            <span>{{ artwork.episodeOrSequenceNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.registerIdInIMDB')"></span>
          </dt>
          <dd>
            <span>{{ artwork.registerIdInIMDB }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.assetsCollectionUUID')"></span>
          </dt>
          <dd>
            <span>{{ artwork.assetsCollectionUUID }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.contentDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ artwork.contentDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.typeOfArtmedia')"></span>
          </dt>
          <dd>
            <div v-if="artwork.typeOfArtmedia">
              <router-link :to="{ name: 'TypeOfArtmediaView', params: { typeOfArtmediaId: artwork.typeOfArtmedia.id } }">{{
                artwork.typeOfArtmedia.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.artworkAggregator')"></span>
          </dt>
          <dd>
            <div v-if="artwork.artworkAggregator">
              <router-link :to="{ name: 'ArtworkView', params: { artworkId: artwork.artworkAggregator.id } }">{{
                artwork.artworkAggregator.artworkTitle
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="artwork.id" :to="{ name: 'ArtworkEdit', params: { artworkId: artwork.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./artwork-details.component.ts"></script>
