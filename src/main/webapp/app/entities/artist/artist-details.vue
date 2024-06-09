<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="artist">
        <h2 class="jh-entity-heading" data-cy="artistDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.detail.title')"></span> {{ artist.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.acronym')"></span>
          </dt>
          <dd>
            <span>{{ artist.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.publicName')"></span>
          </dt>
          <dd>
            <span>{{ artist.publicName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.realName')"></span>
          </dt>
          <dd>
            <span>{{ artist.realName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.biographyDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ artist.biographyDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.typeOfArtmedia')"></span>
          </dt>
          <dd>
            <div v-if="artist.typeOfArtmedia">
              <router-link :to="{ name: 'TypeOfArtmediaView', params: { typeOfArtmediaId: artist.typeOfArtmedia.id } }">{{
                artist.typeOfArtmedia.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.typeOfArtist')"></span>
          </dt>
          <dd>
            <div v-if="artist.typeOfArtist">
              <router-link :to="{ name: 'TypeOfArtistView', params: { typeOfArtistId: artist.typeOfArtist.id } }">{{
                artist.typeOfArtist.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.artistAggregator')"></span>
          </dt>
          <dd>
            <div v-if="artist.artistAggregator">
              <router-link :to="{ name: 'ArtistView', params: { artistId: artist.artistAggregator.id } }">{{
                artist.artistAggregator.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.artist')"></span>
          </dt>
          <dd>
            <span v-for="(artist, i) in artist.artists" :key="artist.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ArtisticGenreView', params: { artisticGenreId: artist.id } }">{{ artist.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="artist.id" :to="{ name: 'ArtistEdit', params: { artistId: artist.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./artist-details.component.ts"></script>
