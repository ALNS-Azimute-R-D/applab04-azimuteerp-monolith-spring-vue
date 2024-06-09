<template>
  <div>
    <h2 id="page-heading" data-cy="AssetMetadataHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.home.title')" id="asset-metadata-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AssetMetadataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-asset-metadata"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && assetMetadata && assetMetadata.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="assetMetadata && assetMetadata.length > 0">
      <table class="table table-striped" aria-describedby="assetMetadata">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('metadataDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.metadataDetailsJSON')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'metadataDetailsJSON'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('asset.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.asset')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'asset.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="assetMetadata in assetMetadata" :key="assetMetadata.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AssetMetadataView', params: { assetMetadataId: assetMetadata.id } }">{{
                assetMetadata.id
              }}</router-link>
            </td>
            <td>{{ assetMetadata.metadataDetailsJSON }}</td>
            <td>
              <div v-if="assetMetadata.asset">
                <router-link :to="{ name: 'AssetView', params: { assetId: assetMetadata.asset.id } }">{{
                  assetMetadata.asset.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AssetMetadataView', params: { assetMetadataId: assetMetadata.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AssetMetadataEdit', params: { assetMetadataId: assetMetadata.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(assetMetadata)"
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
          id="azimuteErpSpringVueMonolith04App.assetMetadata.delete.question"
          data-cy="assetMetadataDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-assetMetadata-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.assetMetadata.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-assetMetadata"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeAssetMetadata()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="assetMetadata && assetMetadata.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./asset-metadata.component.ts"></script>
