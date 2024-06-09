<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="warehouse">
        <h2 class="jh-entity-heading" data-cy="warehouseDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.detail.title')"></span> {{ warehouse.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.acronym')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.name')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.description')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.streetAddress')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.streetAddress }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.houseNumber')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.houseNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.locationName')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.locationName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.postalCode')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.postalCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.pointLocation')"></span>
          </dt>
          <dd>
            <div v-if="warehouse.pointLocation">
              <a v-on:click="openFile(warehouse.pointLocationContentType, warehouse.pointLocation)" v-text="t$('entity.action.open')"></a>
              {{ warehouse.pointLocationContentType }}, {{ byteSize(warehouse.pointLocation) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.mainEmail')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.mainEmail }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.landPhoneNumber')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.landPhoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.mobilePhoneNumber')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.mobilePhoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.faxNumber')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.faxNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ warehouse.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + warehouse.activationStatus)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="warehouse.id"
          :to="{ name: 'WarehouseEdit', params: { warehouseId: warehouse.id } }"
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

<script lang="ts" src="./warehouse-details.component.ts"></script>
