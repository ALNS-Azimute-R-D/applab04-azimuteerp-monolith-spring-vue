<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="eventProgram">
        <h2 class="jh-entity-heading" data-cy="eventProgramDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.detail.title')"></span> {{ eventProgram.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.percentageExecution')"></span>
          </dt>
          <dd>
            <span>{{ eventProgram.percentageExecution }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.event')"></span>
          </dt>
          <dd>
            <div v-if="eventProgram.event">
              <router-link :to="{ name: 'EventView', params: { eventId: eventProgram.event.id } }">{{
                eventProgram.event.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.program')"></span>
          </dt>
          <dd>
            <div v-if="eventProgram.program">
              <router-link :to="{ name: 'ProgramView', params: { programId: eventProgram.program.id } }">{{
                eventProgram.program.acronym
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.responsiblePerson')"></span>
          </dt>
          <dd>
            <div v-if="eventProgram.responsiblePerson">
              <router-link :to="{ name: 'PersonView', params: { personId: eventProgram.responsiblePerson.id } }">{{
                eventProgram.responsiblePerson.fullname
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="eventProgram.id"
          :to="{ name: 'EventProgramEdit', params: { eventProgramId: eventProgram.id } }"
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

<script lang="ts" src="./event-program-details.component.ts"></script>
