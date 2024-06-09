<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="stockLevel">
        <h2 class="jh-entity-heading" data-cy="stockLevelDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.detail.title')"></span> {{ stockLevel.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.lastModifiedDate')"></span>
          </dt>
          <dd>
            <span v-if="stockLevel.lastModifiedDate">{{ formatDateLong(stockLevel.lastModifiedDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.remainingQuantity')"></span>
          </dt>
          <dd>
            <span>{{ stockLevel.remainingQuantity }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.commonAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ stockLevel.commonAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.warehouse')"></span>
          </dt>
          <dd>
            <div v-if="stockLevel.warehouse">
              <router-link :to="{ name: 'WarehouseView', params: { warehouseId: stockLevel.warehouse.id } }">{{
                stockLevel.warehouse.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.product')"></span>
          </dt>
          <dd>
            <div v-if="stockLevel.product">
              <router-link :to="{ name: 'ProductView', params: { productId: stockLevel.product.id } }">{{
                stockLevel.product.productName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="stockLevel.id"
          :to="{ name: 'StockLevelEdit', params: { stockLevelId: stockLevel.id } }"
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

<script lang="ts" src="./stock-level-details.component.ts"></script>
