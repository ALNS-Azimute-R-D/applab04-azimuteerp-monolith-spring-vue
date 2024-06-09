<template>
  <div>
    <h2 id="page-heading" data-cy="SupplierHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.home.title')" id="supplier-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'SupplierCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-supplier"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && suppliers && suppliers.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="suppliers && suppliers.length > 0">
      <table class="table table-striped" aria-describedby="suppliers">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('companyName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.companyName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'companyName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('streetAddress')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.streetAddress')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'streetAddress'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('houseNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.houseNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'houseNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('locationName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.locationName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locationName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('city')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.city')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'city'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stateProvince')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.stateProvince')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stateProvince'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('zipPostalCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.zipPostalCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'zipPostalCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('countryRegion')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.countryRegion')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'countryRegion'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pointLocation')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.pointLocation')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pointLocation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainEmail')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.mainEmail')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainEmail'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber1')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.phoneNumber1')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber1'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber2')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.phoneNumber2')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber2'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('representativePerson.lastname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.supplier.representativePerson')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'representativePerson.lastname'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="supplier in suppliers" :key="supplier.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SupplierView', params: { supplierId: supplier.id } }">{{ supplier.id }}</router-link>
            </td>
            <td>{{ supplier.acronym }}</td>
            <td>{{ supplier.companyName }}</td>
            <td>{{ supplier.streetAddress }}</td>
            <td>{{ supplier.houseNumber }}</td>
            <td>{{ supplier.locationName }}</td>
            <td>{{ supplier.city }}</td>
            <td>{{ supplier.stateProvince }}</td>
            <td>{{ supplier.zipPostalCode }}</td>
            <td>{{ supplier.countryRegion }}</td>
            <td>
              <a
                v-if="supplier.pointLocation"
                v-on:click="openFile(supplier.pointLocationContentType, supplier.pointLocation)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="supplier.pointLocation">{{ supplier.pointLocationContentType }}, {{ byteSize(supplier.pointLocation) }}</span>
            </td>
            <td>{{ supplier.mainEmail }}</td>
            <td>{{ supplier.phoneNumber1 }}</td>
            <td>{{ supplier.phoneNumber2 }}</td>
            <td>{{ supplier.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + supplier.activationStatus)"></td>
            <td>
              <div v-if="supplier.representativePerson">
                <router-link :to="{ name: 'PersonView', params: { personId: supplier.representativePerson.id } }">{{
                  supplier.representativePerson.lastname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SupplierView', params: { supplierId: supplier.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SupplierEdit', params: { supplierId: supplier.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(supplier)"
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
          id="azimuteErpSpringVueMonolith04App.supplier.delete.question"
          data-cy="supplierDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-supplier-heading" v-text="t$('azimuteErpSpringVueMonolith04App.supplier.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-supplier"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeSupplier()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="suppliers && suppliers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./supplier.component.ts"></script>
