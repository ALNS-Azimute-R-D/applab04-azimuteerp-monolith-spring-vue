<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="order">
        <h2 class="jh-entity-heading" data-cy="orderDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.order.detail.title')"></span> {{ order.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.businessCode')"></span>
          </dt>
          <dd>
            <span>{{ order.businessCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.placedDate')"></span>
          </dt>
          <dd>
            <span v-if="order.placedDate">{{ formatDateLong(order.placedDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.totalTaxValue')"></span>
          </dt>
          <dd>
            <span>{{ order.totalTaxValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.totalDueValue')"></span>
          </dt>
          <dd>
            <span>{{ order.totalDueValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.OrderStatusEnum.' + order.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.estimatedDeliveryDate')"></span>
          </dt>
          <dd>
            <span v-if="order.estimatedDeliveryDate">{{ formatDateLong(order.estimatedDeliveryDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + order.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.invoice')"></span>
          </dt>
          <dd>
            <div v-if="order.invoice">
              <router-link :to="{ name: 'InvoiceView', params: { invoiceId: order.invoice.id } }">{{
                order.invoice.description
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.order.customer')"></span>
          </dt>
          <dd>
            <div v-if="order.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: order.customer.id } }">{{
                order.customer.fullname
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="order.id" :to="{ name: 'OrderEdit', params: { orderId: order.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./order-details.component.ts"></script>
