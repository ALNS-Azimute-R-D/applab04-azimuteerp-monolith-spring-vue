<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.customer.home.createOrEditLabel"
          data-cy="CustomerCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.customer.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="customer.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="customer.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerBusinessCode')"
              for="customer-customerBusinessCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customerBusinessCode"
              id="customer-customerBusinessCode"
              data-cy="customerBusinessCode"
              :class="{ valid: !v$.customerBusinessCode.$invalid, invalid: v$.customerBusinessCode.$invalid }"
              v-model="v$.customerBusinessCode.$model"
              required
            />
            <div v-if="v$.customerBusinessCode.$anyDirty && v$.customerBusinessCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customerBusinessCode.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.fullname')"
              for="customer-fullname"
            ></label>
            <input
              type="text"
              class="form-control"
              name="fullname"
              id="customer-fullname"
              data-cy="fullname"
              :class="{ valid: !v$.fullname.$invalid, invalid: v$.fullname.$invalid }"
              v-model="v$.fullname.$model"
              required
            />
            <div v-if="v$.fullname.$anyDirty && v$.fullname.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fullname.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.customAttributesDetailsJSON')"
              for="customer-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="customer-customAttributesDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerStatus')"
              for="customer-customerStatus"
            ></label>
            <select
              class="form-control"
              name="customerStatus"
              :class="{ valid: !v$.customerStatus.$invalid, invalid: v$.customerStatus.$invalid }"
              v-model="v$.customerStatus.$model"
              id="customer-customerStatus"
              data-cy="customerStatus"
              required
            >
              <option
                v-for="customerStatusEnum in customerStatusEnumValues"
                :key="customerStatusEnum"
                v-bind:value="customerStatusEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.CustomerStatusEnum.' + customerStatusEnum)"
              >
                {{ customerStatusEnum }}
              </option>
            </select>
            <div v-if="v$.customerStatus.$anyDirty && v$.customerStatus.$invalid">
              <small class="form-text text-danger" v-for="error of v$.customerStatus.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.activationStatus')"
              for="customer-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="customer-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.buyerPerson')"
              for="customer-buyerPerson"
            ></label>
            <select
              class="form-control"
              id="customer-buyerPerson"
              data-cy="buyerPerson"
              name="buyerPerson"
              v-model="customer.buyerPerson"
              required
            >
              <option v-if="!customer.buyerPerson" v-bind:value="null" selected></option>
              <option
                v-bind:value="customer.buyerPerson && personOption.id === customer.buyerPerson.id ? customer.buyerPerson : personOption"
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.lastname }}
              </option>
            </select>
          </div>
          <div v-if="v$.buyerPerson.$anyDirty && v$.buyerPerson.$invalid">
            <small class="form-text text-danger" v-for="error of v$.buyerPerson.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.customerType')"
              for="customer-customerType"
            ></label>
            <select
              class="form-control"
              id="customer-customerType"
              data-cy="customerType"
              name="customerType"
              v-model="customer.customerType"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  customer.customerType && customerTypeOption.id === customer.customerType.id ? customer.customerType : customerTypeOption
                "
                v-for="customerTypeOption in customerTypes"
                :key="customerTypeOption.id"
              >
                {{ customerTypeOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.customer.district')"
              for="customer-district"
            ></label>
            <select class="form-control" id="customer-district" data-cy="district" name="district" v-model="customer.district">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="customer.district && districtOption.id === customer.district.id ? customer.district : districtOption"
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.name }}
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
<script lang="ts" src="./customer-update.component.ts"></script>
