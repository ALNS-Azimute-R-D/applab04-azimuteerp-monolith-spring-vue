<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.person.home.createOrEditLabel"
          data-cy="PersonCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.person.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="person.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="person.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.firstname')"
              for="person-firstname"
            ></label>
            <input
              type="text"
              class="form-control"
              name="firstname"
              id="person-firstname"
              data-cy="firstname"
              :class="{ valid: !v$.firstname.$invalid, invalid: v$.firstname.$invalid }"
              v-model="v$.firstname.$model"
              required
            />
            <div v-if="v$.firstname.$anyDirty && v$.firstname.$invalid">
              <small class="form-text text-danger" v-for="error of v$.firstname.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.person.lastname')" for="person-lastname"></label>
            <input
              type="text"
              class="form-control"
              name="lastname"
              id="person-lastname"
              data-cy="lastname"
              :class="{ valid: !v$.lastname.$invalid, invalid: v$.lastname.$invalid }"
              v-model="v$.lastname.$model"
              required
            />
            <div v-if="v$.lastname.$anyDirty && v$.lastname.$invalid">
              <small class="form-text text-danger" v-for="error of v$.lastname.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.person.fullname')" for="person-fullname"></label>
            <input
              type="text"
              class="form-control"
              name="fullname"
              id="person-fullname"
              data-cy="fullname"
              :class="{ valid: !v$.fullname.$invalid, invalid: v$.fullname.$invalid }"
              v-model="v$.fullname.$model"
            />
            <div v-if="v$.fullname.$anyDirty && v$.fullname.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fullname.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.birthDate')"
              for="person-birthDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="person-birthDate"
                  v-model="v$.birthDate.$model"
                  name="birthDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="person-birthDate"
                data-cy="birthDate"
                type="text"
                class="form-control"
                name="birthDate"
                :class="{ valid: !v$.birthDate.$invalid, invalid: v$.birthDate.$invalid }"
                v-model="v$.birthDate.$model"
                required
              />
            </b-input-group>
            <div v-if="v$.birthDate.$anyDirty && v$.birthDate.$invalid">
              <small class="form-text text-danger" v-for="error of v$.birthDate.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.person.gender')" for="person-gender"></label>
            <select
              class="form-control"
              name="gender"
              :class="{ valid: !v$.gender.$invalid, invalid: v$.gender.$invalid }"
              v-model="v$.gender.$model"
              id="person-gender"
              data-cy="gender"
              required
            >
              <option
                v-for="genderEnum in genderEnumValues"
                :key="genderEnum"
                v-bind:value="genderEnum"
                v-bind:label="t$('azimuteErpSpringVueMonolith04App.GenderEnum.' + genderEnum)"
              >
                {{ genderEnum }}
              </option>
            </select>
            <div v-if="v$.gender.$anyDirty && v$.gender.$invalid">
              <small class="form-text text-danger" v-for="error of v$.gender.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.person.codeBI')" for="person-codeBI"></label>
            <input
              type="text"
              class="form-control"
              name="codeBI"
              id="person-codeBI"
              data-cy="codeBI"
              :class="{ valid: !v$.codeBI.$invalid, invalid: v$.codeBI.$invalid }"
              v-model="v$.codeBI.$model"
            />
            <div v-if="v$.codeBI.$anyDirty && v$.codeBI.$invalid">
              <small class="form-text text-danger" v-for="error of v$.codeBI.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.person.codeNIF')" for="person-codeNIF"></label>
            <input
              type="text"
              class="form-control"
              name="codeNIF"
              id="person-codeNIF"
              data-cy="codeNIF"
              :class="{ valid: !v$.codeNIF.$invalid, invalid: v$.codeNIF.$invalid }"
              v-model="v$.codeNIF.$model"
            />
            <div v-if="v$.codeNIF.$anyDirty && v$.codeNIF.$invalid">
              <small class="form-text text-danger" v-for="error of v$.codeNIF.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.streetAddress')"
              for="person-streetAddress"
            ></label>
            <input
              type="text"
              class="form-control"
              name="streetAddress"
              id="person-streetAddress"
              data-cy="streetAddress"
              :class="{ valid: !v$.streetAddress.$invalid, invalid: v$.streetAddress.$invalid }"
              v-model="v$.streetAddress.$model"
              required
            />
            <div v-if="v$.streetAddress.$anyDirty && v$.streetAddress.$invalid">
              <small class="form-text text-danger" v-for="error of v$.streetAddress.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.houseNumber')"
              for="person-houseNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="houseNumber"
              id="person-houseNumber"
              data-cy="houseNumber"
              :class="{ valid: !v$.houseNumber.$invalid, invalid: v$.houseNumber.$invalid }"
              v-model="v$.houseNumber.$model"
            />
            <div v-if="v$.houseNumber.$anyDirty && v$.houseNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.houseNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.locationName')"
              for="person-locationName"
            ></label>
            <input
              type="text"
              class="form-control"
              name="locationName"
              id="person-locationName"
              data-cy="locationName"
              :class="{ valid: !v$.locationName.$invalid, invalid: v$.locationName.$invalid }"
              v-model="v$.locationName.$model"
            />
            <div v-if="v$.locationName.$anyDirty && v$.locationName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.locationName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.postalCode')"
              for="person-postalCode"
            ></label>
            <input
              type="text"
              class="form-control"
              name="postalCode"
              id="person-postalCode"
              data-cy="postalCode"
              :class="{ valid: !v$.postalCode.$invalid, invalid: v$.postalCode.$invalid }"
              v-model="v$.postalCode.$model"
              required
            />
            <div v-if="v$.postalCode.$anyDirty && v$.postalCode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.postalCode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.mainEmail')"
              for="person-mainEmail"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainEmail"
              id="person-mainEmail"
              data-cy="mainEmail"
              :class="{ valid: !v$.mainEmail.$invalid, invalid: v$.mainEmail.$invalid }"
              v-model="v$.mainEmail.$model"
              required
            />
            <div v-if="v$.mainEmail.$anyDirty && v$.mainEmail.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainEmail.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.landPhoneNumber')"
              for="person-landPhoneNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="landPhoneNumber"
              id="person-landPhoneNumber"
              data-cy="landPhoneNumber"
              :class="{ valid: !v$.landPhoneNumber.$invalid, invalid: v$.landPhoneNumber.$invalid }"
              v-model="v$.landPhoneNumber.$model"
            />
            <div v-if="v$.landPhoneNumber.$anyDirty && v$.landPhoneNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.landPhoneNumber.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.mobilePhoneNumber')"
              for="person-mobilePhoneNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mobilePhoneNumber"
              id="person-mobilePhoneNumber"
              data-cy="mobilePhoneNumber"
              :class="{ valid: !v$.mobilePhoneNumber.$invalid, invalid: v$.mobilePhoneNumber.$invalid }"
              v-model="v$.mobilePhoneNumber.$model"
            />
            <div v-if="v$.mobilePhoneNumber.$anyDirty && v$.mobilePhoneNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mobilePhoneNumber.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.occupation')"
              for="person-occupation"
            ></label>
            <input
              type="text"
              class="form-control"
              name="occupation"
              id="person-occupation"
              data-cy="occupation"
              :class="{ valid: !v$.occupation.$invalid, invalid: v$.occupation.$invalid }"
              v-model="v$.occupation.$model"
            />
            <div v-if="v$.occupation.$anyDirty && v$.occupation.$invalid">
              <small class="form-text text-danger" v-for="error of v$.occupation.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.preferredLanguage')"
              for="person-preferredLanguage"
            ></label>
            <input
              type="text"
              class="form-control"
              name="preferredLanguage"
              id="person-preferredLanguage"
              data-cy="preferredLanguage"
              :class="{ valid: !v$.preferredLanguage.$invalid, invalid: v$.preferredLanguage.$invalid }"
              v-model="v$.preferredLanguage.$model"
            />
            <div v-if="v$.preferredLanguage.$anyDirty && v$.preferredLanguage.$invalid">
              <small class="form-text text-danger" v-for="error of v$.preferredLanguage.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.usernameInOAuth2')"
              for="person-usernameInOAuth2"
            ></label>
            <input
              type="text"
              class="form-control"
              name="usernameInOAuth2"
              id="person-usernameInOAuth2"
              data-cy="usernameInOAuth2"
              :class="{ valid: !v$.usernameInOAuth2.$invalid, invalid: v$.usernameInOAuth2.$invalid }"
              v-model="v$.usernameInOAuth2.$model"
            />
            <div v-if="v$.usernameInOAuth2.$anyDirty && v$.usernameInOAuth2.$invalid">
              <small class="form-text text-danger" v-for="error of v$.usernameInOAuth2.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.userIdInOAuth2')"
              for="person-userIdInOAuth2"
            ></label>
            <input
              type="text"
              class="form-control"
              name="userIdInOAuth2"
              id="person-userIdInOAuth2"
              data-cy="userIdInOAuth2"
              :class="{ valid: !v$.userIdInOAuth2.$invalid, invalid: v$.userIdInOAuth2.$invalid }"
              v-model="v$.userIdInOAuth2.$model"
            />
            <div v-if="v$.userIdInOAuth2.$anyDirty && v$.userIdInOAuth2.$invalid">
              <small class="form-text text-danger" v-for="error of v$.userIdInOAuth2.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.customAttributesDetailsJSON')"
              for="person-customAttributesDetailsJSON"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customAttributesDetailsJSON"
              id="person-customAttributesDetailsJSON"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.person.activationStatus')"
              for="person-activationStatus"
            ></label>
            <select
              class="form-control"
              name="activationStatus"
              :class="{ valid: !v$.activationStatus.$invalid, invalid: v$.activationStatus.$invalid }"
              v-model="v$.activationStatus.$model"
              id="person-activationStatus"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.person.avatarImg')"
              for="person-avatarImg"
            ></label>
            <div>
              <img
                v-bind:src="'data:' + person.avatarImgContentType + ';base64,' + person.avatarImg"
                style="max-height: 100px"
                v-if="person.avatarImg"
                alt="person"
              />
              <div v-if="person.avatarImg" class="form-text text-danger clearfix">
                <span class="pull-left">{{ person.avatarImgContentType }}, {{ byteSize(person.avatarImg) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('avatarImg', 'avatarImgContentType', 'file_avatarImg')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_avatarImg" v-text="t$('entity.action.addimage')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_avatarImg"
                id="file_avatarImg"
                style="display: none"
                data-cy="avatarImg"
                v-on:change="setFileData($event, person, 'avatarImg', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="avatarImg"
              id="person-avatarImg"
              data-cy="avatarImg"
              :class="{ valid: !v$.avatarImg.$invalid, invalid: v$.avatarImg.$invalid }"
              v-model="v$.avatarImg.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="avatarImgContentType"
              id="person-avatarImgContentType"
              v-model="person.avatarImgContentType"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.typeOfPerson')"
              for="person-typeOfPerson"
            ></label>
            <select
              class="form-control"
              id="person-typeOfPerson"
              data-cy="typeOfPerson"
              name="typeOfPerson"
              v-model="person.typeOfPerson"
              required
            >
              <option v-if="!person.typeOfPerson" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  person.typeOfPerson && typeOfPersonOption.id === person.typeOfPerson.id ? person.typeOfPerson : typeOfPersonOption
                "
                v-for="typeOfPersonOption in typeOfPeople"
                :key="typeOfPersonOption.id"
              >
                {{ typeOfPersonOption.code }}
              </option>
            </select>
          </div>
          <div v-if="v$.typeOfPerson.$anyDirty && v$.typeOfPerson.$invalid">
            <small class="form-text text-danger" v-for="error of v$.typeOfPerson.$errors" :key="error.$uid">{{ error.$message }}</small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('azimuteErpSpringVueMonolith04App.person.district')" for="person-district"></label>
            <select class="form-control" id="person-district" data-cy="district" name="district" v-model="person.district">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="person.district && districtOption.id === person.district.id ? person.district : districtOption"
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.person.managerPerson')"
              for="person-managerPerson"
            ></label>
            <select
              class="form-control"
              id="person-managerPerson"
              data-cy="managerPerson"
              name="managerPerson"
              v-model="person.managerPerson"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="person.managerPerson && personOption.id === person.managerPerson.id ? person.managerPerson : personOption"
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.lastname }}
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
<script lang="ts" src="./person-update.component.ts"></script>
