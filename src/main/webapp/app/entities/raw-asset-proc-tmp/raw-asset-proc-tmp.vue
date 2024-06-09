<template>
  <div>
    <h2 id="page-heading" data-cy="RawAssetProcTmpHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.home.title')" id="raw-asset-proc-tmp-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RawAssetProcTmpCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-raw-asset-proc-tmp"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rawAssetProcTmps && rawAssetProcTmps.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rawAssetProcTmps && rawAssetProcTmps.length > 0">
      <table class="table table-striped" aria-describedby="rawAssetProcTmps">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('statusRawProcessing')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.statusRawProcessing')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'statusRawProcessing'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullFilenamePath')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.fullFilenamePath')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullFilenamePath'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('assetRawContentAsBlob')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.assetRawContentAsBlob')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'assetRawContentAsBlob'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('assetType.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.assetType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'assetType.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rawAssetProcTmp in rawAssetProcTmps" :key="rawAssetProcTmp.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RawAssetProcTmpView', params: { rawAssetProcTmpId: rawAssetProcTmp.id } }">{{
                rawAssetProcTmp.id
              }}</router-link>
            </td>
            <td>{{ rawAssetProcTmp.name }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.StatusRawProcessingEnum.' + rawAssetProcTmp.statusRawProcessing)"></td>
            <td>{{ rawAssetProcTmp.fullFilenamePath }}</td>
            <td>
              <a
                v-if="rawAssetProcTmp.assetRawContentAsBlob"
                v-on:click="openFile(rawAssetProcTmp.assetRawContentAsBlobContentType, rawAssetProcTmp.assetRawContentAsBlob)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="rawAssetProcTmp.assetRawContentAsBlob"
                >{{ rawAssetProcTmp.assetRawContentAsBlobContentType }}, {{ byteSize(rawAssetProcTmp.assetRawContentAsBlob) }}</span
              >
            </td>
            <td>{{ rawAssetProcTmp.customAttributesDetailsJSON }}</td>
            <td>
              <div v-if="rawAssetProcTmp.assetType">
                <router-link :to="{ name: 'AssetTypeView', params: { assetTypeId: rawAssetProcTmp.assetType.id } }">{{
                  rawAssetProcTmp.assetType.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RawAssetProcTmpView', params: { rawAssetProcTmpId: rawAssetProcTmp.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RawAssetProcTmpEdit', params: { rawAssetProcTmpId: rawAssetProcTmp.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rawAssetProcTmp)"
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
          id="azimuteErpSpringVueMonolith04App.rawAssetProcTmp.delete.question"
          data-cy="rawAssetProcTmpDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rawAssetProcTmp-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.rawAssetProcTmp.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rawAssetProcTmp"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRawAssetProcTmp()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="rawAssetProcTmps && rawAssetProcTmps.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./raw-asset-proc-tmp.component.ts"></script>
