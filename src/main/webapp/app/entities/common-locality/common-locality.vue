<template>
  <div>
    <h2 id="page-heading" data-cy="CommonLocalityHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.home.title')" id="common-locality-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CommonLocalityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-common-locality"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && commonLocalities && commonLocalities.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="commonLocalities && commonLocalities.length > 0">
      <table class="table table-striped" aria-describedby="commonLocalities">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('streetAddress')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.streetAddress')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'streetAddress'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('houseNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.houseNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'houseNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('locationName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.locationName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locationName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('postalCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.postalCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postalCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('geoPolygonArea')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.geoPolygonArea')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'geoPolygonArea'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.district')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="commonLocality in commonLocalities" :key="commonLocality.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CommonLocalityView', params: { commonLocalityId: commonLocality.id } }">{{
                commonLocality.id
              }}</router-link>
            </td>
            <td>{{ commonLocality.acronym }}</td>
            <td>{{ commonLocality.name }}</td>
            <td>{{ commonLocality.description }}</td>
            <td>{{ commonLocality.streetAddress }}</td>
            <td>{{ commonLocality.houseNumber }}</td>
            <td>{{ commonLocality.locationName }}</td>
            <td>{{ commonLocality.postalCode }}</td>
            <td>
              <a
                v-if="commonLocality.geoPolygonArea"
                v-on:click="openFile(commonLocality.geoPolygonAreaContentType, commonLocality.geoPolygonArea)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="commonLocality.geoPolygonArea"
                >{{ commonLocality.geoPolygonAreaContentType }}, {{ byteSize(commonLocality.geoPolygonArea) }}</span
              >
            </td>
            <td>
              <div v-if="commonLocality.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: commonLocality.district.id } }">{{
                  commonLocality.district.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CommonLocalityView', params: { commonLocalityId: commonLocality.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'CommonLocalityEdit', params: { commonLocalityId: commonLocality.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(commonLocality)"
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
          id="azimuteErpSpringVueMonolith04App.commonLocality.delete.question"
          data-cy="commonLocalityDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-commonLocality-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.commonLocality.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-commonLocality"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCommonLocality()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="commonLocalities && commonLocalities.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./common-locality.component.ts"></script>
