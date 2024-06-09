<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="scheduledActivity">
        <h2 class="jh-entity-heading" data-cy="scheduledActivityDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.detail.title')"></span> {{ scheduledActivity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.customizedName')"></span>
          </dt>
          <dd>
            <span>{{ scheduledActivity.customizedName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.startTime')"></span>
          </dt>
          <dd>
            <span v-if="scheduledActivity.startTime">{{ formatDateLong(scheduledActivity.startTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.endTime')"></span>
          </dt>
          <dd>
            <span v-if="scheduledActivity.endTime">{{ formatDateLong(scheduledActivity.endTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.numberOfAttendees')"></span>
          </dt>
          <dd>
            <span>{{ scheduledActivity.numberOfAttendees }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.averageEvaluationInStars')"></span>
          </dt>
          <dd>
            <span>{{ scheduledActivity.averageEvaluationInStars }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.customAttributtesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ scheduledActivity.customAttributtesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + scheduledActivity.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.program')"></span>
          </dt>
          <dd>
            <div v-if="scheduledActivity.program">
              <router-link :to="{ name: 'ProgramView', params: { programId: scheduledActivity.program.id } }">{{
                scheduledActivity.program.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.activity')"></span>
          </dt>
          <dd>
            <div v-if="scheduledActivity.activity">
              <router-link :to="{ name: 'ActivityView', params: { activityId: scheduledActivity.activity.id } }">{{
                scheduledActivity.activity.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.responsiblePerson')"></span>
          </dt>
          <dd>
            <div v-if="scheduledActivity.responsiblePerson">
              <router-link :to="{ name: 'PersonView', params: { personId: scheduledActivity.responsiblePerson.id } }">{{
                scheduledActivity.responsiblePerson.fullname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.scheduledActivity.assetCollection')"></span>
          </dt>
          <dd>
            <span v-for="(assetCollection, i) in scheduledActivity.assetCollections" :key="assetCollection.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'AssetCollectionView', params: { assetCollectionId: assetCollection.id } }">{{
                assetCollection.id
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="scheduledActivity.id"
          :to="{ name: 'ScheduledActivityEdit', params: { scheduledActivityId: scheduledActivity.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./scheduled-activity-details.component.ts"></script>
