<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="paymentGateway">
        <h2 class="jh-entity-heading" data-cy="paymentGatewayDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.detail.title')"></span> {{ paymentGateway.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.aliasCode')"></span>
          </dt>
          <dd>
            <span>{{ paymentGateway.aliasCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.description')"></span>
          </dt>
          <dd>
            <span>{{ paymentGateway.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.businessHandlerClazz')"></span>
          </dt>
          <dd>
            <span>{{ paymentGateway.businessHandlerClazz }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + paymentGateway.activationStatus)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="paymentGateway.id"
          :to="{ name: 'PaymentGatewayEdit', params: { paymentGatewayId: paymentGateway.id } }"
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

<script lang="ts" src="./payment-gateway-details.component.ts"></script>
