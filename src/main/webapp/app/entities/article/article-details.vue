<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="article">
        <h2 class="jh-entity-heading" data-cy="articleDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.article.detail.title')"></span> {{ article.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.inventoryProductId')"></span>
          </dt>
          <dd>
            <span>{{ article.inventoryProductId }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.skuCode')"></span>
          </dt>
          <dd>
            <span>{{ article.skuCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.customName')"></span>
          </dt>
          <dd>
            <span>{{ article.customName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.customDescription')"></span>
          </dt>
          <dd>
            <span>{{ article.customDescription }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.priceValue')"></span>
          </dt>
          <dd>
            <span>{{ article.priceValue }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.itemSize')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.SizeOptionEnum.' + article.itemSize)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + article.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.assetCollection')"></span>
          </dt>
          <dd>
            <span v-for="(assetCollection, i) in article.assetCollections" :key="assetCollection.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'AssetCollectionView', params: { assetCollectionId: assetCollection.id } }">{{
                assetCollection.id
              }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.article.mainCategory')"></span>
          </dt>
          <dd>
            <div v-if="article.mainCategory">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: article.mainCategory.id } }">{{
                article.mainCategory.acronym
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="article.id" :to="{ name: 'ArticleEdit', params: { articleId: article.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./article-details.component.ts"></script>
