<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="activity">
        <h2 class="jh-entity-heading" data-cy="activityDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.detail.title')"></span> {{ activity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.name')"></span>
          </dt>
          <dd>
            <span>{{ activity.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.shortDescription')"></span>
          </dt>
          <dd>
            <span>{{ activity.shortDescription }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.fullDescription')"></span>
          </dt>
          <dd>
            <span>{{ activity.fullDescription }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.mainGoals')"></span>
          </dt>
          <dd>
            <span>{{ activity.mainGoals }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.estimatedDurationTime')"></span>
          </dt>
          <dd>
            <span>{{ formatDuration(activity.estimatedDurationTime) }} ({{ activity.estimatedDurationTime }})</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.lastPerformedDate')"></span>
          </dt>
          <dd>
            <span>{{ activity.lastPerformedDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="activity.createdAt">{{ formatDateLong(activity.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + activity.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.typeOfActivity')"></span>
          </dt>
          <dd>
            <div v-if="activity.typeOfActivity">
              <router-link :to="{ name: 'TypeOfActivityView', params: { typeOfActivityId: activity.typeOfActivity.id } }">{{
                activity.typeOfActivity.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.createdByUser')"></span>
          </dt>
          <dd>
            <div v-if="activity.createdByUser">
              <router-link :to="{ name: 'PersonView', params: { personId: activity.createdByUser.id } }">{{
                activity.createdByUser.fullname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.activity.assetCollection')"></span>
          </dt>
          <dd>
            <span v-for="(assetCollection, i) in activity.assetCollections" :key="assetCollection.id"
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
        <router-link v-if="activity.id" :to="{ name: 'ActivityEdit', params: { activityId: activity.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./activity-details.component.ts"></script>
