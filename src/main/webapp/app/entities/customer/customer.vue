<template>
  <div>
    <h2 id="page-heading" data-cy="CustomerHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.home.title')" id="customer-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CustomerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-customer"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && customers && customers.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="customers && customers.length > 0">
      <table class="table table-striped" aria-describedby="customers">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customerBusinessCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerBusinessCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customerBusinessCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.fullname')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customerStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customerStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('buyerPerson.lastname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.buyerPerson')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'buyerPerson.lastname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customerType.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customerType.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.customer.district')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="customer in customers" :key="customer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CustomerView', params: { customerId: customer.id } }">{{ customer.id }}</router-link>
            </td>
            <td>{{ customer.customerBusinessCode }}</td>
            <td>{{ customer.fullname }}</td>
            <td>{{ customer.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.CustomerStatusEnum.' + customer.customerStatus)"></td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + customer.activationStatus)"></td>
            <td>
              <div v-if="customer.buyerPerson">
                <router-link :to="{ name: 'PersonView', params: { personId: customer.buyerPerson.id } }">{{
                  customer.buyerPerson.lastname
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="customer.customerType">
                <router-link :to="{ name: 'CustomerTypeView', params: { customerTypeId: customer.customerType.id } }">{{
                  customer.customerType.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="customer.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: customer.district.id } }">{{
                  customer.district.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CustomerView', params: { customerId: customer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CustomerEdit', params: { customerId: customer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(customer)"
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
          id="azimuteErpSpringVueMonolith04App.customer.delete.question"
          data-cy="customerDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-customer-heading" v-text="t$('azimuteErpSpringVueMonolith04App.customer.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-customer"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCustomer()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="customers && customers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./customer.component.ts"></script>
