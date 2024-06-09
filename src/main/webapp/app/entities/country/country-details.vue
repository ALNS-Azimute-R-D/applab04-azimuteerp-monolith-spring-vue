<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="country">
        <h2 class="jh-entity-heading" data-cy="countryDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.country.detail.title')"></span> {{ country.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.country.acronym')"></span>
          </dt>
          <dd>
            <span>{{ country.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.country.name')"></span>
          </dt>
          <dd>
            <span>{{ country.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.country.continent')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ContinentEnum.' + country.continent)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.country.geoPolygonArea')"></span>
          </dt>
          <dd>
            <div v-if="country.geoPolygonArea">
              <a v-on:click="openFile(country.geoPolygonAreaContentType, country.geoPolygonArea)" v-text="t$('entity.action.open')"></a>
              {{ country.geoPolygonAreaContentType }}, {{ byteSize(country.geoPolygonArea) }}
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="country.id" :to="{ name: 'CountryEdit', params: { countryId: country.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./country-details.component.ts"></script>
