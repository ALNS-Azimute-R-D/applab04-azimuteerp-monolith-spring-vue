<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="district">
        <h2 class="jh-entity-heading" data-cy="districtDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.district.detail.title')"></span> {{ district.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.district.acronym')"></span>
          </dt>
          <dd>
            <span>{{ district.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.district.name')"></span>
          </dt>
          <dd>
            <span>{{ district.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.district.geoPolygonArea')"></span>
          </dt>
          <dd>
            <div v-if="district.geoPolygonArea">
              <a v-on:click="openFile(district.geoPolygonAreaContentType, district.geoPolygonArea)" v-text="t$('entity.action.open')"></a>
              {{ district.geoPolygonAreaContentType }}, {{ byteSize(district.geoPolygonArea) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.district.townCity')"></span>
          </dt>
          <dd>
            <div v-if="district.townCity">
              <router-link :to="{ name: 'TownCityView', params: { townCityId: district.townCity.id } }">{{
                district.townCity.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="district.id" :to="{ name: 'DistrictEdit', params: { districtId: district.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./district-details.component.ts"></script>
