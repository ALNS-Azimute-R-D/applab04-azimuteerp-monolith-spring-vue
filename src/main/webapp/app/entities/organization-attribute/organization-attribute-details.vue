<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organizationAttribute">
        <h2 class="jh-entity-heading" data-cy="organizationAttributeDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.detail.title')"></span> {{ organizationAttribute.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.attributeName')"></span>
          </dt>
          <dd>
            <span>{{ organizationAttribute.attributeName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.attributeValue')"></span>
          </dt>
          <dd>
            <span>{{ organizationAttribute.attributeValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organizationAttribute.organization')"></span>
          </dt>
          <dd>
            <div v-if="organizationAttribute.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organizationAttribute.organization.id } }">{{
                organizationAttribute.organization.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="organizationAttribute.id"
          :to="{ name: 'OrganizationAttributeEdit', params: { organizationAttributeId: organizationAttribute.id } }"
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

<script lang="ts" src="./organization-attribute-details.component.ts"></script>
