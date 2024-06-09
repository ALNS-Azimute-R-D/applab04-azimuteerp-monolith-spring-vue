<template>
  <div>
    <h2 id="page-heading" data-cy="PaymentHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.home.title')" id="payment-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PaymentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && payments && payments.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="payments && payments.length > 0">
      <table class="table table-striped" aria-describedby="payments">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('installmentNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.installmentNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'installmentNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('paymentDueDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentDueDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'paymentDueDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('paymentPaidDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentPaidDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'paymentPaidDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('paymentAmount')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentAmount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'paymentAmount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfPayment')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.typeOfPayment')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfPayment'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('statusPayment')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.statusPayment')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'statusPayment'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('paymentGateway.aliasCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentGateway')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'paymentGateway.aliasCode'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="payment in payments" :key="payment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PaymentView', params: { paymentId: payment.id } }">{{ payment.id }}</router-link>
            </td>
            <td>{{ payment.installmentNumber }}</td>
            <td>{{ formatDateShort(payment.paymentDueDate) || '' }}</td>
            <td>{{ formatDateShort(payment.paymentPaidDate) || '' }}</td>
            <td>{{ payment.paymentAmount }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.PaymentTypeEnum.' + payment.typeOfPayment)"></td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.PaymentStatusEnum.' + payment.statusPayment)"></td>
            <td>{{ payment.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + payment.activationStatus)"></td>
            <td>
              <div v-if="payment.paymentGateway">
                <router-link :to="{ name: 'PaymentGatewayView', params: { paymentGatewayId: payment.paymentGateway.id } }">{{
                  payment.paymentGateway.aliasCode
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PaymentView', params: { paymentId: payment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PaymentEdit', params: { paymentId: payment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(payment)"
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
          id="azimuteErpSpringVueMonolith04App.payment.delete.question"
          data-cy="paymentDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-payment-heading" v-text="t$('azimuteErpSpringVueMonolith04App.payment.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-payment"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePayment()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="payments && payments.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./payment.component.ts"></script>
