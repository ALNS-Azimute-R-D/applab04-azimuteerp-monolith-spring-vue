<template>
  <div>
    <h2 id="page-heading" data-cy="DistrictHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.district.home.title')" id="district-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.district.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'DistrictCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-district"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.district.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && districts && districts.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.district.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="districts && districts.length > 0">
      <table class="table table-striped" aria-describedby="districts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.district.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.district.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('geoPolygonArea')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.district.geoPolygonArea')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'geoPolygonArea'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('townCity.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.district.townCity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'townCity.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="district in districts" :key="district.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DistrictView', params: { districtId: district.id } }">{{ district.id }}</router-link>
            </td>
            <td>{{ district.acronym }}</td>
            <td>{{ district.name }}</td>
            <td>
              <a
                v-if="district.geoPolygonArea"
                v-on:click="openFile(district.geoPolygonAreaContentType, district.geoPolygonArea)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="district.geoPolygonArea">{{ district.geoPolygonAreaContentType }}, {{ byteSize(district.geoPolygonArea) }}</span>
            </td>
            <td>
              <div v-if="district.townCity">
                <router-link :to="{ name: 'TownCityView', params: { townCityId: district.townCity.id } }">{{
                  district.townCity.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DistrictView', params: { districtId: district.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DistrictEdit', params: { districtId: district.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(district)"
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
          id="azimuteErpSpringVueMonolith04App.district.delete.question"
          data-cy="districtDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-district-heading" v-text="t$('azimuteErpSpringVueMonolith04App.district.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-district"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeDistrict()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="districts && districts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./district.component.ts"></script>
