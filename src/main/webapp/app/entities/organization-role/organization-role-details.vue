<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organizationRole">
        <h2 class="jh-entity-heading" data-cy="organizationRoleDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.detail.title')"></span> {{ organizationRole.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.roleName')"></span>
          </dt>
          <dd>
            <span>{{ organizationRole.roleName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + organizationRole.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationRole.organization')"></span>
          </dt>
          <dd>
            <div v-if="organizationRole.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organizationRole.organization.id } }">{{
                organizationRole.organization.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="organizationRole.id"
          :to="{ name: 'OrganizationRoleEdit', params: { organizationRoleId: organizationRole.id } }"
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

<script lang="ts" src="./organization-role-details.component.ts"></script>
