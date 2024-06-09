<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="inventoryTransaction">
        <h2 class="jh-entity-heading" data-cy="inventoryTransactionDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.detail.title')"></span> {{ inventoryTransaction.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.invoiceId')"></span>
          </dt>
          <dd>
            <span>{{ inventoryTransaction.invoiceId }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionCreatedDate')"></span>
          </dt>
          <dd>
            <span v-if="inventoryTransaction.transactionCreatedDate">{{
              formatDateLong(inventoryTransaction.transactionCreatedDate)
            }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionModifiedDate')"></span>
          </dt>
          <dd>
            <span v-if="inventoryTransaction.transactionModifiedDate">{{
              formatDateLong(inventoryTransaction.transactionModifiedDate)
            }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.quantity')"></span>
          </dt>
          <dd>
            <span>{{ inventoryTransaction.quantity }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionComments')"></span>
          </dt>
          <dd>
            <span>{{ inventoryTransaction.transactionComments }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + inventoryTransaction.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.supplier')"></span>
          </dt>
          <dd>
            <div v-if="inventoryTransaction.supplier">
              <router-link :to="{ name: 'SupplierView', params: { supplierId: inventoryTransaction.supplier.id } }">{{
                inventoryTransaction.supplier.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.product')"></span>
          </dt>
          <dd>
            <div v-if="inventoryTransaction.product">
              <router-link :to="{ name: 'ProductView', params: { productId: inventoryTransaction.product.id } }">{{
                inventoryTransaction.product.productName
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.warehouse')"></span>
          </dt>
          <dd>
            <div v-if="inventoryTransaction.warehouse">
              <router-link :to="{ name: 'WarehouseView', params: { warehouseId: inventoryTransaction.warehouse.id } }">{{
                inventoryTransaction.warehouse.acronym
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="inventoryTransaction.id"
          :to="{ name: 'InventoryTransactionEdit', params: { inventoryTransactionId: inventoryTransaction.id } }"
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

<script lang="ts" src="./inventory-transaction-details.component.ts"></script>
