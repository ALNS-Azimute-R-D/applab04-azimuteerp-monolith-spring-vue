<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfArtmedia">
        <h2 class="jh-entity-heading" data-cy="typeOfArtmediaDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.detail.title')"></span> {{ typeOfArtmedia.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.acronym')"></span>
          </dt>
          <dd>
            <span>{{ typeOfArtmedia.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.name')"></span>
          </dt>
          <dd>
            <span>{{ typeOfArtmedia.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfArtmedia.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfArtmedia.id"
          :to="{ name: 'TypeOfArtmediaEdit', params: { typeOfArtmediaId: typeOfArtmedia.id } }"
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

<script lang="ts" src="./type-of-artmedia-details.component.ts"></script>
