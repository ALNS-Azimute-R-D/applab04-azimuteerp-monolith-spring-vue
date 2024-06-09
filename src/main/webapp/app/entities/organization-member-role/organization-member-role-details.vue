<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organizationMemberRole">
        <h2 class="jh-entity-heading" data-cy="organizationMemberRoleDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.detail.title')"></span> {{ organizationMemberRole.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.joinedAt')"></span>
          </dt>
          <dd>
            <span>{{ organizationMemberRole.joinedAt }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.organizationMembership')"></span>
          </dt>
          <dd>
            <div v-if="organizationMemberRole.organizationMembership">
              <router-link
                :to="{
                  name: 'OrganizationMembershipView',
                  params: { organizationMembershipId: organizationMemberRole.organizationMembership.id },
                }"
                >{{ organizationMemberRole.organizationMembership.id }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationMemberRole.organizationRole')"></span>
          </dt>
          <dd>
            <div v-if="organizationMemberRole.organizationRole">
              <router-link
                :to="{ name: 'OrganizationRoleView', params: { organizationRoleId: organizationMemberRole.organizationRole.id } }"
                >{{ organizationMemberRole.organizationRole.id }}</router-link
              >
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="organizationMemberRole.id"
          :to="{ name: 'OrganizationMemberRoleEdit', params: { organizationMemberRoleId: organizationMemberRole.id } }"
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

<script lang="ts" src="./organization-member-role-details.component.ts"></script>
