<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="orderItem">
        <h2 class="jh-entity-heading" data-cy="orderItemDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.detail.title')"></span> {{ orderItem.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.quantity')"></span>
          </dt>
          <dd>
            <span>{{ orderItem.quantity }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.totalPrice')"></span>
          </dt>
          <dd>
            <span>{{ orderItem.totalPrice }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.OrderItemStatusEnum.' + orderItem.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.article')"></span>
          </dt>
          <dd>
            <div v-if="orderItem.article">
              <router-link :to="{ name: 'ArticleView', params: { articleId: orderItem.article.id } }">{{
                orderItem.article.customName
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.order')"></span>
          </dt>
          <dd>
            <div v-if="orderItem.order">
              <router-link :to="{ name: 'OrderView', params: { orderId: orderItem.order.id } }">{{
                orderItem.order.businessCode
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="orderItem.id"
          :to="{ name: 'OrderItemEdit', params: { orderItemId: orderItem.id } }"
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

<script lang="ts" src="./order-item-details.component.ts"></script>
