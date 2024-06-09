<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.product.home.createOrEditLabel"
          data-cy="ProductCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.product.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="product.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="product.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.productSKU')"
              for="product-productSKU"
            ></label>
            <input
              type="text"
              class="form-control"
              name="productSKU"
              id="product-productSKU"
              data-cy="productSKU"
              :class="{ valid: !v$.productSKU.$invalid, invalid: v$.productSKU.$invalid }"
              v-model="v$.productSKU.$model"
            />
            <div v-if="v$.productSKU.$anyDirty && v$.productSKU.$invalid">
              <small class="form-text text-danger" v-for="error of v$.productSKU.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.productName')"
              for="product-productName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="productName"
              id="product-productName"
              data-cy="productName"
              :class="{ valid: !v$.productName.$invalid, invalid: v$.productName.$invalid }"
              v-model="v$.productName.$model"
            />
            <div v-if="v$.productName.$anyDirty && v$.productName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.productName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.description')"
              for="product-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="product-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.standardCost')"
              for="product-standardCost"
            ></label>
            <input
              type="number"
              class="form-control"
              name="standardCost"
              id="product-standardCost"
              data-cy="standardCost"
              :class="{ valid: !v$.standardCost.$invalid, invalid: v$.standardCost.$invalid }"
              v-model.number="v$.standardCost.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.listPrice')"
              for="product-listPrice"
            ></label>
            <input
              type="number"
              class="form-control"
              name="listPrice"
              id="product-listPrice"
              data-cy="listPrice"
              :class="{ valid: !v$.listPrice.$invalid, invalid: v$.listPrice.$invalid }"
              v-model.number="v$.listPrice.$model"
              required
            />
            <div v-if="v$.listPrice.$anyDirty && v$.listPrice.$invalid">
              <small class="form-text text-danger" v-for="error of v$.listPrice.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.reorderLevel')"
              for="product-reorderLevel"
            ></label>
            <input
              type="number"
              class="form-control"
              name="reorderLevel"
              id="product-reorderLevel"
              data-cy="reorderLevel"
              :class="{ valid: !v$.reorderLevel.$invalid, invalid: v$.reorderLevel.$invalid }"
              v-model.number="v$.reorderLevel.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.targetLevel')"
              for="product-targetLevel"
            ></label>
            <input
              type="number"
              class="form-control"
              name="targetLevel"
              id="product-targetLevel"
              data-cy="targetLevel"
              :class="{ valid: !v$.targetLevel.$invalid, invalid: v$.targetLevel.$invalid }"
              v-model.number="v$.targetLevel.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.quantityPerUnit')"
              for="product-quantityPerUnit"
            ></label>
            <input
              type="text"
              class="form-control"
              name="quantityPerUnit"
              id="product-quantityPerUnit"
              data-cy="quantityPerUnit"
              :class="{ valid: !v$.quantityPerUnit.$invalid, invalid: v$.quantityPerUnit.$invalid }"
              v-model="v$.quantityPerUnit.$model"
            />
            <div v-if="v$.quantityPerUnit.$anyDirty && v$.quantityPerUnit.$invalid">
              <small class="form-text text-danger" v-for="error of v$.quantityPerUnit.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.discontinued')"
              for="product-discontinued"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="discontinued"
              id="product-discontinued"
              data-cy="discontinued"
              :class="{ valid: !v$.discontinued.$invalid, invalid: v$.discontinued.$invalid }"
              v-model="v$.discontinued.$model"
              required
            />
            <div v-if="v$.discontinued.$anyDirty && v$.discontinued.$invalid">
              <small class="form-text text-danger" v-for="error of v$.discontinued.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.minimumReorderQuantity')"
              for="product-minimumReorderQuantity"
            ></label>
            <input
              type="number"
              class="form-control"
              name="minimumReorderQuantity"
              id="product-minimumReorderQuantity"
              data-cy="minimumReorderQuantity"
              :class="{ valid: !v$.minimumReorderQuantity.$invalid, invalid: v$.minimumReorderQuantity.$invalid }"
              v-model.number="v$.minimumReorderQuantity.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.suggestedCategory')"
              for="product-suggestedCategory"
            ></label>
            <input
              type="text"
              class="form-control"
              name="suggestedCategory"
              id="product-suggestedCategory"
              data-cy="suggestedCategory"
              :class="{ valid: !v$.suggestedCategory.$invalid, invalid: v$.suggestedCategory.$invalid }"
              v-model="v$.suggestedCategory.$model"
            />
            <div v-if="v$.suggestedCategory.$anyDirty && v$.suggestedCategory.$invalid">
              <small class="form-text text-danger" v-for="error of v$.suggestedCategory.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.attachments')"
              for="product-attachments"
            ></label>
            <div>
              <div v-if="product.attachments" class="form-text text-danger clearfix">
                <a
                  class="pull-left"
                  v-on:click="openFile(product.attachmentsContentType, product.attachments)"
                  v-text="t$('entity.action.open')"
                ></a
                ><br />
                <span class="pull-left">{{ product.attachmentsContentType }}, {{ byteSize(product.attachments) }}</span>
                <button
                  type="button"
                  v-on:click="
                    product.attachments = null;
                    product.attachmentsContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_attachments" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_attachments"
                id="file_attachments"
                style="display: none"
                data-cy="attachments"
                v-on:change="setFileData($event, product, 'attachments', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="attachments"
              id="product-attachments"
              data-cy="attachments"
              :class="{ valid: !v$.attachments.$invalid, invalid: v$.attachments.$invalid }"
              v-model="v$.attachments.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="attachmentsContentType"
              id="product-attachmentsContentType"
              v-model="product.attachmentsContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.product.activationStatus')"
              for="product-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="product-activationStatus"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.product.brand')" for="product-brand"></label>
            <select class="form-control" id="product-brand" data-cy="brand" name="brand" v-model="product.brand" required>
              <option v-if="!product.brand" v-bind:value="null" selected></option>
              <option
                v-bind:value="product.brand && brandOption.id === product.brand.id ? product.brand : brandOption"
                v-for="brandOption in brands"
                :key="brandOption.id"
              >
                {{ brandOption.acronym }}
              </option>
            </select>
          </div>
          <div v-if="v$.brand.$anyDirty && v$.brand.$invalid">
            <small class="form-text text-danger" v-for="error of v$.brand.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label v-text="t$('azimuteErpSpringVueMonolith04App.product.toSupplier')" for="product-toSupplier"></label>
            <select
              class="form-control"
              id="product-toSuppliers"
              data-cy="toSupplier"
              multiple
              name="toSupplier"
              v-if="product.toSuppliers !== undefined"
              v-model="product.toSuppliers"
            >
              <option
                v-bind:value="getSelected(product.toSuppliers, supplierOption, 'id')"
                v-for="supplierOption in suppliers"
                :key="supplierOption.id"
              >
                {{ supplierOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./product-update.component.ts"></script>
