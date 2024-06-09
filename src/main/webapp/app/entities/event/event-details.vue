<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="event">
        <h2 class="jh-entity-heading" data-cy="eventDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.event.detail.title')"></span> {{ event.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.acronym')"></span>
          </dt>
          <dd>
            <span>{{ event.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.name')"></span>
          </dt>
          <dd>
            <span>{{ event.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.description')"></span>
          </dt>
          <dd>
            <span>{{ event.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.fullDescription')"></span>
          </dt>
          <dd>
            <span>{{ event.fullDescription }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.startTime')"></span>
          </dt>
          <dd>
            <span v-if="event.startTime">{{ formatDateLong(event.startTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.endTime')"></span>
          </dt>
          <dd>
            <span v-if="event.endTime">{{ formatDateLong(event.endTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.defaultTicketValue')"></span>
          </dt>
          <dd>
            <span>{{ event.defaultTicketValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.EventStatusEnum.' + event.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + event.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.mainVenue')"></span>
          </dt>
          <dd>
            <div v-if="event.mainVenue">
              <router-link :to="{ name: 'VenueView', params: { venueId: event.mainVenue.id } }">{{ event.mainVenue.acronym }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.typeOfEvent')"></span>
          </dt>
          <dd>
            <div v-if="event.typeOfEvent">
              <router-link :to="{ name: 'TypeOfEventView', params: { typeOfEventId: event.typeOfEvent.id } }">{{
                event.typeOfEvent.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.promoteurPerson')"></span>
          </dt>
          <dd>
            <div v-if="event.promoteurPerson">
              <router-link :to="{ name: 'PersonView', params: { personId: event.promoteurPerson.id } }">{{
                event.promoteurPerson.fullname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.assetCollection')"></span>
          </dt>
          <dd>
            <span v-for="(assetCollection, i) in event.assetCollections" :key="assetCollection.id"
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
        <router-link v-if="event.id" :to="{ name: 'EventEdit', params: { eventId: event.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event-details.component.ts"></script>
