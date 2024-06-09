<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfPerson">
        <h2 class="jh-entity-heading" data-cy="typeOfPersonDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfPerson.detail.title')"></span> {{ typeOfPerson.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfPerson.code')"></span>
          </dt>
          <dd>
            <span>{{ typeOfPerson.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfPerson.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfPerson.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfPerson.id"
          :to="{ name: 'TypeOfPersonEdit', params: { typeOfPersonId: typeOfPerson.id } }"
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

<script lang="ts" src="./type-of-person-details.component.ts"></script>
