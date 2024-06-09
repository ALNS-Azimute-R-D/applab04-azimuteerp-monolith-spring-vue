<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.stockLevel.home.createOrEditLabel"
          data-cy="StockLevelCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="stockLevel.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="stockLevel.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.lastModifiedDate')"
              for="stock-level-lastModifiedDate"
            ></label>
            <div class="d-flex">
              <input
                id="stock-level-lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                class="form-control"
                name="lastModifiedDate"
                :class="{ valid: !v$.lastModifiedDate.$invalid, invalid: v$.lastModifiedDate.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.lastModifiedDate.$model)"
                @change="updateInstantField('lastModifiedDate', $event)"
              />
            </div>
            <div v-if="v$.lastModifiedDate.$anyDirty && v$.lastModifiedDate.$invalid">
              <small class="form-text text-danger" v-for="error of v$.lastModifiedDate.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.remainingQuantity')"
              for="stock-level-remainingQuantity"
            ></label>
            <input
              type="number"
              class="form-control"
              name="remainingQuantity"
              id="stock-level-remainingQuantity"
              data-cy="remainingQuantity"
              :class="{ valid: !v$.remainingQuantity.$invalid, invalid: v$.remainingQuantity.$invalid }"
              v-model.number="v$.remainingQuantity.$model"
              required
            />
            <div v-if="v$.remainingQuantity.$anyDirty && v$.remainingQuantity.$invalid">
              <small class="form-text text-danger" v-for="error of v$.remainingQuantity.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.commonAttributesDetailsJSON')"
              for="stock-level-commonAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="commonAttributesDetailsJSON"
              id="stock-level-commonAttributesDetailsJSON"
              data-cy="commonAttributesDetailsJSON"
              :class="{ valid: !v$.commonAttributesDetailsJSON.$invalid, invalid: v$.commonAttributesDetailsJSON.$invalid }"
              v-model="v$.commonAttributesDetailsJSON.$model"
            />
            <div v-if="v$.commonAttributesDetailsJSON.$anyDirty && v$.commonAttributesDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.commonAttributesDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.warehouse')"
              for="stock-level-warehouse"
            ></label>
            <select
              class="form-control"
              id="stock-level-warehouse"
              data-cy="warehouse"
              name="warehouse"
              v-model="stockLevel.warehouse"
              required
            >
              <option v-if="!stockLevel.warehouse" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  stockLevel.warehouse && warehouseOption.id === stockLevel.warehouse.id ? stockLevel.warehouse : warehouseOption
                "
                v-for="warehouseOption in warehouses"
                :key="warehouseOption.id"
              >
                {{ warehouseOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.warehouse.$anyDirty && v$.warehouse.$invalid">
            <small class="form-text text-danger" v-for="error of v$.warehouse.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.stockLevel.product')"
              for="stock-level-product"
            ></label>
            <select class="form-control" id="stock-level-product" data-cy="product" name="product" v-model="stockLevel.product" required>
              <option v-if="!stockLevel.product" v-bind:value="null" selected></option>
              <option
                v-bind:value="stockLevel.product && productOption.id === stockLevel.product.id ? stockLevel.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.productName }}
              </option>
            </select>
          </div>
          <div v-if="v$.product.$anyDirty && v$.product.$invalid">
            <small class="form-text text-danger" v-for="error of v$.product.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./stock-level-update.component.ts"></script>
