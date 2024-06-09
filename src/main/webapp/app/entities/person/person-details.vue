<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="person">
        <h2 class="jh-entity-heading" data-cy="personDetailsHeading">
          <span v-text="t$('azimuteErpSpringVueMonolith04App.person.detail.title')"></span> {{ person.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.firstname')"></span>
          </dt>
          <dd>
            <span>{{ person.firstname }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.lastname')"></span>
          </dt>
          <dd>
            <span>{{ person.lastname }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.fullname')"></span>
          </dt>
          <dd>
            <span>{{ person.fullname }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.birthDate')"></span>
          </dt>
          <dd>
            <span>{{ person.birthDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.gender')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.GenderEnum.' + person.gender)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.codeBI')"></span>
          </dt>
          <dd>
            <span>{{ person.codeBI }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.codeNIF')"></span>
          </dt>
          <dd>
            <span>{{ person.codeNIF }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.streetAddress')"></span>
          </dt>
          <dd>
            <span>{{ person.streetAddress }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.houseNumber')"></span>
          </dt>
          <dd>
            <span>{{ person.houseNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.locationName')"></span>
          </dt>
          <dd>
            <span>{{ person.locationName }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.postalCode')"></span>
          </dt>
          <dd>
            <span>{{ person.postalCode }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.mainEmail')"></span>
          </dt>
          <dd>
            <span>{{ person.mainEmail }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.landPhoneNumber')"></span>
          </dt>
          <dd>
            <span>{{ person.landPhoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.mobilePhoneNumber')"></span>
          </dt>
          <dd>
            <span>{{ person.mobilePhoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.occupation')"></span>
          </dt>
          <dd>
            <span>{{ person.occupation }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.preferredLanguage')"></span>
          </dt>
          <dd>
            <span>{{ person.preferredLanguage }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.usernameInOAuth2')"></span>
          </dt>
          <dd>
            <span>{{ person.usernameInOAuth2 }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.userIdInOAuth2')"></span>
          </dt>
          <dd>
            <span>{{ person.userIdInOAuth2 }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.customAttributesDetailsJSON')"></span>
          </dt>
          <dd>
            <span>{{ person.customAttributesDetailsJSON }}</span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.activationStatus')"></span>
          </dt>
          <dd>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + person.activationStatus)"></span>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.avatarImg')"></span>
          </dt>
          <dd>
            <div v-if="person.avatarImg">
              <a v-on:click="openFile(person.avatarImgContentType, person.avatarImg)">
                <img
                  v-bind:src="'data:' + person.avatarImgContentType + ';base64,' + person.avatarImg"
                  style="max-width: 100%"
                  alt="person"
                />
              </a>
              {{ person.avatarImgContentType }}, {{ byteSize(person.avatarImg) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.typeOfPerson')"></span>
          </dt>
          <dd>
            <div v-if="person.typeOfPerson">
              <router-link :to="{ name: 'TypeOfPersonView', params: { typeOfPersonId: person.typeOfPerson.id } }">{{
                person.typeOfPerson.code
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.district')"></span>
          </dt>
          <dd>
            <div v-if="person.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: person.district.id } }">{{
                person.district.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.managerPerson')"></span>
          </dt>
          <dd>
            <div v-if="person.managerPerson">
              <router-link :to="{ name: 'PersonView', params: { personId: person.managerPerson.id } }">{{
                person.managerPerson.lastname
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="person.id" :to="{ name: 'PersonEdit', params: { personId: person.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./person-details.component.ts"></script>
