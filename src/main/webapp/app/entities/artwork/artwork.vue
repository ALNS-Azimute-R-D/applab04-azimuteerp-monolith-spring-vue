<template>
  <div>
    <h2 id="page-heading" data-cy="ArtworkHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.home.title')" id="artwork-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ArtworkCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-artwork"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && artworks && artworks.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="artworks && artworks.length > 0">
      <table class="table table-striped" aria-describedby="artworks">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('artworkTitle')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.artworkTitle')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'artworkTitle'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('productionYear')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.productionYear')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'productionYear'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('seasonNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.seasonNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'seasonNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('episodeOrSequenceNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.episodeOrSequenceNumber')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'episodeOrSequenceNumber'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registerIdInIMDB')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.registerIdInIMDB')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registerIdInIMDB'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('assetsCollectionUUID')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.assetsCollectionUUID')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'assetsCollectionUUID'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contentDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.contentDetailsJSON')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contentDetailsJSON'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfArtmedia.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.typeOfArtmedia')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfArtmedia.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('artworkAggregator.artworkTitle')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artwork.artworkAggregator')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'artworkAggregator.artworkTitle'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="artwork in artworks" :key="artwork.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ArtworkView', params: { artworkId: artwork.id } }">{{ artwork.id }}</router-link>
            </td>
            <td>{{ artwork.artworkTitle }}</td>
            <td>{{ artwork.productionYear }}</td>
            <td>{{ artwork.seasonNumber }}</td>
            <td>{{ artwork.episodeOrSequenceNumber }}</td>
            <td>{{ artwork.registerIdInIMDB }}</td>
            <td>{{ artwork.assetsCollectionUUID }}</td>
            <td>{{ artwork.contentDetailsJSON }}</td>
            <td>
              <div v-if="artwork.typeOfArtmedia">
                <router-link :to="{ name: 'TypeOfArtmediaView', params: { typeOfArtmediaId: artwork.typeOfArtmedia.id } }">{{
                  artwork.typeOfArtmedia.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="artwork.artworkAggregator">
                <router-link :to="{ name: 'ArtworkView', params: { artworkId: artwork.artworkAggregator.id } }">{{
                  artwork.artworkAggregator.artworkTitle
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ArtworkView', params: { artworkId: artwork.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ArtworkEdit', params: { artworkId: artwork.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(artwork)"
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
          id="azimuteErpSpringVueMonolith04App.artwork.delete.question"
          data-cy="artworkDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-artwork-heading" v-text="t$('azimuteErpSpringVueMonolith04App.artwork.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-artwork"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeArtwork()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="artworks && artworks.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./artwork.component.ts"></script>
