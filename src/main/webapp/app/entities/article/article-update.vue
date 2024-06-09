<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.article.home.createOrEditLabel"
          data-cy="ArticleCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.article.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="article.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="article.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.inventoryProductId')"
              for="article-inventoryProductId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="inventoryProductId"
              id="article-inventoryProductId"
              data-cy="inventoryProductId"
              :class="{ valid: !v$.inventoryProductId.$invalid, invalid: v$.inventoryProductId.$invalid }"
              v-model.number="v$.inventoryProductId.$model"
              required
            />
            <div v-if="v$.inventoryProductId.$anyDirty && v$.inventoryProductId.$invalid">
              <small class="form-text text-danger" v-for="error of v$.inventoryProductId.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.article.skuCode')" for="article-skuCode"></label>
            <input
              type="text"
              class="form-control"
              name="skuCode"
              id="article-skuCode"
              data-cy="skuCode"
              :class="{ valid: !v$.skuCode.$invalid, invalid: v$.skuCode.$invalid }"
              v-model="v$.skuCode.$model"
            />
            <div v-if="v$.skuCode.$anyDirty && v$.skuCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.skuCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.customName')"
              for="article-customName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customName"
              id="article-customName"
              data-cy="customName"
              :class="{ valid: !v$.customName.$invalid, invalid: v$.customName.$invalid }"
              v-model="v$.customName.$model"
            />
            <div v-if="v$.customName.$anyDirty && v$.customName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.customDescription')"
              for="article-customDescription"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customDescription"
              id="article-customDescription"
              data-cy="customDescription"
              :class="{ valid: !v$.customDescription.$invalid, invalid: v$.customDescription.$invalid }"
              v-model="v$.customDescription.$model"
            />
            <div v-if="v$.customDescription.$anyDirty && v$.customDescription.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customDescription.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.priceValue')"
              for="article-priceValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="priceValue"
              id="article-priceValue"
              data-cy="priceValue"
              :class="{ valid: !v$.priceValue.$invalid, invalid: v$.priceValue.$invalid }"
              v-model.number="v$.priceValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.itemSize')"
              for="article-itemSize"
            ></label>
            <select
              class="form-control"
              name="itemSize"
              :class="{ valid: !v$.itemSize.$invalid, invalid: v$.itemSize.$invalid }"
              v-model="v$.itemSize.$model"
              id="article-itemSize"
              data-cy="itemSize"
              required
            >
              <option
                v-for="sizeOptionEnum in sizeOptionEnumValues"
                :key="sizeOptionEnum"
                v-bind:value="sizeOptionEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.SizeOptionEnum.' + sizeOptionEnum)"
              >
                {{ sizeOptionEnum }}
              </option>
            </select>
            <div v-if="v$.itemSize.$anyDirty && v$.itemSize.$invalid">
              <small class="form-text text-danger" v-for="error of v$.itemSize.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.activationStatus')"
              for="article-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="article-activationStatus"
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
            <label v-text="t$('azimuteErpSpringVueMonolith04App.article.assetCollection')" for="article-assetCollection"></label>
            <select
              class="form-control"
              id="article-assetCollections"
              data-cy="assetCollection"
              multiple
              name="assetCollection"
              v-if="article.assetCollections !== undefined"
              v-model="article.assetCollections"
            >
              <option
                v-bind:value="getSelected(article.assetCollections, assetCollectionOption, 'id')"
                v-for="assetCollectionOption in assetCollections"
                :key="assetCollectionOption.id"
              >
                {{ assetCollectionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.article.mainCategory')"
              for="article-mainCategory"
            ></label>
            <select
              class="form-control"
              id="article-mainCategory"
              data-cy="mainCategory"
              name="mainCategory"
              v-model="article.mainCategory"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="article.mainCategory && categoryOption.id === article.mainCategory.id ? article.mainCategory : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.acronym }}
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
<script lang="ts" src="./article-update.component.ts"></script>
