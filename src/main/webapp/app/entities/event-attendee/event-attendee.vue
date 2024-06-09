<template>
  <div>
    <h2 id="page-heading" data-cy="EventAttendeeHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.home.title')" id="event-attendee-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EventAttendeeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-event-attendee"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && eventAttendees && eventAttendees.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="eventAttendees && eventAttendees.length > 0">
      <table class="table table-striped" aria-describedby="eventAttendees">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attendedAsRole')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.attendedAsRole')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attendedAsRole'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wasPresentInEvent')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.wasPresentInEvent')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wasPresentInEvent'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('shouldBuyTicket')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.shouldBuyTicket')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shouldBuyTicket'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ticketNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.ticketNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ticketNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('seatNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.seatNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'seatNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attendeePerson.fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.attendeePerson')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'attendeePerson.fullname'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('event.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.event')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'event.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ticketPurchased.buyingCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.ticketPurchased')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'ticketPurchased.buyingCode'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="eventAttendee in eventAttendees" :key="eventAttendee.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EventAttendeeView', params: { eventAttendeeId: eventAttendee.id } }">{{
                eventAttendee.id
              }}</router-link>
            </td>
            <td>{{ eventAttendee.attendedAsRole }}</td>
            <td>{{ eventAttendee.wasPresentInEvent }}</td>
            <td>{{ eventAttendee.shouldBuyTicket }}</td>
            <td>{{ eventAttendee.ticketNumber }}</td>
            <td>{{ eventAttendee.seatNumber }}</td>
            <td>
              <div v-if="eventAttendee.attendeePerson">
                <router-link :to="{ name: 'PersonView', params: { personId: eventAttendee.attendeePerson.id } }">{{
                  eventAttendee.attendeePerson.fullname
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="eventAttendee.event">
                <router-link :to="{ name: 'EventView', params: { eventId: eventAttendee.event.id } }">{{
                  eventAttendee.event.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="eventAttendee.ticketPurchased">
                <router-link :to="{ name: 'TicketPurchasedView', params: { ticketPurchasedId: eventAttendee.ticketPurchased.id } }">{{
                  eventAttendee.ticketPurchased.buyingCode
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'EventAttendeeView', params: { eventAttendeeId: eventAttendee.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'EventAttendeeEdit', params: { eventAttendeeId: eventAttendee.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(eventAttendee)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="azimuteErpSpringVueMonolith04App.eventAttendee.delete.question"
          data-cy="eventAttendeeDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-eventAttendee-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-eventAttendee"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEventAttendee()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="eventAttendees && eventAttendees.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event-attendee.component.ts"></script>
