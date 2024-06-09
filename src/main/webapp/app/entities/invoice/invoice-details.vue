<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="invoice">
        <h2 class="jh-entity-heading" data-cy="invoiceDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.detail.title')"></span> {{ invoice.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.businessCode')"></span>
          </dt>
          <dd>
            <span>{{ invoice.businessCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.invoiceDate')"></span>
          </dt>
          <dd>
            <span v-if="invoice.invoiceDate">{{ formatDateLong(invoice.invoiceDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.dueDate')"></span>
          </dt>
          <dd>
            <span v-if="invoice.dueDate">{{ formatDateLong(invoice.dueDate) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.description')"></span>
          </dt>
          <dd>
            <span>{{ invoice.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.taxValue')"></span>
          </dt>
          <dd>
            <span>{{ invoice.taxValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.shippingValue')"></span>
          </dt>
          <dd>
            <span>{{ invoice.shippingValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.amountDueValue')"></span>
          </dt>
          <dd>
            <span>{{ invoice.amountDueValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.numberOfInstallmentsOriginal')"></span>
          </dt>
          <dd>
            <span>{{ invoice.numberOfInstallmentsOriginal }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.numberOfInstallmentsPaid')"></span>
          </dt>
          <dd>
            <span>{{ invoice.numberOfInstallmentsPaid }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.amountPaidValue')"></span>
          </dt>
          <dd>
            <span>{{ invoice.amountPaidValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.status')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.InvoiceStatusEnum.' + invoice.status)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ invoice.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + invoice.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.invoice.preferrablePaymentGateway')"></span>
          </dt>
          <dd>
            <div v-if="invoice.preferrablePaymentGateway">
              <router-link :to="{ name: 'PaymentGatewayView', params: { paymentGatewayId: invoice.preferrablePaymentGateway.id } }">{{
                invoice.preferrablePaymentGateway.aliasCode
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="invoice.id" :to="{ name: 'InvoiceEdit', params: { invoiceId: invoice.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./invoice-details.component.ts"></script>
