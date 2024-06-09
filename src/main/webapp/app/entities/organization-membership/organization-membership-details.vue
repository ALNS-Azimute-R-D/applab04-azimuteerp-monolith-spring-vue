<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organizationMembership">
        <h2 class="jh-entity-heading" data-cy="organizationMembershipDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.detail.title')"></span> {{ organizationMembership.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.joinedAt')"></span>
          </dt>
          <dd>
            <span>{{ organizationMembership.joinedAt }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + organizationMembership.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.organization')"></span>
          </dt>
          <dd>
            <div v-if="organizationMembership.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organizationMembership.organization.id } }">{{
                organizationMembership.organization.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMembership.person')"></span>
          </dt>
          <dd>
            <div v-if="organizationMembership.person">
              <router-link :to="{ name: 'PersonView', params: { personId: organizationMembership.person.id } }">{{
                organizationMembership.person.lastname
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="organizationMembership.id"
          :to="{ name: 'OrganizationMembershipEdit', params: { organizationMembershipId: organizationMembership.id } }"
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

<script lang="ts" src="./organization-membership-details.component.ts"></script>
