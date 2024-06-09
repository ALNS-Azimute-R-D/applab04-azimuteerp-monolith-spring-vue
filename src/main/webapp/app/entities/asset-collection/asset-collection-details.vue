<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="assetCollection">
        <h2 class="jh-entity-heading" data-cy="assetCollectionDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.detail.title')"></span> {{ assetCollection.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.name')"></span>
          </dt>
          <dd>
            <span>{{ assetCollection.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.fullFilenamePath')"></span>
          </dt>
          <dd>
            <span>{{ assetCollection.fullFilenamePath }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + assetCollection.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.asset')"></span>
          </dt>
          <dd>
            <span v-for="(asset, i) in assetCollection.assets" :key="asset.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'AssetView', params: { assetId: asset.id } }">{{ asset.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.article')"></span>
          </dt>
          <dd>
            <span v-for="(article, i) in assetCollection.articles" :key="article.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ArticleView', params: { articleId: article.id } }">{{ article.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.event')"></span>
          </dt>
          <dd>
            <span v-for="(event, i) in assetCollection.events" :key="event.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'EventView', params: { eventId: event.id } }">{{ event.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.activity')"></span>
          </dt>
          <dd>
            <span v-for="(activity, i) in assetCollection.activities" :key="activity.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ActivityView', params: { activityId: activity.id } }">{{ activity.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetCollection.scheduledActivity')"></span>
          </dt>
          <dd>
            <span v-for="(scheduledActivity, i) in assetCollection.scheduledActivities" :key="scheduledActivity.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ScheduledActivityView', params: { scheduledActivityId: scheduledActivity.id } }">{{
                scheduledActivity.id
              }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="assetCollection.id"
          :to="{ name: 'AssetCollectionEdit', params: { assetCollectionId: assetCollection.id } }"
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

<script lang="ts" src="./asset-collection-details.component.ts"></script>
