<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organizationDomain">
        <h2 class="jh-entity-heading" data-cy="organizationDomainDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.detail.title')"></span> {{ organizationDomain.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.domainAcronym')"></span>
          </dt>
          <dd>
            <span>{{ organizationDomain.domainAcronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.name')"></span>
          </dt>
          <dd>
            <span>{{ organizationDomain.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.isVerified')"></span>
          </dt>
          <dd>
            <span>{{ organizationDomain.isVerified }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.businessHandlerClazz')"></span>
          </dt>
          <dd>
            <span>{{ organizationDomain.businessHandlerClazz }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + organizationDomain.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationDomain.organization')"></span>
          </dt>
          <dd>
            <div v-if="organizationDomain.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organizationDomain.organization.id } }">{{
                organizationDomain.organization.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="organizationDomain.id"
          :to="{ name: 'OrganizationDomainEdit', params: { organizationDomainId: organizationDomain.id } }"
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

<script lang="ts" src="./organization-domain-details.component.ts"></script>
