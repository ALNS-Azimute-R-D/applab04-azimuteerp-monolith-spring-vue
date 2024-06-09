<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="province">
        <h2 class="jh-entity-heading" data-cy="provinceDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.province.detail.title')"></span> {{ province.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.province.acronym')"></span>
          </dt>
          <dd>
            <span>{{ province.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.province.name')"></span>
          </dt>
          <dd>
            <span>{{ province.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.province.geoPolygonArea')"></span>
          </dt>
          <dd>
            <div v-if="province.geoPolygonArea">
              <a v-on:click="openFile(province.geoPolygonAreaContentType, province.geoPolygonArea)" v-text="t$('entity.action.open')"></a>
              {{ province.geoPolygonAreaContentType }}, {{ byteSize(province.geoPolygonArea) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.province.country')"></span>
          </dt>
          <dd>
            <div v-if="province.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: province.country.id } }">{{
                province.country.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="province.id" :to="{ name: 'ProvinceEdit', params: { provinceId: province.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./province-details.component.ts"></script>
