<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfVenue">
        <h2 class="jh-entity-heading" data-cy="typeOfVenueDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfVenue.detail.title')"></span> {{ typeOfVenue.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfVenue.acronym')"></span>
          </dt>
          <dd>
            <span>{{ typeOfVenue.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfVenue.name')"></span>
          </dt>
          <dd>
            <span>{{ typeOfVenue.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfVenue.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfVenue.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfVenue.handlerClazzName')"></span>
          </dt>
          <dd>
            <span>{{ typeOfVenue.handlerClazzName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfVenue.id"
          :to="{ name: 'TypeOfVenueEdit', params: { typeOfVenueId: typeOfVenue.id } }"
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

<script lang="ts" src="./type-of-venue-details.component.ts"></script>
