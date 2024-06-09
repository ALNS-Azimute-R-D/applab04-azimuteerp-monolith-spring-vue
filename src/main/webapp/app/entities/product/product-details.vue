<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.product.detail.title')"></span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.productSKU')"></span>
          </dt>
          <dd>
            <span>{{ product.productSKU }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.productName')"></span>
          </dt>
          <dd>
            <span>{{ product.productName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.description')"></span>
          </dt>
          <dd>
            <span>{{ product.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.standardCost')"></span>
          </dt>
          <dd>
            <span>{{ product.standardCost }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.listPrice')"></span>
          </dt>
          <dd>
            <span>{{ product.listPrice }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.reorderLevel')"></span>
          </dt>
          <dd>
            <span>{{ product.reorderLevel }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.targetLevel')"></span>
          </dt>
          <dd>
            <span>{{ product.targetLevel }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.quantityPerUnit')"></span>
          </dt>
          <dd>
            <span>{{ product.quantityPerUnit }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.discontinued')"></span>
          </dt>
          <dd>
            <span>{{ product.discontinued }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.minimumReorderQuantity')"></span>
          </dt>
          <dd>
            <span>{{ product.minimumReorderQuantity }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.suggestedCategory')"></span>
          </dt>
          <dd>
            <span>{{ product.suggestedCategory }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.attachments')"></span>
          </dt>
          <dd>
            <div v-if="product.attachments">
              <a v-on:click="openFile(product.attachmentsContentType, product.attachments)" v-text="t$('entity.action.open')"></a>
              {{ product.attachmentsContentType }}, {{ byteSize(product.attachments) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + product.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.brand')"></span>
          </dt>
          <dd>
            <div v-if="product.brand">
              <router-link :to="{ name: 'BrandView', params: { brandId: product.brand.id } }">{{ product.brand.acronym }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.toSupplier')"></span>
          </dt>
          <dd>
            <span v-for="(toSupplier, i) in product.toSuppliers" :key="toSupplier.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'SupplierView', params: { supplierId: toSupplier.id } }">{{ toSupplier.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
