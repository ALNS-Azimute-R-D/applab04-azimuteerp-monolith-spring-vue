<template>
  <div>
    <h2 id="page-heading" data-cy="EventProgramHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.home.title')" id="event-program-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EventProgramCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-event-program"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && eventPrograms && eventPrograms.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="eventPrograms && eventPrograms.length > 0">
      <table class="table table-striped" aria-describedby="eventPrograms">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('percentageExecution')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.percentageExecution')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'percentageExecution'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('event.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.event')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'event.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('program.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.program')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'program.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('responsiblePerson.fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.responsiblePerson')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'responsiblePerson.fullname'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="eventProgram in eventPrograms" :key="eventProgram.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EventProgramView', params: { eventProgramId: eventProgram.id } }">{{
                eventProgram.id
              }}</router-link>
            </td>
            <td>{{ eventProgram.percentageExecution }}</td>
            <td>
              <div v-if="eventProgram.event">
                <router-link :to="{ name: 'EventView', params: { eventId: eventProgram.event.id } }">{{
                  eventProgram.event.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="eventProgram.program">
                <router-link :to="{ name: 'ProgramView', params: { programId: eventProgram.program.id } }">{{
                  eventProgram.program.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="eventProgram.responsiblePerson">
                <router-link :to="{ name: 'PersonView', params: { personId: eventProgram.responsiblePerson.id } }">{{
                  eventProgram.responsiblePerson.fullname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EventProgramView', params: { eventProgramId: eventProgram.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EventProgramEdit', params: { eventProgramId: eventProgram.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(eventProgram)"
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
          id="azimuteErpSpringVueMonolith04App.eventProgram.delete.question"
          data-cy="eventProgramDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-eventProgram-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-eventProgram"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEventProgram()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="eventPrograms && eventPrograms.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event-program.component.ts"></script>
