<template>
  <div>
    <h2 id="page-heading" data-cy="OrganizationMemberRoleHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.home.title')" id="organization-member-role-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OrganizationMemberRoleCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-organization-member-role"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && organizationMemberRoles && organizationMemberRoles.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="organizationMemberRoles && organizationMemberRoles.length > 0">
      <table class="table table-striped" aria-describedby="organizationMemberRoles">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('joinedAt')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.joinedAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'joinedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organizationMembership.id')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.organizationMembership')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'organizationMembership.id'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organizationRole.id')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.organizationRole')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organizationRole.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="organizationMemberRole in organizationMemberRoles" :key="organizationMemberRole.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrganizationMemberRoleView', params: { organizationMemberRoleId: organizationMemberRole.id } }">{{
                organizationMemberRole.id
              }}</router-link>
            </td>
            <td>{{ organizationMemberRole.joinedAt }}</td>
            <td>
              <div v-if="organizationMemberRole.organizationMembership">
                <router-link
                  :to="{
                    name: 'OrganizationMembershipView',
                    params: { organizationMembershipId: organizationMemberRole.organizationMembership.id },
                  }"
                  >{{ organizationMemberRole.organizationMembership.id }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="organizationMemberRole.organizationRole">
                <router-link
                  :to="{ name: 'OrganizationRoleView', params: { organizationRoleId: organizationMemberRole.organizationRole.id } }"
                  >{{ organizationMemberRole.organizationRole.id }}</router-link
                >
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OrganizationMemberRoleView', params: { organizationMemberRoleId: organizationMemberRole.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OrganizationMemberRoleEdit', params: { organizationMemberRoleId: organizationMemberRole.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(organizationMemberRole)"
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
          id="azimuteErpSpringVueMonolith04App.organizationMemberRole.delete.question"
          data-cy="organizationMemberRoleDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-organizationMemberRole-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-organizationMemberRole"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOrganizationMemberRole()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="organizationMemberRoles && organizationMemberRoles.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./organization-member-role.component.ts"></script>
