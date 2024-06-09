<template>
  <div>
    <h2 id="page-heading" data-cy="StockLevelHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.home.title')" id="stock-level-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'StockLevelCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-stock-level"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stockLevels && stockLevels.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="stockLevels && stockLevels.length > 0">
      <table class="table table-striped" aria-describedby="stockLevels">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastModifiedDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.lastModifiedDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastModifiedDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('remainingQuantity')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.remainingQuantity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'remainingQuantity'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('commonAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.commonAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'commonAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('warehouse.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.warehouse')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'warehouse.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('product.productName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.product')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'product.productName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="stockLevel in stockLevels" :key="stockLevel.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StockLevelView', params: { stockLevelId: stockLevel.id } }">{{ stockLevel.id }}</router-link>
            </td>
            <td>{{ formatDateShort(stockLevel.lastModifiedDate) || '' }}</td>
            <td>{{ stockLevel.remainingQuantity }}</td>
            <td>{{ stockLevel.commonAttributesDetailsJSON }}</td>
            <td>
              <div v-if="stockLevel.warehouse">
                <router-link :to="{ name: 'WarehouseView', params: { warehouseId: stockLevel.warehouse.id } }">{{
                  stockLevel.warehouse.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="stockLevel.product">
                <router-link :to="{ name: 'ProductView', params: { productId: stockLevel.product.id } }">{{
                  stockLevel.product.productName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StockLevelView', params: { stockLevelId: stockLevel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StockLevelEdit', params: { stockLevelId: stockLevel.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(stockLevel)"
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
          id="azimuteErpSpringVueMonolith04App.stockLevel.delete.question"
          data-cy="stockLevelDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-stockLevel-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-stockLevel"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeStockLevel()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="stockLevels && stockLevels.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./stock-level.component.ts"></script>
