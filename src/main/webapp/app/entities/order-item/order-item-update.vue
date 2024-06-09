<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.orderItem.home.createOrEditLabel"
          data-cy="OrderItemCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="orderItem.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="orderItem.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.quantity')"
              for="order-item-quantity"
            ></label>
            <input
              type="number"
              class="form-control"
              name="quantity"
              id="order-item-quantity"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.totalPrice')"
              for="order-item-totalPrice"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalPrice"
              id="order-item-totalPrice"
              data-cy="totalPrice"
              :class="{ valid: !v$.totalPrice.$invalid, invalid: v$.totalPrice.$invalid }"
              v-model.number="v$.totalPrice.$model"
              required
            />
            <div v-if="v$.totalPrice.$anyDirty && v$.totalPrice.$invalid">
              <small class="form-text text-danger" v-for="error of v$.totalPrice.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.status')"
              for="order-item-status"
            ></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="order-item-status"
              data-cy="status"
              required
            >
              <option
                v-for="orderItemStatusEnum in orderItemStatusEnumValues"
                :key="orderItemStatusEnum"
                v-bind:value="orderItemStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.OrderItemStatusEnum.' + orderItemStatusEnum)"
              >
                {{ orderItemStatusEnum }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.article')"
              for="order-item-article"
            ></label>
            <select class="form-control" id="order-item-article" data-cy="article" name="article" v-model="orderItem.article" required>
              <option v-if="!orderItem.article" v-bind:value="null" selected></option>
              <option
                v-bind:value="orderItem.article && articleOption.id === orderItem.article.id ? orderItem.article : articleOption"
                v-for="articleOption in articles"
                :key="articleOption.id"
              >
                {{ articleOption.customName }}
              </option>
            </select>
          </div>
          <div v-if="v$.article.$anyDirty && v$.article.$invalid">
            <small class="form-text text-danger" v-for="error of v$.article.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.orderItem.order')"
              for="order-item-order"
            ></label>
            <select class="form-control" id="order-item-order" data-cy="order" name="order" v-model="orderItem.order" required>
              <option v-if="!orderItem.order" v-bind:value="null" selected></option>
              <option
                v-bind:value="orderItem.order && orderOption.id === orderItem.order.id ? orderItem.order : orderOption"
                v-for="orderOption in orders"
                :key="orderOption.id"
              >
                {{ orderOption.businessCode }}
              </option>
            </select>
          </div>
          <div v-if="v$.order.$anyDirty && v$.order.$invalid">
            <small class="form-text text-danger" v-for="error of v$.order.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./order-item-update.component.ts"></script>
