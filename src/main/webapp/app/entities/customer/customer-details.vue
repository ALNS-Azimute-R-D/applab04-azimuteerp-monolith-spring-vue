<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="customer">
        <h2 class="jh-entity-heading" data-cy="customerDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.detail.title')"></span> {{ customer.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerBusinessCode')"></span>
          </dt>
          <dd>
            <span>{{ customer.customerBusinessCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.fullname')"></span>
          </dt>
          <dd>
            <span>{{ customer.fullname }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ customer.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.CustomerStatusEnum.' + customer.customerStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + customer.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.buyerPerson')"></span>
          </dt>
          <dd>
            <div v-if="customer.buyerPerson">
              <router-link :to="{ name: 'PersonView', params: { personId: customer.buyerPerson.id } }">{{
                customer.buyerPerson.lastname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerType')"></span>
          </dt>
          <dd>
            <div v-if="customer.customerType">
              <router-link :to="{ name: 'CustomerTypeView', params: { customerTypeId: customer.customerType.id } }">{{
                customer.customerType.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.district')"></span>
          </dt>
          <dd>
            <div v-if="customer.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: customer.district.id } }">{{
                customer.district.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="customer.id" :to="{ name: 'CustomerEdit', params: { customerId: customer.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./customer-details.component.ts"></script>
