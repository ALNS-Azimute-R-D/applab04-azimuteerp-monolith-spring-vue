<template>
  <div>
    <h2 id="page-heading" data-cy="OrganizationAttributeHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.home.title')" id="organization-attribute-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OrganizationAttributeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-organization-attribute"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && organizationAttributes && organizationAttributes.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="organizationAttributes && organizationAttributes.length > 0">
      <table class="table table-striped" aria-describedby="organizationAttributes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attributeName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.attributeName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attributeName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attributeValue')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.attributeValue')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attributeValue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organization.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.organization')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organization.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="organizationAttribute in organizationAttributes" :key="organizationAttribute.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrganizationAttributeView', params: { organizationAttributeId: organizationAttribute.id } }">{{
                organizationAttribute.id
              }}</router-link>
            </td>
            <td>{{ organizationAttribute.attributeName }}</td>
            <td>{{ organizationAttribute.attributeValue }}</td>
            <td>
              <div v-if="organizationAttribute.organization">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: organizationAttribute.organization.id } }">{{
                  organizationAttribute.organization.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OrganizationAttributeView', params: { organizationAttributeId: organizationAttribute.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OrganizationAttributeEdit', params: { organizationAttributeId: organizationAttribute.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(organizationAttribute)"
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
          id="azimuteErpSpringVueMonolith04App.organizationAttribute.delete.question"
          data-cy="organizationAttributeDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-organizationAttribute-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-organizationAttribute"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOrganizationAttribute()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="organizationAttributes && organizationAttributes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./organization-attribute.component.ts"></script>
