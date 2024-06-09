<template>
  <div>
    <h2 id="page-heading" data-cy="BusinessUnitHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.home.title')" id="business-unit-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'BusinessUnitCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-business-unit"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && businessUnits && businessUnits.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="businessUnits && businessUnits.length > 0">
      <table class="table table-striped" aria-describedby="businessUnits">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hierarchicalLevel')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.hierarchicalLevel')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hierarchicalLevel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organization.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.organization')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organization.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessUnitParent.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.businessUnitParent')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'businessUnitParent.name'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="businessUnit in businessUnits" :key="businessUnit.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BusinessUnitView', params: { businessUnitId: businessUnit.id } }">{{
                businessUnit.id
              }}</router-link>
            </td>
            <td>{{ businessUnit.acronym }}</td>
            <td>{{ businessUnit.hierarchicalLevel }}</td>
            <td>{{ businessUnit.name }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + businessUnit.activationStatus)"></td>
            <td>
              <div v-if="businessUnit.organization">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: businessUnit.organization.id } }">{{
                  businessUnit.organization.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="businessUnit.businessUnitParent">
                <router-link :to="{ name: 'BusinessUnitView', params: { businessUnitId: businessUnit.businessUnitParent.id } }">{{
                  businessUnit.businessUnitParent.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BusinessUnitView', params: { businessUnitId: businessUnit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BusinessUnitEdit', params: { businessUnitId: businessUnit.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(businessUnit)"
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
          id="azimuteErpSpringVueMonolith04App.businessUnit.delete.question"
          data-cy="businessUnitDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-businessUnit-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-businessUnit"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeBusinessUnit()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="businessUnits && businessUnits.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./business-unit.component.ts"></script>
