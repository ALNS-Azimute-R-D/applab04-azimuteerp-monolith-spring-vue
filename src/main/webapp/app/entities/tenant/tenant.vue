<template>
  <div>
    <h2 id="page-heading" data-cy="TenantHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.home.title')" id="tenant-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TenantCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tenant"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tenants && tenants.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="tenants && tenants.length > 0">
      <table class="table table-striped" aria-describedby="tenants">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customerBusinessCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.customerBusinessCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customerBusinessCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessHandlerClazz')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.businessHandlerClazz')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessHandlerClazz'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainContactPersonDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.mainContactPersonDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'mainContactPersonDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('billingDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.billingDetailsJSON')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'billingDetailsJSON'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('technicalEnvironmentsDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.technicalEnvironmentsDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'technicalEnvironmentsDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('logoImg')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.logoImg')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logoImg'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tenant in tenants" :key="tenant.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TenantView', params: { tenantId: tenant.id } }">{{ tenant.id }}</router-link>
            </td>
            <td>{{ tenant.acronym }}</td>
            <td>{{ tenant.name }}</td>
            <td>{{ tenant.description }}</td>
            <td>{{ tenant.customerBusinessCode }}</td>
            <td>{{ tenant.businessHandlerClazz }}</td>
            <td>{{ tenant.mainContactPersonDetailsJSON }}</td>
            <td>{{ tenant.billingDetailsJSON }}</td>
            <td>{{ tenant.technicalEnvironmentsDetailsJSON }}</td>
            <td>{{ tenant.customAttributesDetailsJSON }}</td>
            <td>
              <a v-if="tenant.logoImg" v-on:click="openFile(tenant.logoImgContentType, tenant.logoImg)">
                <img v-bind:src="'data:' + tenant.logoImgContentType + ';base64,' + tenant.logoImg" style="max-height: 30px" alt="tenant" />
              </a>
              <span v-if="tenant.logoImg">{{ tenant.logoImgContentType }}, {{ byteSize(tenant.logoImg) }}</span>
            </td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + tenant.activationStatus)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TenantView', params: { tenantId: tenant.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TenantEdit', params: { tenantId: tenant.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tenant)"
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
          id="azimuteErpSpringVueMonolith04App.tenant.delete.question"
          data-cy="tenantDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-tenant-heading" v-text="t$('azimuteErpSpringVueMonolith04App.tenant.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-tenant"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTenant()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="tenants && tenants.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tenant.component.ts"></script>
