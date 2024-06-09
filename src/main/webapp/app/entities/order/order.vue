<template>
  <div>
    <h2 id="page-heading" data-cy="OrderHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.order.home.title')" id="order-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.order.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OrderCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-order"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && orders && orders.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.order.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="orders && orders.length > 0">
      <table class="table table-striped" aria-describedby="orders">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.businessCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('placedDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.placedDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'placedDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalTaxValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.totalTaxValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalTaxValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalDueValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.totalDueValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalDueValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estimatedDeliveryDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.estimatedDeliveryDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estimatedDeliveryDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invoice.description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.invoice')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invoice.description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customer.fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.order.customer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer.fullname'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrderView', params: { orderId: order.id } }">{{ order.id }}</router-link>
            </td>
            <td>{{ order.businessCode }}</td>
            <td>{{ formatDateShort(order.placedDate) || '' }}</td>
            <td>{{ order.totalTaxValue }}</td>
            <td>{{ order.totalDueValue }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.OrderStatusEnum.' + order.status)"></td>
            <td>{{ formatDateShort(order.estimatedDeliveryDate) || '' }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + order.activationStatus)"></td>
            <td>
              <div v-if="order.invoice">
                <router-link :to="{ name: 'InvoiceView', params: { invoiceId: order.invoice.id } }">{{
                  order.invoice.description
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="order.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: order.customer.id } }">{{
                  order.customer.fullname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrderView', params: { orderId: order.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrderEdit', params: { orderId: order.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(order)"
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
          id="azimuteErpSpringVueMonolith04App.order.delete.question"
          data-cy="orderDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-order-heading" v-text="t$('azimuteErpSpringVueMonolith04App.order.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-order"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOrder()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="orders && orders.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./order.component.ts"></script>
