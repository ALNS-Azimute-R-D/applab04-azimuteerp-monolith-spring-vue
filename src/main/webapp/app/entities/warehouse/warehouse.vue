<template>
  <div>
    <h2 id="page-heading" data-cy="WarehouseHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.home.title')" id="warehouse-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'WarehouseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-warehouse"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && warehouses && warehouses.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="warehouses && warehouses.length > 0">
      <table class="table table-striped" aria-describedby="warehouses">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('streetAddress')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.streetAddress')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'streetAddress'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('houseNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.houseNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'houseNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('locationName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.locationName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locationName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('postalCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.postalCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postalCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pointLocation')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.pointLocation')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pointLocation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainEmail')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.mainEmail')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainEmail'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('landPhoneNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.landPhoneNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'landPhoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mobilePhoneNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.mobilePhoneNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mobilePhoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('faxNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.faxNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'faxNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="warehouse in warehouses" :key="warehouse.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'WarehouseView', params: { warehouseId: warehouse.id } }">{{ warehouse.id }}</router-link>
            </td>
            <td>{{ warehouse.acronym }}</td>
            <td>{{ warehouse.name }}</td>
            <td>{{ warehouse.description }}</td>
            <td>{{ warehouse.streetAddress }}</td>
            <td>{{ warehouse.houseNumber }}</td>
            <td>{{ warehouse.locationName }}</td>
            <td>{{ warehouse.postalCode }}</td>
            <td>
              <a
                v-if="warehouse.pointLocation"
                v-on:click="openFile(warehouse.pointLocationContentType, warehouse.pointLocation)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="warehouse.pointLocation">{{ warehouse.pointLocationContentType }}, {{ byteSize(warehouse.pointLocation) }}</span>
            </td>
            <td>{{ warehouse.mainEmail }}</td>
            <td>{{ warehouse.landPhoneNumber }}</td>
            <td>{{ warehouse.mobilePhoneNumber }}</td>
            <td>{{ warehouse.faxNumber }}</td>
            <td>{{ warehouse.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + warehouse.activationStatus)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'WarehouseView', params: { warehouseId: warehouse.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'WarehouseEdit', params: { warehouseId: warehouse.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(warehouse)"
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
          id="azimuteErpSpringVueMonolith04App.warehouse.delete.question"
          data-cy="warehouseDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-warehouse-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.warehouse.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-warehouse"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeWarehouse()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="warehouses && warehouses.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./warehouse.component.ts"></script>
