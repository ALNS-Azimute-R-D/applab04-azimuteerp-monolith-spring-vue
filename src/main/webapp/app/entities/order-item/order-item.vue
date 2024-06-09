<template>
  <div>
    <h2 id="page-heading" data-cy="OrderItemHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.home.title')" id="order-item-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OrderItemCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-order-item"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && orderItems && orderItems.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="orderItems && orderItems.length > 0">
      <table class="table table-striped" aria-describedby="orderItems">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('quantity')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.quantity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantity'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalPrice')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.totalPrice')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalPrice'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('article.customName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.article')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'article.customName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('order.businessCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.order')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'order.businessCode'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="orderItem in orderItems" :key="orderItem.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrderItemView', params: { orderItemId: orderItem.id } }">{{ orderItem.id }}</router-link>
            </td>
            <td>{{ orderItem.quantity }}</td>
            <td>{{ orderItem.totalPrice }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.OrderItemStatusEnum.' + orderItem.status)"></td>
            <td>
              <div v-if="orderItem.article">
                <router-link :to="{ name: 'ArticleView', params: { articleId: orderItem.article.id } }">{{
                  orderItem.article.customName
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="orderItem.order">
                <router-link :to="{ name: 'OrderView', params: { orderId: orderItem.order.id } }">{{
                  orderItem.order.businessCode
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrderItemView', params: { orderItemId: orderItem.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrderItemEdit', params: { orderItemId: orderItem.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(orderItem)"
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
          id="azimuteErpSpringVueMonolith04App.orderItem.delete.question"
          data-cy="orderItemDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-orderItem-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-orderItem"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOrderItem()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="orderItems && orderItems.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./order-item.component.ts"></script>
