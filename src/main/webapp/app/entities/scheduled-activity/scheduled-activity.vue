<template>
  <div>
    <h2 id="page-heading" data-cy="ScheduledActivityHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.home.title')" id="scheduled-activity-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ScheduledActivityCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-scheduled-activity"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && scheduledActivities && scheduledActivities.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="scheduledActivities && scheduledActivities.length > 0">
      <table class="table table-striped" aria-describedby="scheduledActivities">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customizedName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.customizedName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customizedName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('startTime')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.startTime')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('endTime')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.endTime')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfAttendees')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.numberOfAttendees')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfAttendees'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('averageEvaluationInStars')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.averageEvaluationInStars')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'averageEvaluationInStars'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributtesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.customAttributtesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributtesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('program.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.program')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'program.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activity.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.activity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activity.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('responsiblePerson.fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.responsiblePerson')"></span>
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
          <tr v-for="scheduledActivity in scheduledActivities" :key="scheduledActivity.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ScheduledActivityView', params: { scheduledActivityId: scheduledActivity.id } }">{{
                scheduledActivity.id
              }}</router-link>
            </td>
            <td>{{ scheduledActivity.customizedName }}</td>
            <td>{{ formatDateShort(scheduledActivity.startTime) || '' }}</td>
            <td>{{ formatDateShort(scheduledActivity.endTime) || '' }}</td>
            <td>{{ scheduledActivity.numberOfAttendees }}</td>
            <td>{{ scheduledActivity.averageEvaluationInStars }}</td>
            <td>{{ scheduledActivity.customAttributtesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + scheduledActivity.activationStatus)"></td>
            <td>
              <div v-if="scheduledActivity.program">
                <router-link :to="{ name: 'ProgramView', params: { programId: scheduledActivity.program.id } }">{{
                  scheduledActivity.program.acronym
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="scheduledActivity.activity">
                <router-link :to="{ name: 'ActivityView', params: { activityId: scheduledActivity.activity.id } }">{{
                  scheduledActivity.activity.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="scheduledActivity.responsiblePerson">
                <router-link :to="{ name: 'PersonView', params: { personId: scheduledActivity.responsiblePerson.id } }">{{
                  scheduledActivity.responsiblePerson.fullname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ScheduledActivityView', params: { scheduledActivityId: scheduledActivity.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ScheduledActivityEdit', params: { scheduledActivityId: scheduledActivity.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(scheduledActivity)"
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
          id="azimuteErpSpringVueMonolith04App.scheduledActivity.delete.question"
          data-cy="scheduledActivityDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-scheduledActivity-heading"
          v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-scheduledActivity"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeScheduledActivity()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="scheduledActivities && scheduledActivities.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./scheduled-activity.component.ts"></script>
