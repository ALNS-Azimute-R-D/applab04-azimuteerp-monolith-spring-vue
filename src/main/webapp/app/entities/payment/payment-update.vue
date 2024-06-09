<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.payment.home.createOrEditLabel"
          data-cy="PaymentCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.payment.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="payment.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="payment.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.installmentNumber')"
              for="payment-installmentNumber"
            ></label>
            <input
              type="number"
              class="form-control"
              name="installmentNumber"
              id="payment-installmentNumber"
              data-cy="installmentNumber"
              :class="{ valid: !v$.installmentNumber.$invalid, invalid: v$.installmentNumber.$invalid }"
              v-model.number="v$.installmentNumber.$model"
              required
            />
            <div v-if="v$.installmentNumber.$anyDirty && v$.installmentNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.installmentNumber.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentDueDate')"
              for="payment-paymentDueDate"
            ></label>
            <div class="d-flex">
              <input
                id="payment-paymentDueDate"
                data-cy="paymentDueDate"
                type="datetime-local"
                class="form-control"
                name="paymentDueDate"
                :class="{ valid: !v$.paymentDueDate.$invalid, invalid: v$.paymentDueDate.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.paymentDueDate.$model)"
                @change="updateInstantField('paymentDueDate', $event)"
              />
            </div>
            <div v-if="v$.paymentDueDate.$anyDirty && v$.paymentDueDate.$invalid">
              <small class="form-text text-danger" v-for="error of v$.paymentDueDate.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentPaidDate')"
              for="payment-paymentPaidDate"
            ></label>
            <div class="d-flex">
              <input
                id="payment-paymentPaidDate"
                data-cy="paymentPaidDate"
                type="datetime-local"
                class="form-control"
                name="paymentPaidDate"
                :class="{ valid: !v$.paymentPaidDate.$invalid, invalid: v$.paymentPaidDate.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.paymentPaidDate.$model)"
                @change="updateInstantField('paymentPaidDate', $event)"
              />
            </div>
            <div v-if="v$.paymentPaidDate.$anyDirty && v$.paymentPaidDate.$invalid">
              <small class="form-text text-danger" v-for="error of v$.paymentPaidDate.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentAmount')"
              for="payment-paymentAmount"
            ></label>
            <input
              type="number"
              class="form-control"
              name="paymentAmount"
              id="payment-paymentAmount"
              data-cy="paymentAmount"
              :class="{ valid: !v$.paymentAmount.$invalid, invalid: v$.paymentAmount.$invalid }"
              v-model.number="v$.paymentAmount.$model"
              required
            />
            <div v-if="v$.paymentAmount.$anyDirty && v$.paymentAmount.$invalid">
              <small class="form-text text-danger" v-for="error of v$.paymentAmount.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.typeOfPayment')"
              for="payment-typeOfPayment"
            ></label>
            <select
              class="form-control"
              name="typeOfPayment"
              :class="{ valid: !v$.typeOfPayment.$invalid, invalid: v$.typeOfPayment.$invalid }"
              v-model="v$.typeOfPayment.$model"
              id="payment-typeOfPayment"
              data-cy="typeOfPayment"
              required
            >
              <option
                v-for="paymentTypeEnum in paymentTypeEnumValues"
                :key="paymentTypeEnum"
                v-bind:value="paymentTypeEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.PaymentTypeEnum.' + paymentTypeEnum)"
              >
                {{ paymentTypeEnum }}
              </option>
            </select>
            <div v-if="v$.typeOfPayment.$anyDirty && v$.typeOfPayment.$invalid">
              <small class="form-text text-danger" v-for="error of v$.typeOfPayment.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.statusPayment')"
              for="payment-statusPayment"
            ></label>
            <select
              class="form-control"
              name="statusPayment"
              :class="{ valid: !v$.statusPayment.$invalid, invalid: v$.statusPayment.$invalid }"
              v-model="v$.statusPayment.$model"
              id="payment-statusPayment"
              data-cy="statusPayment"
              required
            >
              <option
                v-for="paymentStatusEnum in paymentStatusEnumValues"
                :key="paymentStatusEnum"
                v-bind:value="paymentStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.PaymentStatusEnum.' + paymentStatusEnum)"
              >
                {{ paymentStatusEnum }}
              </option>
            </select>
            <div v-if="v$.statusPayment.$anyDirty && v$.statusPayment.$invalid">
              <small class="form-text text-danger" v-for="error of v$.statusPayment.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.customAttributesDetailsJSON')"
              for="payment-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="payment-customAttributesDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.activationStatus')"
              for="payment-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="payment-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.payment.paymentGateway')"
              for="payment-paymentGateway"
            ></label>
            <select
              class="form-control"
              id="payment-paymentGateway"
              data-cy="paymentGateway"
              name="paymentGateway"
              v-model="payment.paymentGateway"
              required
            >
              <option v-if="!payment.paymentGateway" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  payment.paymentGateway && paymentGatewayOption.id === payment.paymentGateway.id
                    ? payment.paymentGateway
                    : paymentGatewayOption
                "
                v-for="paymentGatewayOption in paymentGateways"
                :key="paymentGatewayOption.id"
              >
                {{ paymentGatewayOption.aliasCode }}
              </option>
            </select>
          </div>
          <div v-if="v$.paymentGateway.$anyDirty && v$.paymentGateway.$invalid">
            <small class="form-text text-danger" v-for="error of v$.paymentGateway.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./payment-update.component.ts"></script>
