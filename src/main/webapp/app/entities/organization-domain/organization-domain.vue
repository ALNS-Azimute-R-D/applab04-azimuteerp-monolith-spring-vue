<template>
  <div>
    <h2 id="page-heading" data-cy="OrganizationDomainHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.home.title')" id="organization-domain-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OrganizationDomainCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-organization-domain"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && organizationDomains && organizationDomains.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="organizationDomains && organizationDomains.length > 0">
      <table class="table table-striped" aria-describedby="organizationDomains">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('domainAcronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.domainAcronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'domainAcronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isVerified')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.isVerified')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isVerified'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessHandlerClazz')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.businessHandlerClazz')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessHandlerClazz'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organization.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.organization')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organization.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="organizationDomain in organizationDomains" :key="organizationDomain.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrganizationDomainView', params: { organizationDomainId: organizationDomain.id } }">{{
                organizationDomain.id
              }}</router-link>
            </td>
            <td>{{ organizationDomain.domainAcronym }}</td>
            <td>{{ organizationDomain.name }}</td>
            <td>{{ organizationDomain.isVerified }}</td>
            <td>{{ organizationDomain.businessHandlerClazz }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + organizationDomain.activationStatus)"></td>
            <td>
              <div v-if="organizationDomain.organization">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: organizationDomain.organization.id } }">{{
                  organizationDomain.organization.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OrganizationDomainView', params: { organizationDomainId: organizationDomain.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OrganizationDomainEdit', params: { organizationDomainId: organizationDomain.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(organizationDomain)"
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
          id="azimuteErpSpringVueMonolith04App.organizationDomain.delete.question"
          data-cy="organizationDomainDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-organizationDomain-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-organizationDomain"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOrganizationDomain()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="organizationDomains && organizationDomains.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./organization-domain.component.ts"></script>
