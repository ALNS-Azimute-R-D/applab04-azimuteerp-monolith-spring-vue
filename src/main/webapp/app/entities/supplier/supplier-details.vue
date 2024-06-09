<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="supplier">
        <h2 class="jh-entity-heading" data-cy="supplierDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.detail.title')"></span> {{ supplier.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.acronym')"></span>
          </dt>
          <dd>
            <span>{{ supplier.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.companyName')"></span>
          </dt>
          <dd>
            <span>{{ supplier.companyName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.streetAddress')"></span>
          </dt>
          <dd>
            <span>{{ supplier.streetAddress }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.houseNumber')"></span>
          </dt>
          <dd>
            <span>{{ supplier.houseNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.locationName')"></span>
          </dt>
          <dd>
            <span>{{ supplier.locationName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.city')"></span>
          </dt>
          <dd>
            <span>{{ supplier.city }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.stateProvince')"></span>
          </dt>
          <dd>
            <span>{{ supplier.stateProvince }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.zipPostalCode')"></span>
          </dt>
          <dd>
            <span>{{ supplier.zipPostalCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.countryRegion')"></span>
          </dt>
          <dd>
            <span>{{ supplier.countryRegion }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.pointLocation')"></span>
          </dt>
          <dd>
            <div v-if="supplier.pointLocation">
              <a v-on:click="openFile(supplier.pointLocationContentType, supplier.pointLocation)" v-text="t$('entity.action.open')"></a>
              {{ supplier.pointLocationContentType }}, {{ byteSize(supplier.pointLocation) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.mainEmail')"></span>
          </dt>
          <dd>
            <span>{{ supplier.mainEmail }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.phoneNumber1')"></span>
          </dt>
          <dd>
            <span>{{ supplier.phoneNumber1 }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.phoneNumber2')"></span>
          </dt>
          <dd>
            <span>{{ supplier.phoneNumber2 }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ supplier.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + supplier.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.representativePerson')"></span>
          </dt>
          <dd>
            <div v-if="supplier.representativePerson">
              <router-link :to="{ name: 'PersonView', params: { personId: supplier.representativePerson.id } }">{{
                supplier.representativePerson.lastname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.toProduct')"></span>
          </dt>
          <dd>
            <span v-for="(toProduct, i) in supplier.toProducts" :key="toProduct.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ProductView', params: { productId: toProduct.id } }">{{ toProduct.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="supplier.id" :to="{ name: 'SupplierEdit', params: { supplierId: supplier.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./supplier-details.component.ts"></script>
