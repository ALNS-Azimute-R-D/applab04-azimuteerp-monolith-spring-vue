<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="customerType">
        <h2 class="jh-entity-heading" data-cy="customerTypeDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.detail.title')"></span> {{ customerType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.name')"></span>
          </dt>
          <dd>
            <span>{{ customerType.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.description')"></span>
          </dt>
          <dd>
            <span>{{ customerType.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.businessHandlerClazz')"></span>
          </dt>
          <dd>
            <span>{{ customerType.businessHandlerClazz }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="customerType.id"
          :to="{ name: 'CustomerTypeEdit', params: { customerTypeId: customerType.id } }"
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

<script lang="ts" src="./customer-type-details.component.ts"></script>
