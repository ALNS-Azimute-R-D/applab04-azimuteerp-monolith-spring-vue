<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.order.home.createOrEditLabel"
          data-cy="OrderCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.order.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="order.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="order.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.order.businessCode')"
              for="order-businessCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessCode"
              id="order-businessCode"
              data-cy="businessCode"
              :class="{ valid: !v$.businessCode.$invalid, invalid: v$.businessCode.$invalid }"
              v-model="v$.businessCode.$model"
              required
            />
            <div v-if="v$.businessCode.$anyDirty && v$.businessCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.businessCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.order.placedDate')"
              for="order-placedDate"
            ></label>
            <div class="d-flex">
              <input
                id="order-placedDate"
                data-cy="placedDate"
                type="datetime-local"
                class="form-control"
                name="placedDate"
                :class="{ valid: !v$.placedDate.$invalid, invalid: v$.placedDate.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.placedDate.$model)"
                @change="updateInstantField('placedDate', $event)"
              />
            </div>
            <div v-if="v$.placedDate.$anyDirty && v$.placedDate.$invalid">
              <small class="form-text text-danger" v-for="error of v$.placedDate.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.order.totalTaxValue')"
              for="order-totalTaxValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalTaxValue"
              id="order-totalTaxValue"
              data-cy="totalTaxValue"
              :class="{ valid: !v$.totalTaxValue.$invalid, invalid: v$.totalTaxValue.$invalid }"
              v-model.number="v$.totalTaxValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.order.totalDueValue')"
              for="order-totalDueValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="totalDueValue"
              id="order-totalDueValue"
              data-cy="totalDueValue"
              :class="{ valid: !v$.totalDueValue.$invalid, invalid: v$.totalDueValue.$invalid }"
              v-model.number="v$.totalDueValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.order.status')" for="order-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="order-status"
              data-cy="status"
              required
            >
              <option
                v-for="orderStatusEnum in orderStatusEnumValues"
                :key="orderStatusEnum"
                v-bind:value="orderStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.OrderStatusEnum.' + orderStatusEnum)"
              >
                {{ orderStatusEnum }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.order.estimatedDeliveryDate')"
              for="order-estimatedDeliveryDate"
            ></label>
            <div class="d-flex">
              <input
                id="order-estimatedDeliveryDate"
                data-cy="estimatedDeliveryDate"
                type="datetime-local"
                class="form-control"
                name="estimatedDeliveryDate"
                :class="{ valid: !v$.estimatedDeliveryDate.$invalid, invalid: v$.estimatedDeliveryDate.$invalid }"
                :value="convertDateTimeFromServer(v$.estimatedDeliveryDate.$model)"
                @change="updateInstantField('estimatedDeliveryDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.order.activationStatus')"
              for="order-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="order-activationStatus"
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
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.order.invoice')" for="order-invoice"></label>
            <select class="form-control" id="order-invoice" data-cy="invoice" name="invoice" v-model="order.invoice">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="order.invoice && invoiceOption.id === order.invoice.id ? order.invoice : invoiceOption"
                v-for="invoiceOption in invoices"
                :key="invoiceOption.id"
              >
                {{ invoiceOption.description }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.order.customer')" for="order-customer"></label>
            <select class="form-control" id="order-customer" data-cy="customer" name="customer" v-model="order.customer" required>
              <option v-if="!order.customer" v-bind:value="null" selected></option>
              <option
                v-bind:value="order.customer && customerOption.id === order.customer.id ? order.customer : customerOption"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.fullname }}
              </option>
            </select>
          </div>
          <div v-if="v$.customer.$anyDirty && v$.customer.$invalid">
            <small class="form-text text-danger" v-for="error of v$.customer.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./order-update.component.ts"></script>
