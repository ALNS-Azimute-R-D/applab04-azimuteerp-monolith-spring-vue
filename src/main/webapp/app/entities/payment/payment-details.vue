<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="payment">
        <h2 class="jh-entity-heading" data-cy="paymentDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.detail.title')"></span> {{ payment.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.installmentNumber')"></span>
          </dt>
          <dd>
            <span>{{ payment.installmentNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentDueDate')"></span>
          </dt>
          <dd>
            <span v-if="payment.paymentDueDate">{{ formatDateLong(payment.paymentDueDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentPaidDate')"></span>
          </dt>
          <dd>
            <span v-if="payment.paymentPaidDate">{{ formatDateLong(payment.paymentPaidDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentAmount')"></span>
          </dt>
          <dd>
            <span>{{ payment.paymentAmount }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.typeOfPayment')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.PaymentTypeEnum.' + payment.typeOfPayment)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.statusPayment')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.PaymentStatusEnum.' + payment.statusPayment)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ payment.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + payment.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentGateway')"></span>
          </dt>
          <dd>
            <div v-if="payment.paymentGateway">
              <router-link :to="{ name: 'PaymentGatewayView', params: { paymentGatewayId: payment.paymentGateway.id } }">{{
                payment.paymentGateway.aliasCode
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="payment.id" :to="{ name: 'PaymentEdit', params: { paymentId: payment.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./payment-details.component.ts"></script>
