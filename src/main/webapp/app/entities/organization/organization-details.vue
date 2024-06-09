<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="organization">
        <h2 class="jh-entity-heading" data-cy="organizationDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.detail.title')"></span> {{ organization.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.acronym')"></span>
          </dt>
          <dd>
            <span>{{ organization.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.businessCode')"></span>
          </dt>
          <dd>
            <span>{{ organization.businessCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.hierarchicalLevel')"></span>
          </dt>
          <dd>
            <span>{{ organization.hierarchicalLevel }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.name')"></span>
          </dt>
          <dd>
            <span>{{ organization.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.description')"></span>
          </dt>
          <dd>
            <span>{{ organization.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.businessHandlerClazz')"></span>
          </dt>
          <dd>
            <span>{{ organization.businessHandlerClazz }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.mainContactPersonDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ organization.mainContactPersonDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.technicalEnvironmentsDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ organization.technicalEnvironmentsDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ organization.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.organizationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.OrganizationStatusEnum.' + organization.organizationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + organization.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.logoImg')"></span>
          </dt>
          <dd>
            <div v-if="organization.logoImg">
              <a v-on:click="openFile(organization.logoImgContentType, organization.logoImg)">
                <img
                  v-bind:src="'data:' + organization.logoImgContentType + ';base64,' + organization.logoImg"
                  style="max-width: 100%"
                  alt="organization"
                />
              </a>
              {{ organization.logoImgContentType }}, {{ byteSize(organization.logoImg) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.tenant')"></span>
          </dt>
          <dd>
            <div v-if="organization.tenant">
              <router-link :to="{ name: 'TenantView', params: { tenantId: organization.tenant.id } }">{{
                organization.tenant.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.typeOfOrganization')"></span>
          </dt>
          <dd>
            <div v-if="organization.typeOfOrganization">
              <router-link :to="{ name: 'TypeOfOrganizationView', params: { typeOfOrganizationId: organization.typeOfOrganization.id } }">{{
                organization.typeOfOrganization.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.organization.organizationParent')"></span>
          </dt>
          <dd>
            <div v-if="organization.organizationParent">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: organization.organizationParent.id } }">{{
                organization.organizationParent.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="organization.id"
          :to="{ name: 'OrganizationEdit', params: { organizationId: organization.id } }"
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

<script lang="ts" src="./organization-details.component.ts"></script>
