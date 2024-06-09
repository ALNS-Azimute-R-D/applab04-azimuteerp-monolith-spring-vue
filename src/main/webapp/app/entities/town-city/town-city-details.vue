<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="townCity">
        <h2 class="jh-entity-heading" data-cy="townCityDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.detail.title')"></span> {{ townCity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.acronym')"></span>
          </dt>
          <dd>
            <span>{{ townCity.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.name')"></span>
          </dt>
          <dd>
            <span>{{ townCity.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.geoPolygonArea')"></span>
          </dt>
          <dd>
            <div v-if="townCity.geoPolygonArea">
              <a v-on:click="openFile(townCity.geoPolygonAreaContentType, townCity.geoPolygonArea)" v-text="t$('entity.action.open')"></a>
              {{ townCity.geoPolygonAreaContentType }}, {{ byteSize(townCity.geoPolygonArea) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.province')"></span>
          </dt>
          <dd>
            <div v-if="townCity.province">
              <router-link :to="{ name: 'ProvinceView', params: { provinceId: townCity.province.id } }">{{
                townCity.province.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="townCity.id" :to="{ name: 'TownCityEdit', params: { townCityId: townCity.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./town-city-details.component.ts"></script>
