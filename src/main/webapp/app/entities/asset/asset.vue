<template>
  <div>
    <h2 id="page-heading" data-cy="AssetHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.home.title')" id="asset-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AssetCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-asset"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && assets && assets.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="assets && assets.length > 0">
      <table class="table table-striped" aria-describedby="assets">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('storageTypeUsed')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.storageTypeUsed')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'storageTypeUsed'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullFilenamePath')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.fullFilenamePath')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullFilenamePath'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('preferredPurpose')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.preferredPurpose')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'preferredPurpose'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('assetContentAsBlob')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetContentAsBlob')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'assetContentAsBlob'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('assetType.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.assetType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'assetType.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rawAssetProcTmp.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.asset.rawAssetProcTmp')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rawAssetProcTmp.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="asset in assets" :key="asset.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AssetView', params: { assetId: asset.id } }">{{ asset.id }}</router-link>
            </td>
            <td>{{ asset.name }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.StorageTypeEnum.' + asset.storageTypeUsed)"></td>
            <td>{{ asset.fullFilenamePath }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.StatusAssetEnum.' + asset.status)"></td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.PreferredPurposeEnum.' + asset.preferredPurpose)"></td>
            <td>
              <a
                v-if="asset.assetContentAsBlob"
                v-on:click="openFile(asset.assetContentAsBlobContentType, asset.assetContentAsBlob)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="asset.assetContentAsBlob"
                >{{ asset.assetContentAsBlobContentType }}, {{ byteSize(asset.assetContentAsBlob) }}</span
              >
            </td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + asset.activationStatus)"></td>
            <td>
              <div v-if="asset.assetType">
                <router-link :to="{ name: 'AssetTypeView', params: { assetTypeId: asset.assetType.id } }">{{
                  asset.assetType.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="asset.rawAssetProcTmp">
                <router-link :to="{ name: 'RawAssetProcTmpView', params: { rawAssetProcTmpId: asset.rawAssetProcTmp.id } }">{{
                  asset.rawAssetProcTmp.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AssetView', params: { assetId: asset.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AssetEdit', params: { assetId: asset.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(asset)"
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
          id="azimuteErpSpringVueMonolith04App.asset.delete.question"
          data-cy="assetDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-asset-heading" v-text="t$('azimuteErpSpringVueMonolith04App.asset.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-asset"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeAsset()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="assets && assets.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./asset.component.ts"></script>
