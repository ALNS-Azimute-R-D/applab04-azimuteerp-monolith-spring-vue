<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="venue">
        <h2 class="jh-entity-heading" data-cy="venueDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.detail.title')"></span> {{ venue.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.acronym')"></span>
          </dt>
          <dd>
            <span>{{ venue.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.name')"></span>
          </dt>
          <dd>
            <span>{{ venue.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.description')"></span>
          </dt>
          <dd>
            <span>{{ venue.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.geoPointLocation')"></span>
          </dt>
          <dd>
            <div v-if="venue.geoPointLocation">
              <a v-on:click="openFile(venue.geoPointLocationContentType, venue.geoPointLocation)" v-text="t$('entity.action.open')"></a>
              {{ venue.geoPointLocationContentType }}, {{ byteSize(venue.geoPointLocation) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.typeOfVenue')"></span>
          </dt>
          <dd>
            <div v-if="venue.typeOfVenue">
              <router-link :to="{ name: 'TypeOfVenueView', params: { typeOfVenueId: venue.typeOfVenue.id } }">{{
                venue.typeOfVenue.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.venue.commonLocality')"></span>
          </dt>
          <dd>
            <div v-if="venue.commonLocality">
              <router-link :to="{ name: 'CommonLocalityView', params: { commonLocalityId: venue.commonLocality.id } }">{{
                venue.commonLocality.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="venue.id" :to="{ name: 'VenueEdit', params: { venueId: venue.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./venue-details.component.ts"></script>
