<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="eventAttendee">
        <h2 class="jh-entity-heading" data-cy="eventAttendeeDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.detail.title')"></span> {{ eventAttendee.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.attendedAsRole')"></span>
          </dt>
          <dd>
            <span>{{ eventAttendee.attendedAsRole }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.wasPresentInEvent')"></span>
          </dt>
          <dd>
            <span>{{ eventAttendee.wasPresentInEvent }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.shouldBuyTicket')"></span>
          </dt>
          <dd>
            <span>{{ eventAttendee.shouldBuyTicket }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.ticketNumber')"></span>
          </dt>
          <dd>
            <span>{{ eventAttendee.ticketNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.seatNumber')"></span>
          </dt>
          <dd>
            <span>{{ eventAttendee.seatNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.attendeePerson')"></span>
          </dt>
          <dd>
            <div v-if="eventAttendee.attendeePerson">
              <router-link :to="{ name: 'PersonView', params: { personId: eventAttendee.attendeePerson.id } }">{{
                eventAttendee.attendeePerson.fullname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.event')"></span>
          </dt>
          <dd>
            <div v-if="eventAttendee.event">
              <router-link :to="{ name: 'EventView', params: { eventId: eventAttendee.event.id } }">{{
                eventAttendee.event.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.ticketPurchased')"></span>
          </dt>
          <dd>
            <div v-if="eventAttendee.ticketPurchased">
              <router-link :to="{ name: 'TicketPurchasedView', params: { ticketPurchasedId: eventAttendee.ticketPurchased.id } }">{{
                eventAttendee.ticketPurchased.buyingCode
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="eventAttendee.id"
          :to="{ name: 'EventAttendeeEdit', params: { eventAttendeeId: eventAttendee.id } }"
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

<script lang="ts" src="./event-attendee-details.component.ts"></script>
