<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="artisticGenre">
        <h2 class="jh-entity-heading" data-cy="artisticGenreDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.detail.title')"></span> {{ artisticGenre.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.acronym')"></span>
          </dt>
          <dd>
            <span>{{ artisticGenre.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.name')"></span>
          </dt>
          <dd>
            <span>{{ artisticGenre.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.description')"></span>
          </dt>
          <dd>
            <span>{{ artisticGenre.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artisticGenre.artisticGenre')"></span>
          </dt>
          <dd>
            <span v-for="(artisticGenre, i) in artisticGenre.artisticGenres" :key="artisticGenre.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ArtistView', params: { artistId: artisticGenre.id } }">{{ artisticGenre.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="artisticGenre.id"
          :to="{ name: 'ArtisticGenreEdit', params: { artisticGenreId: artisticGenre.id } }"
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

<script lang="ts" src="./artistic-genre-details.component.ts"></script>
