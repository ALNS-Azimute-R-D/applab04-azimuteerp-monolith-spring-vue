<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.invoice.home.createOrEditLabel"
          data-cy="InvoiceCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.invoice.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="invoice.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="invoice.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.businessCode')"
              for="invoice-businessCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="businessCode"
              id="invoice-businessCode"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.invoiceDate')"
              for="invoice-invoiceDate"
            ></label>
            <div class="d-flex">
              <input
                id="invoice-invoiceDate"
                data-cy="invoiceDate"
                type="datetime-local"
                class="form-control"
                name="invoiceDate"
                :class="{ valid: !v$.invoiceDate.$invalid, invalid: v$.invoiceDate.$invalid }"
                :value="convertDateTimeFromServer(v$.invoiceDate.$model)"
                @change="updateInstantField('invoiceDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.invoice.dueDate')" for="invoice-dueDate"></label>
            <div class="d-flex">
              <input
                id="invoice-dueDate"
                data-cy="dueDate"
                type="datetime-local"
                class="form-control"
                name="dueDate"
                :class="{ valid: !v$.dueDate.$invalid, invalid: v$.dueDate.$invalid }"
                :value="convertDateTimeFromServer(v$.dueDate.$model)"
                @change="updateInstantField('dueDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.description')"
              for="invoice-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="invoice-description"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.taxValue')"
              for="invoice-taxValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="taxValue"
              id="invoice-taxValue"
              data-cy="taxValue"
              :class="{ valid: !v$.taxValue.$invalid, invalid: v$.taxValue.$invalid }"
              v-model.number="v$.taxValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.shippingValue')"
              for="invoice-shippingValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="shippingValue"
              id="invoice-shippingValue"
              data-cy="shippingValue"
              :class="{ valid: !v$.shippingValue.$invalid, invalid: v$.shippingValue.$invalid }"
              v-model.number="v$.shippingValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.amountDueValue')"
              for="invoice-amountDueValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="amountDueValue"
              id="invoice-amountDueValue"
              data-cy="amountDueValue"
              :class="{ valid: !v$.amountDueValue.$invalid, invalid: v$.amountDueValue.$invalid }"
              v-model.number="v$.amountDueValue.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.numberOfInstallmentsOriginal')"
              for="invoice-numberOfInstallmentsOriginal"
            ></label>
            <input
              type="number"
              class="form-control"
              name="numberOfInstallmentsOriginal"
              id="invoice-numberOfInstallmentsOriginal"
              data-cy="numberOfInstallmentsOriginal"
              :class="{ valid: !v$.numberOfInstallmentsOriginal.$invalid, invalid: v$.numberOfInstallmentsOriginal.$invalid }"
              v-model.number="v$.numberOfInstallmentsOriginal.$model"
              required
            />
            <div v-if="v$.numberOfInstallmentsOriginal.$anyDirty && v$.numberOfInstallmentsOriginal.$invalid">
              <small class="form-text text-danger" v-for="error of v$.numberOfInstallmentsOriginal.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.numberOfInstallmentsPaid')"
              for="invoice-numberOfInstallmentsPaid"
            ></label>
            <input
              type="number"
              class="form-control"
              name="numberOfInstallmentsPaid"
              id="invoice-numberOfInstallmentsPaid"
              data-cy="numberOfInstallmentsPaid"
              :class="{ valid: !v$.numberOfInstallmentsPaid.$invalid, invalid: v$.numberOfInstallmentsPaid.$invalid }"
              v-model.number="v$.numberOfInstallmentsPaid.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.amountPaidValue')"
              for="invoice-amountPaidValue"
            ></label>
            <input
              type="number"
              class="form-control"
              name="amountPaidValue"
              id="invoice-amountPaidValue"
              data-cy="amountPaidValue"
              :class="{ valid: !v$.amountPaidValue.$invalid, invalid: v$.amountPaidValue.$invalid }"
              v-model.number="v$.amountPaidValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.invoice.status')" for="invoice-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="invoice-status"
              data-cy="status"
              required
            >
              <option
                v-for="invoiceStatusEnum in invoiceStatusEnumValues"
                :key="invoiceStatusEnum"
                v-bind:value="invoiceStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.InvoiceStatusEnum.' + invoiceStatusEnum)"
              >
                {{ invoiceStatusEnum }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.customAttributesDetailsJSON')"
              for="invoice-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="invoice-customAttributesDetailsJSON"
              data-cy="customAttributesDetailsJSON"
              :class="{ valid: !v$.customAttributesDetailsJSON.$invalid, invalid: v$.customAttributesDetailsJSON.$invalid }"
              v-model="v$.customAttributesDetailsJSON.$model"
            />
            <div v-if="v$.customAttributesDetailsJSON.$anyDirty && v$.customAttributesDetailsJSON.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customAttributesDetailsJSON.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.activationStatus')"
              for="invoice-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="invoice-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.invoice.preferrablePaymentGateway')"
              for="invoice-preferrablePaymentGateway"
            ></label>
            <select
              class="form-control"
              id="invoice-preferrablePaymentGateway"
              data-cy="preferrablePaymentGateway"
              name="preferrablePaymentGateway"
              v-model="invoice.preferrablePaymentGateway"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  invoice.preferrablePaymentGateway && paymentGatewayOption.id === invoice.preferrablePaymentGateway.id
                    ? invoice.preferrablePaymentGateway
                    : paymentGatewayOption
                "
                v-for="paymentGatewayOption in paymentGateways"
                :key="paymentGatewayOption.id"
              >
                {{ paymentGatewayOption.aliasCode }}
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
<script lang="ts" src="./invoice-update.component.ts"></script>
