<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.ticketPurchased.home.createOrEditLabel"
          data-cy="TicketPurchasedCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="ticketPurchased.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="ticketPurchased.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.buyingCode')"
              for="ticket-purchased-buyingCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="buyingCode"
              id="ticket-purchased-buyingCode"
              data-cy="buyingCode"
              :class="{ valid: !v$.buyingCode.$invalid, invalid: v$.buyingCode.$invalid }"
              v-model="v$.buyingCode.$model"
            />
            <div v-if="v$.buyingCode.$anyDirty && v$.buyingCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.buyingCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.duePaymentDate')"
              for="ticket-purchased-duePaymentDate"
            ></label>
            <div class="d-flex">
              <input
                id="ticket-purchased-duePaymentDate"
                data-cy="duePaymentDate"
                type="datetime-local"
                class="form-control"
                name="duePaymentDate"
                :class="{ valid: !v$.duePaymentDate.$invalid, invalid: v$.duePaymentDate.$invalid }"
                :value="convertDateTimeFromServer(v$.duePaymentDate.$model)"
                @change="updateInstantField('duePaymentDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.amountOfTickets')"
              for="ticket-purchased-amountOfTickets"
            ></label>
            <input
              type="number"
              class="form-control"
              name="amountOfTickets"
              id="ticket-purchased-amountOfTickets"
              data-cy="amountOfTickets"
              :class="{ valid: !v$.amountOfTickets.$invalid, invalid: v$.amountOfTickets.$invalid }"
              v-model.number="v$.amountOfTickets.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.taxValue')"
              for="ticket-purchased-taxValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="taxValue"
              id="ticket-purchased-taxValue"
              data-cy="taxValue"
              :class="{ valid: !v$.taxValue.$invalid, invalid: v$.taxValue.$invalid }"
              v-model.number="v$.taxValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.ticketValue')"
              for="ticket-purchased-ticketValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="ticketValue"
              id="ticket-purchased-ticketValue"
              data-cy="ticketValue"
              :class="{ valid: !v$.ticketValue.$invalid, invalid: v$.ticketValue.$invalid }"
              v-model.number="v$.ticketValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.acquiredSeatsNumbers')"
              for="ticket-purchased-acquiredSeatsNumbers"
            ></label>
            <input
              type="text"
              class="form-control"
              name="acquiredSeatsNumbers"
              id="ticket-purchased-acquiredSeatsNumbers"
              data-cy="acquiredSeatsNumbers"
              :class="{ valid: !v$.acquiredSeatsNumbers.$invalid, invalid: v$.acquiredSeatsNumbers.$invalid }"
              v-model="v$.acquiredSeatsNumbers.$model"
            />
            <div v-if="v$.acquiredSeatsNumbers.$anyDirty && v$.acquiredSeatsNumbers.$invalid">
              <small class="form-text text-danger" v-for="error of v$.acquiredSeatsNumbers.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.description')"
              for="ticket-purchased-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="ticket-purchased-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.event')"
              for="ticket-purchased-event"
            ></label>
            <select class="form-control" id="ticket-purchased-event" data-cy="event" name="event" v-model="ticketPurchased.event">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ticketPurchased.event && eventOption.id === ticketPurchased.event.id ? ticketPurchased.event : eventOption"
                v-for="eventOption in events"
                :key="eventOption.id"
              >
                {{ eventOption.acronym }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.ticketPurchased.invoice')"
              for="ticket-purchased-invoice"
            ></label>
            <select class="form-control" id="ticket-purchased-invoice" data-cy="invoice" name="invoice" v-model="ticketPurchased.invoice">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  ticketPurchased.invoice && invoiceOption.id === ticketPurchased.invoice.id ? ticketPurchased.invoice : invoiceOption
                "
                v-for="invoiceOption in invoices"
                :key="invoiceOption.id"
              >
                {{ invoiceOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ticket-purchased-update.component.ts"></script>
