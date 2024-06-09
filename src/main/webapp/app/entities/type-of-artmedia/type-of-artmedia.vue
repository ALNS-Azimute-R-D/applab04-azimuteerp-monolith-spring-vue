<template>
  <div>
    <h2 id="page-heading" data-cy="TypeOfArtmediaHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.home.title')" id="type-of-artmedia-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TypeOfArtmediaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-type-of-artmedia"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && typeOfArtmedias && typeOfArtmedias.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="typeOfArtmedias && typeOfArtmedias.length > 0">
      <table class="table table-striped" aria-describedby="typeOfArtmedias">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="typeOfArtmedia in typeOfArtmedias" :key="typeOfArtmedia.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TypeOfArtmediaView', params: { typeOfArtmediaId: typeOfArtmedia.id } }">{{
                typeOfArtmedia.id
              }}</router-link>
            </td>
            <td>{{ typeOfArtmedia.acronym }}</td>
            <td>{{ typeOfArtmedia.name }}</td>
            <td>{{ typeOfArtmedia.description }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TypeOfArtmediaView', params: { typeOfArtmediaId: typeOfArtmedia.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TypeOfArtmediaEdit', params: { typeOfArtmediaId: typeOfArtmedia.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(typeOfArtmedia)"
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
          id="azimuteErpSpringVueMonolith04App.typeOfArtmedia.delete.question"
          data-cy="typeOfArtmediaDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-typeOfArtmedia-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.typeOfArtmedia.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-typeOfArtmedia"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTypeOfArtmedia()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="typeOfArtmedias && typeOfArtmedias.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./type-of-artmedia.component.ts"></script>
