<template>
  <div>
    <h2 id="page-heading" data-cy="EventHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.event.home.title')" id="event-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.event.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EventCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-event"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.event.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && events && events.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.event.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="events && events.length > 0">
      <table class="table table-striped" aria-describedby="events">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullDescription')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.fullDescription')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullDescription'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('startTime')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.startTime')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('endTime')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.endTime')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('defaultTicketValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.defaultTicketValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'defaultTicketValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainVenue.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.mainVenue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainVenue.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfEvent.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.typeOfEvent')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfEvent.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('promoteurPerson.fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.event.promoteurPerson')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'promoteurPerson.fullname'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="event in events" :key="event.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EventView', params: { eventId: event.id } }">{{ event.id }}</router-link>
            </td>
            <td>{{ event.acronym }}</td>
            <td>{{ event.name }}</td>
            <td>{{ event.description }}</td>
            <td>{{ event.fullDescription }}</td>
            <td>{{ formatDateShort(event.startTime) || '' }}</td>
            <td>{{ formatDateShort(event.endTime) || '' }}</td>
            <td>{{ event.defaultTicketValue }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.EventStatusEnum.' + event.status)"></td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + event.activationStatus)"></td>
            <td>
              <div v-if="event.mainVenue">
                <router-link :to="{ name: 'VenueView', params: { venueId: event.mainVenue.id } }">{{
                  event.mainVenue.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="event.typeOfEvent">
                <router-link :to="{ name: 'TypeOfEventView', params: { typeOfEventId: event.typeOfEvent.id } }">{{
                  event.typeOfEvent.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="event.promoteurPerson">
                <router-link :to="{ name: 'PersonView', params: { personId: event.promoteurPerson.id } }">{{
                  event.promoteurPerson.fullname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EventView', params: { eventId: event.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EventEdit', params: { eventId: event.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(event)"
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
          id="azimuteErpSpringVueMonolith04App.event.delete.question"
          data-cy="eventDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-event-heading" v-text="t$('azimuteErpSpringVueMonolith04App.event.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-event"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEvent()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="events && events.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event.component.ts"></script>
