<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.paymentGateway.home.createOrEditLabel"
          data-cy="PaymentGatewayCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="paymentGateway.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="paymentGateway.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.aliasCode')"
              for="payment-gateway-aliasCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="aliasCode"
              id="payment-gateway-aliasCode"
              data-cy="aliasCode"
              :class="{ valid: !v$.aliasCode.$invalid, invalid: v$.aliasCode.$invalid }"
              v-model="v$.aliasCode.$model"
              required
            />
            <div v-if="v$.aliasCode.$anyDirty && v$.aliasCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.aliasCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.description')"
              for="payment-gateway-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="payment-gateway-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
              required
            />
            <div v-if="v$.description.$anyDirty && v$.description.$invalid">
              <small class="form-text text-danger" v-for="error of v$.description.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.businessHandlerClazz')"
              for="payment-gateway-businessHandlerClazz"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessHandlerClazz"
              id="payment-gateway-businessHandlerClazz"
              data-cy="businessHandlerClazz"
              :class="{ valid: !v$.businessHandlerClazz.$invalid, invalid: v$.businessHandlerClazz.$invalid }"
              v-model="v$.businessHandlerClazz.$model"
            />
            <div v-if="v$.businessHandlerClazz.$anyDirty && v$.businessHandlerClazz.$invalid">
              <small class="form-text text-danger" v-for="error of v$.businessHandlerClazz.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.paymentGateway.activationStatus')"
              for="payment-gateway-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="payment-gateway-activationStatus"
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
<script lang="ts" src="./payment-gateway-update.component.ts"></script>
