<template>
  <div>
    <h2 id="page-heading" data-cy="InvoiceHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.home.title')" id="invoice-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'InvoiceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-invoice"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && invoices && invoices.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="invoices && invoices.length > 0">
      <table class="table table-striped" aria-describedby="invoices">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.businessCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invoiceDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.invoiceDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invoiceDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dueDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.dueDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dueDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taxValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.taxValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taxValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('shippingValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.shippingValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shippingValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amountDueValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.amountDueValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amountDueValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfInstallmentsOriginal')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.numberOfInstallmentsOriginal')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'numberOfInstallmentsOriginal'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfInstallmentsPaid')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.numberOfInstallmentsPaid')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'numberOfInstallmentsPaid'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amountPaidValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.amountPaidValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amountPaidValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('preferrablePaymentGateway.aliasCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.preferrablePaymentGateway')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'preferrablePaymentGateway.aliasCode'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="invoice in invoices" :key="invoice.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'InvoiceView', params: { invoiceId: invoice.id } }">{{ invoice.id }}</router-link>
            </td>
            <td>{{ invoice.businessCode }}</td>
            <td>{{ formatDateShort(invoice.invoiceDate) || '' }}</td>
            <td>{{ formatDateShort(invoice.dueDate) || '' }}</td>
            <td>{{ invoice.description }}</td>
            <td>{{ invoice.taxValue }}</td>
            <td>{{ invoice.shippingValue }}</td>
            <td>{{ invoice.amountDueValue }}</td>
            <td>{{ invoice.numberOfInstallmentsOriginal }}</td>
            <td>{{ invoice.numberOfInstallmentsPaid }}</td>
            <td>{{ invoice.amountPaidValue }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.InvoiceStatusEnum.' + invoice.status)"></td>
            <td>{{ invoice.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + invoice.activationStatus)"></td>
            <td>
              <div v-if="invoice.preferrablePaymentGateway">
                <router-link :to="{ name: 'PaymentGatewayView', params: { paymentGatewayId: invoice.preferrablePaymentGateway.id } }">{{
                  invoice.preferrablePaymentGateway.aliasCode
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'InvoiceView', params: { invoiceId: invoice.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'InvoiceEdit', params: { invoiceId: invoice.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(invoice)"
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
          id="azimuteErpSpringVueMonolith04App.invoice.delete.question"
          data-cy="invoiceDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-invoice-heading" v-text="t$('azimuteErpSpringVueMonolith04App.invoice.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-invoice"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeInvoice()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="invoices && invoices.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./invoice.component.ts"></script>
