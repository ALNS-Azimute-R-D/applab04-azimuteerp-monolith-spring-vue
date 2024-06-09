<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfArtist">
        <h2 class="jh-entity-heading" data-cy="typeOfArtistDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtist.detail.title')"></span> {{ typeOfArtist.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtist.acronym')"></span>
          </dt>
          <dd>
            <span>{{ typeOfArtist.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtist.name')"></span>
          </dt>
          <dd>
            <span>{{ typeOfArtist.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtist.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfArtist.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfArtist.id"
          :to="{ name: 'TypeOfArtistEdit', params: { typeOfArtistId: typeOfArtist.id } }"
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

<script lang="ts" src="./type-of-artist-details.component.ts"></script>
