<template>
  <div>
    <h2 id="page-heading" data-cy="ArtistHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.home.title')" id="artist-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ArtistCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-artist"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && artists && artists.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="artists && artists.length > 0">
      <table class="table table-striped" aria-describedby="artists">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('publicName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.publicName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'publicName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('realName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.realName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'realName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('biographyDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.biographyDetailsJSON')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'biographyDetailsJSON'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfArtmedia.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.typeOfArtmedia')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfArtmedia.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfArtist.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.typeOfArtist')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfArtist.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('artistAggregator.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.artist.artistAggregator')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'artistAggregator.acronym'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="artist in artists" :key="artist.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ArtistView', params: { artistId: artist.id } }">{{ artist.id }}</router-link>
            </td>
            <td>{{ artist.acronym }}</td>
            <td>{{ artist.publicName }}</td>
            <td>{{ artist.realName }}</td>
            <td>{{ artist.biographyDetailsJSON }}</td>
            <td>
              <div v-if="artist.typeOfArtmedia">
                <router-link :to="{ name: 'TypeOfArtmediaView', params: { typeOfArtmediaId: artist.typeOfArtmedia.id } }">{{
                  artist.typeOfArtmedia.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="artist.typeOfArtist">
                <router-link :to="{ name: 'TypeOfArtistView', params: { typeOfArtistId: artist.typeOfArtist.id } }">{{
                  artist.typeOfArtist.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="artist.artistAggregator">
                <router-link :to="{ name: 'ArtistView', params: { artistId: artist.artistAggregator.id } }">{{
                  artist.artistAggregator.acronym
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ArtistView', params: { artistId: artist.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ArtistEdit', params: { artistId: artist.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(artist)"
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
          id="azimuteErpSpringVueMonolith04App.artist.delete.question"
          data-cy="artistDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-artist-heading" v-text="t$('azimuteErpSpringVueMonolith04App.artist.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-artist"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeArtist()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="artists && artists.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./artist.component.ts"></script>
