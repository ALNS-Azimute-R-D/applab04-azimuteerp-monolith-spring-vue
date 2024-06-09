<template>
  <div>
    <h2 id="page-heading" data-cy="ActivityHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.home.title')" id="activity-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ActivityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-activity"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && activities && activities.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="activities && activities.length > 0">
      <table class="table table-striped" aria-describedby="activities">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('shortDescription')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.shortDescription')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'shortDescription'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullDescription')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.fullDescription')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullDescription'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainGoals')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.mainGoals')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainGoals'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estimatedDurationTime')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.estimatedDurationTime')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estimatedDurationTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastPerformedDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.lastPerformedDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastPerformedDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.createdAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfActivity.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.typeOfActivity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfActivity.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdByUser.fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.createdByUser')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdByUser.fullname'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="activity in activities" :key="activity.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }">{{ activity.id }}</router-link>
            </td>
            <td>{{ activity.name }}</td>
            <td>{{ activity.shortDescription }}</td>
            <td>{{ activity.fullDescription }}</td>
            <td>{{ activity.mainGoals }}</td>
            <td>{{ formatDuration(activity.estimatedDurationTime) }}</td>
            <td>{{ activity.lastPerformedDate }}</td>
            <td>{{ formatDateShort(activity.createdAt) || '' }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + activity.activationStatus)"></td>
            <td>
              <div v-if="activity.typeOfActivity">
                <router-link :to="{ name: 'TypeOfActivityView', params: { typeOfActivityId: activity.typeOfActivity.id } }">{{
                  activity.typeOfActivity.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="activity.createdByUser">
                <router-link :to="{ name: 'PersonView', params: { personId: activity.createdByUser.id } }">{{
                  activity.createdByUser.fullname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ActivityEdit', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(activity)"
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
          id="azimuteErpSpringVueMonolith04App.activity.delete.question"
          data-cy="activityDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-activity-heading" v-text="t$('azimuteErpSpringVueMonolith04App.activity.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-activity"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeActivity()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="activities && activities.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./activity.component.ts"></script>
