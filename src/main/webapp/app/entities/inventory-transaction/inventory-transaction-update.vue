<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.inventoryTransaction.home.createOrEditLabel"
          data-cy="InventoryTransactionCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="inventoryTransaction.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="inventoryTransaction.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.invoiceId')"
              for="inventory-transaction-invoiceId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="invoiceId"
              id="inventory-transaction-invoiceId"
              data-cy="invoiceId"
              :class="{ valid: !v$.invoiceId.$invalid, invalid: v$.invoiceId.$invalid }"
              v-model.number="v$.invoiceId.$model"
              required
            />
            <div v-if="v$.invoiceId.$anyDirty && v$.invoiceId.$invalid">
              <small class="form-text text-danger" v-for="error of v$.invoiceId.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionCreatedDate')"
              for="inventory-transaction-transactionCreatedDate"
            ></label>
            <div class="d-flex">
              <input
                id="inventory-transaction-transactionCreatedDate"
                data-cy="transactionCreatedDate"
                type="datetime-local"
                class="form-control"
                name="transactionCreatedDate"
                :class="{ valid: !v$.transactionCreatedDate.$invalid, invalid: v$.transactionCreatedDate.$invalid }"
                :value="convertDateTimeFromServer(v$.transactionCreatedDate.$model)"
                @change="updateInstantField('transactionCreatedDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionModifiedDate')"
              for="inventory-transaction-transactionModifiedDate"
            ></label>
            <div class="d-flex">
              <input
                id="inventory-transaction-transactionModifiedDate"
                data-cy="transactionModifiedDate"
                type="datetime-local"
                class="form-control"
                name="transactionModifiedDate"
                :class="{ valid: !v$.transactionModifiedDate.$invalid, invalid: v$.transactionModifiedDate.$invalid }"
                :value="convertDateTimeFromServer(v$.transactionModifiedDate.$model)"
                @change="updateInstantField('transactionModifiedDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.quantity')"
              for="inventory-transaction-quantity"
            ></label>
            <input
              type="number"
              class="form-control"
              name="quantity"
              id="inventory-transaction-quantity"
              data-cy="quantity"
              :class="{ valid: !v$.quantity.$invalid, invalid: v$.quantity.$invalid }"
              v-model.number="v$.quantity.$model"
              required
            />
            <div v-if="v$.quantity.$anyDirty && v$.quantity.$invalid">
              <small class="form-text text-danger" v-for="error of v$.quantity.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.transactionComments')"
              for="inventory-transaction-transactionComments"
            ></label>
            <input
              type="text"
              class="form-control"
              name="transactionComments"
              id="inventory-transaction-transactionComments"
              data-cy="transactionComments"
              :class="{ valid: !v$.transactionComments.$invalid, invalid: v$.transactionComments.$invalid }"
              v-model="v$.transactionComments.$model"
            />
            <div v-if="v$.transactionComments.$anyDirty && v$.transactionComments.$invalid">
              <small class="form-text text-danger" v-for="error of v$.transactionComments.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.activationStatus')"
              for="inventory-transaction-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="inventory-transaction-activationStatus"
              data-cy="activationStatus"
              required
            >
              <option
                v-for="activationStatusEnum in activationStatusEnumValues"
                :key="activationStatusEnum"
                v-bind:value="activationStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + activationStatusEnum)"
              >
                {{ activationStatusEnum }}
              </option>
            </select>
            <div v-if="v$.activationStatus.$anyDirty && v$.activationStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.activationStatus.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.supplier')"
              for="inventory-transaction-supplier"
            ></label>
            <select
              class="form-control"
              id="inventory-transaction-supplier"
              data-cy="supplier"
              name="supplier"
              v-model="inventoryTransaction.supplier"
              required
            >
              <option v-if="!inventoryTransaction.supplier" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  inventoryTransaction.supplier && supplierOption.id === inventoryTransaction.supplier.id
                    ? inventoryTransaction.supplier
                    : supplierOption
                "
                v-for="supplierOption in suppliers"
                :key="supplierOption.id"
              >
                {{ supplierOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.supplier.$anyDirty && v$.supplier.$invalid">
            <small class="form-text text-danger" v-for="error of v$.supplier.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.product')"
              for="inventory-transaction-product"
            ></label>
            <select
              class="form-control"
              id="inventory-transaction-product"
              data-cy="product"
              name="product"
              v-model="inventoryTransaction.product"
              required
            >
              <option v-if="!inventoryTransaction.product" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  inventoryTransaction.product && productOption.id === inventoryTransaction.product.id
                    ? inventoryTransaction.product
                    : productOption
                "
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
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.inventoryTransaction.warehouse')"
              for="inventory-transaction-warehouse"
            ></label>
            <select
              class="form-control"
              id="inventory-transaction-warehouse"
              data-cy="warehouse"
              name="warehouse"
              v-model="inventoryTransaction.warehouse"
              required
            >
              <option v-if="!inventoryTransaction.warehouse" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  inventoryTransaction.warehouse && warehouseOption.id === inventoryTransaction.warehouse.id
                    ? inventoryTransaction.warehouse
                    : warehouseOption
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
<script lang="ts" src="./inventory-transaction-update.component.ts"></script>
