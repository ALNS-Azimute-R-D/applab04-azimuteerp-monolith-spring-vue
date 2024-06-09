<template>
  <div>
    <h2 id="page-heading" data-cy="PaymentGatewayHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.home.title')" id="payment-gateway-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PaymentGatewayCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payment-gateway"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && paymentGateways && paymentGateways.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="paymentGateways && paymentGateways.length > 0">
      <table class="table table-striped" aria-describedby="paymentGateways">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('aliasCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.aliasCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aliasCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessHandlerClazz')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.businessHandlerClazz')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessHandlerClazz'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="paymentGateway in paymentGateways" :key="paymentGateway.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PaymentGatewayView', params: { paymentGatewayId: paymentGateway.id } }">{{
                paymentGateway.id
              }}</router-link>
            </td>
            <td>{{ paymentGateway.aliasCode }}</td>
            <td>{{ paymentGateway.description }}</td>
            <td>{{ paymentGateway.businessHandlerClazz }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + paymentGateway.activationStatus)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PaymentGatewayView', params: { paymentGatewayId: paymentGateway.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PaymentGatewayEdit', params: { paymentGatewayId: paymentGateway.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(paymentGateway)"
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
          id="azimuteErpSpringVueMonolith04App.paymentGateway.delete.question"
          data-cy="paymentGatewayDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-paymentGateway-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-paymentGateway"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePaymentGateway()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="paymentGateways && paymentGateways.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./payment-gateway.component.ts"></script>
