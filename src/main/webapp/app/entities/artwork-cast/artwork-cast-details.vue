<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="artworkCast">
        <h2 class="jh-entity-heading" data-cy="artworkCastDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.detail.title')"></span> {{ artworkCast.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.orderOfAppearance')"></span>
          </dt>
          <dd>
            <span>{{ artworkCast.orderOfAppearance }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.characterName')"></span>
          </dt>
          <dd>
            <span>{{ artworkCast.characterName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.mainAssetUUID')"></span>
          </dt>
          <dd>
            <span>{{ artworkCast.mainAssetUUID }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.characterDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ artworkCast.characterDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.artwork')"></span>
          </dt>
          <dd>
            <div v-if="artworkCast.artwork">
              <router-link :to="{ name: 'ArtworkView', params: { artworkId: artworkCast.artwork.id } }">{{
                artworkCast.artwork.artworkTitle
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artworkCast.artist')"></span>
          </dt>
          <dd>
            <div v-if="artworkCast.artist">
              <router-link :to="{ name: 'ArtistView', params: { artistId: artworkCast.artist.id } }">{{
                artworkCast.artist.publicName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="artworkCast.id"
          :to="{ name: 'ArtworkCastEdit', params: { artworkCastId: artworkCast.id } }"
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

<script lang="ts" src="./artwork-cast-details.component.ts"></script>
