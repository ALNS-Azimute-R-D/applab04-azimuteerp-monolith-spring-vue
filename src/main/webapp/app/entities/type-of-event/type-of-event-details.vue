<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfEvent">
        <h2 class="jh-entity-heading" data-cy="typeOfEventDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.detail.title')"></span> {{ typeOfEvent.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.acronym')"></span>
          </dt>
          <dd>
            <span>{{ typeOfEvent.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.name')"></span>
          </dt>
          <dd>
            <span>{{ typeOfEvent.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfEvent.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfEvent.handlerClazzName')"></span>
          </dt>
          <dd>
            <span>{{ typeOfEvent.handlerClazzName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfEvent.id"
          :to="{ name: 'TypeOfEventEdit', params: { typeOfEventId: typeOfEvent.id } }"
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

<script lang="ts" src="./type-of-event-details.component.ts"></script>
