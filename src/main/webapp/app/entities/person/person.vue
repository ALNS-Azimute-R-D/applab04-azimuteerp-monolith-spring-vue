<template>
  <div>
    <h2 id="page-heading" data-cy="PersonHeading">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.person.home.title')" id="person-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('azimuteErpSpringVueMonolith04App.person.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PersonCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-person"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('azimuteErpSpringVueMonolith04App.person.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && people && people.length === 0">
      <span v-text="t$('azimuteErpSpringVueMonolith04App.person.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="people && people.length > 0">
      <table class="table table-striped" aria-describedby="people">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.firstname')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.lastname')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fullname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.fullname')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('birthDate')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.birthDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'birthDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('gender')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.gender')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'gender'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('codeBI')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.codeBI')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'codeBI'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('codeNIF')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.codeNIF')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'codeNIF'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('streetAddress')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.streetAddress')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'streetAddress'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('houseNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.houseNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'houseNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('locationName')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.locationName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'locationName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('postalCode')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.postalCode')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'postalCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mainEmail')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.mainEmail')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainEmail'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('landPhoneNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.landPhoneNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'landPhoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mobilePhoneNumber')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.mobilePhoneNumber')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mobilePhoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('occupation')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.occupation')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'occupation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('preferredLanguage')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.preferredLanguage')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'preferredLanguage'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usernameInOAuth2')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.usernameInOAuth2')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usernameInOAuth2'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('userIdInOAuth2')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.userIdInOAuth2')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userIdInOAuth2'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customAttributesDetailsJSON')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.customAttributesDetailsJSON')"></span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'customAttributesDetailsJSON'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('activationStatus')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.activationStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'activationStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('avatarImg')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.avatarImg')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'avatarImg'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('typeOfPerson.code')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.typeOfPerson')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'typeOfPerson.code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.name')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.district')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('managerPerson.lastname')">
              <span v-text="t$('azimuteErpSpringVueMonolith04App.person.managerPerson')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'managerPerson.lastname'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="person in people" :key="person.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PersonView', params: { personId: person.id } }">{{ person.id }}</router-link>
            </td>
            <td>{{ person.firstname }}</td>
            <td>{{ person.lastname }}</td>
            <td>{{ person.fullname }}</td>
            <td>{{ person.birthDate }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.GenderEnum.' + person.gender)"></td>
            <td>{{ person.codeBI }}</td>
            <td>{{ person.codeNIF }}</td>
            <td>{{ person.streetAddress }}</td>
            <td>{{ person.houseNumber }}</td>
            <td>{{ person.locationName }}</td>
            <td>{{ person.postalCode }}</td>
            <td>{{ person.mainEmail }}</td>
            <td>{{ person.landPhoneNumber }}</td>
            <td>{{ person.mobilePhoneNumber }}</td>
            <td>{{ person.occupation }}</td>
            <td>{{ person.preferredLanguage }}</td>
            <td>{{ person.usernameInOAuth2 }}</td>
            <td>{{ person.userIdInOAuth2 }}</td>
            <td>{{ person.customAttributesDetailsJSON }}</td>
            <td v-text="t$('azimuteErpSpringVueMonolith04App.ActivationStatusEnum.' + person.activationStatus)"></td>
            <td>
              <a v-if="person.avatarImg" v-on:click="openFile(person.avatarImgContentType, person.avatarImg)">
                <img
                  v-bind:src="'data:' + person.avatarImgContentType + ';base64,' + person.avatarImg"
                  style="max-height: 30px"
                  alt="person"
                />
              </a>
              <span v-if="person.avatarImg">{{ person.avatarImgContentType }}, {{ byteSize(person.avatarImg) }}</span>
            </td>
            <td>
              <div v-if="person.typeOfPerson">
                <router-link :to="{ name: 'TypeOfPersonView', params: { typeOfPersonId: person.typeOfPerson.id } }">{{
                  person.typeOfPerson.code
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="person.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: person.district.id } }">{{
                  person.district.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="person.managerPerson">
                <router-link :to="{ name: 'PersonView', params: { personId: person.managerPerson.id } }">{{
                  person.managerPerson.lastname
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PersonView', params: { personId: person.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PersonEdit', params: { personId: person.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(person)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="azimuteErpSpringVueMonolith04App.person.delete.question"
          data-cy="personDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-person-heading" v-text="t$('azimuteErpSpringVueMonolith04App.person.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-person"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePerson()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="people && people.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./person.component.ts"></script>
