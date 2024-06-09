<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="assetType">
        <h2 class="jh-entity-heading" data-cy="assetTypeDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.assetType.detail.title')"></span> {{ assetType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetType.acronym')"></span>
          </dt>
          <dd>
            <span>{{ assetType.acronym }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetType.name')"></span>
          </dt>
          <dd>
            <span>{{ assetType.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetType.description')"></span>
          </dt>
          <dd>
            <span>{{ assetType.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetType.handlerClazzName')"></span>
          </dt>
          <dd>
            <span>{{ assetType.handlerClazzName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.assetType.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ assetType.customAttributesDetailsJSON }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="assetType.id"
          :to="{ name: 'AssetTypeEdit', params: { assetTypeId: assetType.id } }"
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

<script lang="ts" src="./asset-type-details.component.ts"></script>
