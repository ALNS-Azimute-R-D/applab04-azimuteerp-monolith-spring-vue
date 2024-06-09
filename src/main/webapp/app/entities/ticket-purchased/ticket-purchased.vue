<template>
  <div>
    <h2 id="page-heading" data-cy="TicketPurchasedHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.home.title')" id="ticket-purchased-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TicketPurchasedCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ticket-purchased"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ticketPurchaseds && ticketPurchaseds.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="ticketPurchaseds && ticketPurchaseds.length > 0">
      <table class="table table-striped" aria-describedby="ticketPurchaseds">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('buyingCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.buyingCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'buyingCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('duePaymentDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.duePaymentDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duePaymentDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amountOfTickets')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.amountOfTickets')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amountOfTickets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taxValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.taxValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taxValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ticketValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.ticketValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ticketValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acquiredSeatsNumbers')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.acquiredSeatsNumbers')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acquiredSeatsNumbers'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('event.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.event')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'event.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invoice.id')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.invoice')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invoice.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ticketPurchased in ticketPurchaseds" :key="ticketPurchased.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TicketPurchasedView', params: { ticketPurchasedId: ticketPurchased.id } }">{{
                ticketPurchased.id
              }}</router-link>
            </td>
            <td>{{ ticketPurchased.buyingCode }}</td>
            <td>{{ formatDateShort(ticketPurchased.duePaymentDate) || '' }}</td>
            <td>{{ ticketPurchased.amountOfTickets }}</td>
            <td>{{ ticketPurchased.taxValue }}</td>
            <td>{{ ticketPurchased.ticketValue }}</td>
            <td>{{ ticketPurchased.acquiredSeatsNumbers }}</td>
            <td>{{ ticketPurchased.description }}</td>
            <td>
              <div v-if="ticketPurchased.event">
                <router-link :to="{ name: 'EventView', params: { eventId: ticketPurchased.event.id } }">{{
                  ticketPurchased.event.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="ticketPurchased.invoice">
                <router-link :to="{ name: 'InvoiceView', params: { invoiceId: ticketPurchased.invoice.id } }">{{
                  ticketPurchased.invoice.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TicketPurchasedView', params: { ticketPurchasedId: ticketPurchased.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TicketPurchasedEdit', params: { ticketPurchasedId: ticketPurchased.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ticketPurchased)"
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
          id="azimuteErpSpringVueMonolith04App.ticketPurchased.delete.question"
          data-cy="ticketPurchasedDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-ticketPurchased-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-ticketPurchased"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTicketPurchased()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="ticketPurchaseds && ticketPurchaseds.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ticket-purchased.component.ts"></script>
