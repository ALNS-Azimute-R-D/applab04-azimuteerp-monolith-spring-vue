<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="commonLocality">
        <h2 class="jh-entity-heading" data-cy="commonLocalityDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.detail.title')"></span> {{ commonLocality.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.acronym')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.name')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.description')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.streetAddress')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.streetAddress }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.houseNumber')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.houseNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.locationName')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.locationName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.postalCode')"></span>
          </dt>
          <dd>
            <span>{{ commonLocality.postalCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.geoPolygonArea')"></span>
          </dt>
          <dd>
            <div v-if="commonLocality.geoPolygonArea">
              <a
                v-on:click="openFile(commonLocality.geoPolygonAreaContentType, commonLocality.geoPolygonArea)"
                v-text="t$('entity.action.open')"
              ></a>
              {{ commonLocality.geoPolygonAreaContentType }}, {{ byteSize(commonLocality.geoPolygonArea) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.district')"></span>
          </dt>
          <dd>
            <div v-if="commonLocality.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: commonLocality.district.id } }">{{
                commonLocality.district.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="commonLocality.id"
          :to="{ name: 'CommonLocalityEdit', params: { commonLocalityId: commonLocality.id } }"
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

<script lang="ts" src="./common-locality-details.component.ts"></script>
