<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeOfOrganization">
        <h2 class="jh-entity-heading" data-cy="typeOfOrganizationDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.detail.title')"></span> {{ typeOfOrganization.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.acronym')"></span>
          </dt>
          <dd>
            <span>{{ typeOfOrganization.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.name')"></span>
          </dt>
          <dd>
            <span>{{ typeOfOrganization.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.description')"></span>
          </dt>
          <dd>
            <span>{{ typeOfOrganization.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.typeOfOrganization.businessHandlerClazz')"></span>
          </dt>
          <dd>
            <span>{{ typeOfOrganization.businessHandlerClazz }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="typeOfOrganization.id"
          :to="{ name: 'TypeOfOrganizationEdit', params: { typeOfOrganizationId: typeOfOrganization.id } }"
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

<script lang="ts" src="./type-of-organization-details.component.ts"></script>
