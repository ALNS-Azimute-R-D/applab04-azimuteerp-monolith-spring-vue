<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ticketPurchased">
        <h2 class="jh-entity-heading" data-cy="ticketPurchasedDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.detail.title')"></span> {{ ticketPurchased.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.buyingCode')"></span>
          </dt>
          <dd>
            <span>{{ ticketPurchased.buyingCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.duePaymentDate')"></span>
          </dt>
          <dd>
            <span v-if="ticketPurchased.duePaymentDate">{{ formatDateLong(ticketPurchased.duePaymentDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.amountOfTickets')"></span>
          </dt>
          <dd>
            <span>{{ ticketPurchased.amountOfTickets }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.taxValue')"></span>
          </dt>
          <dd>
            <span>{{ ticketPurchased.taxValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.ticketValue')"></span>
          </dt>
          <dd>
            <span>{{ ticketPurchased.ticketValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.acquiredSeatsNumbers')"></span>
          </dt>
          <dd>
            <span>{{ ticketPurchased.acquiredSeatsNumbers }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.description')"></span>
          </dt>
          <dd>
            <span>{{ ticketPurchased.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.event')"></span>
          </dt>
          <dd>
            <div v-if="ticketPurchased.event">
              <router-link :to="{ name: 'EventView', params: { eventId: ticketPurchased.event.id } }">{{
                ticketPurchased.event.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.invoice')"></span>
          </dt>
          <dd>
            <div v-if="ticketPurchased.invoice">
              <router-link :to="{ name: 'InvoiceView', params: { invoiceId: ticketPurchased.invoice.id } }">{{
                ticketPurchased.invoice.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="ticketPurchased.id"
          :to="{ name: 'TicketPurchasedEdit', params: { ticketPurchasedId: ticketPurchased.id } }"
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

<script lang="ts" src="./ticket-purchased-details.component.ts"></script>
