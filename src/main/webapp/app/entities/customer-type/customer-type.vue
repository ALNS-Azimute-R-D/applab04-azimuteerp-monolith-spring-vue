<template>
  <div>
    <h2 id="page-heading" data-cy="CustomerTypeHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.home.title')" id="customer-type-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CustomerTypeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-customer-type"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && customerTypes && customerTypes.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="customerTypes && customerTypes.length > 0">
      <table class="table table-striped" aria-describedby="customerTypes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessHandlerClazz')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customerType.businessHandlerClazz')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessHandlerClazz'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="customerType in customerTypes" :key="customerType.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CustomerTypeView', params: { customerTypeId: customerType.id } }">{{
                customerType.id
              }}</router-link>
            </td>
            <td>{{ customerType.name }}</td>
            <td>{{ customerType.description }}</td>
            <td>{{ customerType.businessHandlerClazz }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CustomerTypeView', params: { customerTypeId: customerType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CustomerTypeEdit', params: { customerTypeId: customerType.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(customerType)"
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
          id="azimuteErpSpringVueMonolith04App.customerType.delete.question"
          data-cy="customerTypeDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-customerType-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.customerType.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-customerType"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCustomerType()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="customerTypes && customerTypes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./customer-type.component.ts"></script>
