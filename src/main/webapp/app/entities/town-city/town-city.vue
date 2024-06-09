<template>
  <div>
    <h2 id="page-heading" data-cy="TownCityHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.home.title')" id="town-city-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TownCityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-town-city"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && townCities && townCities.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="townCities && townCities.length > 0">
      <table class="table table-striped" aria-describedby="townCities">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('geoPolygonArea')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.geoPolygonArea')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'geoPolygonArea'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('province.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.townCity.province')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'province.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="townCity in townCities" :key="townCity.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TownCityView', params: { townCityId: townCity.id } }">{{ townCity.id }}</router-link>
            </td>
            <td>{{ townCity.acronym }}</td>
            <td>{{ townCity.name }}</td>
            <td>
              <a
                v-if="townCity.geoPolygonArea"
                v-on:click="openFile(townCity.geoPolygonAreaContentType, townCity.geoPolygonArea)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="townCity.geoPolygonArea">{{ townCity.geoPolygonAreaContentType }}, {{ byteSize(townCity.geoPolygonArea) }}</span>
            </td>
            <td>
              <div v-if="townCity.province">
                <router-link :to="{ name: 'ProvinceView', params: { provinceId: townCity.province.id } }">{{
                  townCity.province.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TownCityView', params: { townCityId: townCity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TownCityEdit', params: { townCityId: townCity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(townCity)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="azimuteErpSpringVueMonolith04App.townCity.delete.question"
          data-cy="townCityDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-townCity-heading" v-text="t$('azimuteErpSpringVueMonolith04App.townCity.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-townCity"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTownCity()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="townCities && townCities.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./town-city.component.ts"></script>
