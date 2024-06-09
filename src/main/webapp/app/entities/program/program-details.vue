<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="program">
        <h2 class="jh-entity-heading" data-cy="programDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.program.detail.title')"></span> {{ program.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.program.acronym')"></span>
          </dt>
          <dd>
            <span>{{ program.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.program.name')"></span>
          </dt>
          <dd>
            <span>{{ program.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.program.description')"></span>
          </dt>
          <dd>
            <span>{{ program.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.program.fullDescription')"></span>
          </dt>
          <dd>
            <span>{{ program.fullDescription }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.program.targetPublic')"></span>
          </dt>
          <dd>
            <span>{{ program.targetPublic }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.program.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + program.activationStatus)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="program.id" :to="{ name: 'ProgramEdit', params: { programId: program.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./program-details.component.ts"></script>
