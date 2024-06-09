<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="businessUnit">
        <h2 class="jh-entity-heading" data-cy="businessUnitDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.detail.title')"></span> {{ businessUnit.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.acronym')"></span>
          </dt>
          <dd>
            <span>{{ businessUnit.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.hierarchicalLevel')"></span>
          </dt>
          <dd>
            <span>{{ businessUnit.hierarchicalLevel }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.name')"></span>
          </dt>
          <dd>
            <span>{{ businessUnit.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + businessUnit.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.organization')"></span>
          </dt>
          <dd>
            <div v-if="businessUnit.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: businessUnit.organization.id } }">{{
                businessUnit.organization.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.businessUnit.businessUnitParent')"></span>
          </dt>
          <dd>
            <div v-if="businessUnit.businessUnitParent">
              <router-link :to="{ name: 'BusinessUnitView', params: { businessUnitId: businessUnit.businessUnitParent.id } }">{{
                businessUnit.businessUnitParent.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="businessUnit.id"
          :to="{ name: 'BusinessUnitEdit', params: { businessUnitId: businessUnit.id } }"
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

<script lang="ts" src="./business-unit-details.component.ts"></script>
