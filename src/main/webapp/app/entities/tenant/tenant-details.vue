<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tenant">
        <h2 class="jh-entity-heading" data-cy="tenantDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.detail.title')"></span> {{ tenant.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.acronym')"></span>
          </dt>
          <dd>
            <span>{{ tenant.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.name')"></span>
          </dt>
          <dd>
            <span>{{ tenant.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.description')"></span>
          </dt>
          <dd>
            <span>{{ tenant.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.customerBusinessCode')"></span>
          </dt>
          <dd>
            <span>{{ tenant.customerBusinessCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.businessHandlerClazz')"></span>
          </dt>
          <dd>
            <span>{{ tenant.businessHandlerClazz }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.mainContactPersonDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ tenant.mainContactPersonDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.billingDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ tenant.billingDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.technicalEnvironmentsDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ tenant.technicalEnvironmentsDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ tenant.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.logoImg')"></span>
          </dt>
          <dd>
            <div v-if="tenant.logoImg">
              <a v-on:click="openFile(tenant.logoImgContentType, tenant.logoImg)">
                <img v-bind:src="'data:' + tenant.logoImgContentType + ';base64,' + tenant.logoImg" style="max-width: 100%" alt="tenant" />
              </a>
              {{ tenant.logoImgContentType }}, {{ byteSize(tenant.logoImg) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.tenant.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + tenant.activationStatus)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="tenant.id" :to="{ name: 'TenantEdit', params: { tenantId: tenant.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tenant-details.component.ts"></script>
