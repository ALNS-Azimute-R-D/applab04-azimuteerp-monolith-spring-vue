<template>
  <div>
    <h2 id="page-heading" data-cy="TypeOfEventHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.home.title')" id="type-of-event-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TypeOfEventCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-type-of-event"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && typeOfEvents && typeOfEvents.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="typeOfEvents && typeOfEvents.length > 0">
      <table class="table table-striped" aria-describedby="typeOfEvents">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.acronym')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('handlerClazzName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.handlerClazzName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'handlerClazzName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="typeOfEvent in typeOfEvents" :key="typeOfEvent.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TypeOfEventView', params: { typeOfEventId: typeOfEvent.id } }">{{ typeOfEvent.id }}</router-link>
            </td>
            <td>{{ typeOfEvent.acronym }}</td>
            <td>{{ typeOfEvent.name }}</td>
            <td>{{ typeOfEvent.description }}</td>
            <td>{{ typeOfEvent.handlerClazzName }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TypeOfEventView', params: { typeOfEventId: typeOfEvent.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TypeOfEventEdit', params: { typeOfEventId: typeOfEvent.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(typeOfEvent)"
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
          id="azimuteErpSpringVueMonolith04App.typeOfEvent.delete.question"
          data-cy="typeOfEventDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-typeOfEvent-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-typeOfEvent"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeTypeOfEvent()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="typeOfEvents && typeOfEvents.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./type-of-event.component.ts"></script>
