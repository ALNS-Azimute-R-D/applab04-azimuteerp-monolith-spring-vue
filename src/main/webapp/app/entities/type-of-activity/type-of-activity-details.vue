<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfActivity">
        <h2 class="jh-entity-heading" data-cy="typeOfActivityDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.detail.title')"></span> {{ typeOfActivity.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.acronym')"></span>
          </dt>
          <dd>
            <span>{{ typeOfActivity.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.name')"></span>
          </dt>
          <dd>
            <span>{{ typeOfActivity.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfActivity.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfActivity.handlerClazzName')"></span>
          </dt>
          <dd>
            <span>{{ typeOfActivity.handlerClazzName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfActivity.id"
          :to="{ name: 'TypeOfActivityEdit', params: { typeOfActivityId: typeOfActivity.id } }"
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

<script lang="ts" src="./type-of-activity-details.component.ts"></script>
