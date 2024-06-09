<template>
  <div>
    <h2 id="page-heading" data-cy="InventoryTransactionHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.home.title')" id="inventory-transaction-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'InventoryTransactionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-inventory-transaction"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && inventoryTransactions && inventoryTransactions.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="inventoryTransactions && inventoryTransactions.length > 0">
      <table class="table table-striped" aria-describedby="inventoryTransactions">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invoiceId')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.invoiceId')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invoiceId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('transactionCreatedDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionCreatedDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'transactionCreatedDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('transactionModifiedDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionModifiedDate')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'transactionModifiedDate'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('quantity')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.quantity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantity'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('transactionComments')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionComments')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'transactionComments'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supplier.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.supplier')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supplier.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('product.productName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.product')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'product.productName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('warehouse.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.warehouse')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'warehouse.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="inventoryTransaction in inventoryTransactions" :key="inventoryTransaction.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'InventoryTransactionView', params: { inventoryTransactionId: inventoryTransaction.id } }">{{
                inventoryTransaction.id
              }}</router-link>
            </td>
            <td>{{ inventoryTransaction.invoiceId }}</td>
            <td>{{ formatDateShort(inventoryTransaction.transactionCreatedDate) || '' }}</td>
            <td>{{ formatDateShort(inventoryTransaction.transactionModifiedDate) || '' }}</td>
            <td>{{ inventoryTransaction.quantity }}</td>
            <td>{{ inventoryTransaction.transactionComments }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + inventoryTransaction.activationStatus)"></td>
            <td>
              <div v-if="inventoryTransaction.supplier">
                <router-link :to="{ name: 'SupplierView', params: { supplierId: inventoryTransaction.supplier.id } }">{{
                  inventoryTransaction.supplier.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="inventoryTransaction.product">
                <router-link :to="{ name: 'ProductView', params: { productId: inventoryTransaction.product.id } }">{{
                  inventoryTransaction.product.productName
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="inventoryTransaction.warehouse">
                <router-link :to="{ name: 'WarehouseView', params: { warehouseId: inventoryTransaction.warehouse.id } }">{{
                  inventoryTransaction.warehouse.acronym
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'InventoryTransactionView', params: { inventoryTransactionId: inventoryTransaction.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'InventoryTransactionEdit', params: { inventoryTransactionId: inventoryTransaction.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(inventoryTransaction)"
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
          id="azimuteErpSpringVueMonolith04App.inventoryTransaction.delete.question"
          data-cy="inventoryTransactionDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-inventoryTransaction-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-inventoryTransaction"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeInventoryTransaction()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="inventoryTransactions && inventoryTransactions.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./inventory-transaction.component.ts"></script>
