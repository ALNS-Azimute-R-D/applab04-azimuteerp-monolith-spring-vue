<template>
  <div>
    <h2 id="page-heading" data-cy="ProductHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.product.home.title')" id="product-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.product.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ProductCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-product"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.product.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && products && products.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.product.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="products && products.length > 0">
      <table class="table table-striped" aria-describedby="products">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('productSKU')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.productSKU')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'productSKU'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('productName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.productName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'productName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('standardCost')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.standardCost')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'standardCost'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('listPrice')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.listPrice')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'listPrice'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('reorderLevel')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.reorderLevel')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'reorderLevel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('targetLevel')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.targetLevel')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'targetLevel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('quantityPerUnit')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.quantityPerUnit')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantityPerUnit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('discontinued')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.discontinued')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'discontinued'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('minimumReorderQuantity')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.minimumReorderQuantity')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'minimumReorderQuantity'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('suggestedCategory')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.suggestedCategory')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'suggestedCategory'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attachments')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.attachments')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attachments'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('brand.acronym')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.product.brand')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'brand.acronym'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProductView', params: { productId: product.id } }">{{ product.id }}</router-link>
            </td>
            <td>{{ product.productSKU }}</td>
            <td>{{ product.productName }}</td>
            <td>{{ product.description }}</td>
            <td>{{ product.standardCost }}</td>
            <td>{{ product.listPrice }}</td>
            <td>{{ product.reorderLevel }}</td>
            <td>{{ product.targetLevel }}</td>
            <td>{{ product.quantityPerUnit }}</td>
            <td>{{ product.discontinued }}</td>
            <td>{{ product.minimumReorderQuantity }}</td>
            <td>{{ product.suggestedCategory }}</td>
            <td>
              <a
                v-if="product.attachments"
                v-on:click="openFile(product.attachmentsContentType, product.attachments)"
                v-text="t$('entity.action.open')"
              ></a>
              <span v-if="product.attachments">{{ product.attachmentsContentType }}, {{ byteSize(product.attachments) }}</span>
            </td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + product.activationStatus)"></td>
            <td>
              <div v-if="product.brand">
                <router-link :to="{ name: 'BrandView', params: { brandId: product.brand.id } }">{{ product.brand.acronym }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProductView', params: { productId: product.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(product)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="azimuteErpSpringVueMonolith04App.product.delete.question"
          data-cy="productDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-product-heading" v-text="t$('azimuteErpSpringVueMonolith04App.product.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-product"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeProduct()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="products && products.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product.component.ts"></script>
