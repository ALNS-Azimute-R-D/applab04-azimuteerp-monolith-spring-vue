<template>
  <div>
    <h2 id="page-heading" data-cy="OrganizationHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.home.title')" id="organization-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OrganizationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-organization"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && organizations && organizations.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="organizations && organizations.length > 0">
      <table class="table table-striped" aria-describedby="organizations">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.businessCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hierarchicalLevel')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.hierarchicalLevel')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hierarchicalLevel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('businessHandlerClazz')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.businessHandlerClazz')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'businessHandlerClazz'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainContactPersonDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.mainContactPersonDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'mainContactPersonDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('technicalEnvironmentsDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.technicalEnvironmentsDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'technicalEnvironmentsDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organizationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.organizationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organizationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('logoImg')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.logoImg')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'logoImg'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tenant.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.tenant')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tenant.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfOrganization.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.typeOfOrganization')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'typeOfOrganization.name'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('organizationParent.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.organizationParent')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'organizationParent.name'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="organization in organizations" :key="organization.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.id } }">{{
                organization.id
              }}</router-link>
            </td>
            <td>{{ organization.acronym }}</td>
            <td>{{ organization.businessCode }}</td>
            <td>{{ organization.hierarchicalLevel }}</td>
            <td>{{ organization.name }}</td>
            <td>{{ organization.description }}</td>
            <td>{{ organization.businessHandlerClazz }}</td>
            <td>{{ organization.mainContactPersonDetailsJSON }}</td>
            <td>{{ organization.technicalEnvironmentsDetailsJSON }}</td>
            <td>{{ organization.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.OrganizationStatusEnum.' + organization.organizationStatus)"></td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + organization.activationStatus)"></td>
            <td>
              <a v-if="organization.logoImg" v-on:click="openFile(organization.logoImgContentType, organization.logoImg)">
                <img
                  v-bind:src="'data:' + organization.logoImgContentType + ';base64,' + organization.logoImg"
                  style="max-height: 30px"
                  alt="organization"
                />
              </a>
              <span v-if="organization.logoImg">{{ organization.logoImgContentType }}, {{ byteSize(organization.logoImg) }}</span>
            </td>
            <td>
              <div v-if="organization.tenant">
                <router-link :to="{ name: 'TenantView', params: { tenantId: organization.tenant.id } }">{{
                  organization.tenant.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="organization.typeOfOrganization">
                <router-link
                  :to="{ name: 'TypeOfOrganizationView', params: { typeOfOrganizationId: organization.typeOfOrganization.id } }"
                  >{{ organization.typeOfOrganization.name }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="organization.organizationParent">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.organizationParent.id } }">{{
                  organization.organizationParent.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OrganizationEdit', params: { organizationId: organization.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(organization)"
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
          id="azimuteErpSpringVueMonolith04App.organization.delete.question"
          data-cy="organizationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-organization-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.organization.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-organization"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeOrganization()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="organizations && organizations.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./organization.component.ts"></script>
