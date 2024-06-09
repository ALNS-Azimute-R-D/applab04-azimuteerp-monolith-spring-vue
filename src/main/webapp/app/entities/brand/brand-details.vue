<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="brand">
        <h2 class="jh-entity-heading" data-cy="brandDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.brand.detail.title')"></span> {{ brand.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.brand.acronym')"></span>
          </dt>
          <dd>
            <span>{{ brand.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.brand.name')"></span>
          </dt>
          <dd>
            <span>{{ brand.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.brand.description')"></span>
          </dt>
          <dd>
            <span>{{ brand.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.brand.logoBrand')"></span>
          </dt>
          <dd>
            <div v-if="brand.logoBrand">
              <a v-on:click="openFile(brand.logoBrandContentType, brand.logoBrand)">
                <img v-bind:src="'data:' + brand.logoBrandContentType + ';base64,' + brand.logoBrand" style="max-width: 100%" alt="brand" />
              </a>
              {{ brand.logoBrandContentType }}, {{ byteSize(brand.logoBrand) }}
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="brand.id" :to="{ name: 'BrandEdit', params: { brandId: brand.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./brand-details.component.ts"></script>
