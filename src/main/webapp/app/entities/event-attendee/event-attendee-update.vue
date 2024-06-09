<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.eventAttendee.home.createOrEditLabel"
          data-cy="EventAttendeeCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="eventAttendee.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="eventAttendee.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.attendedAsRole')"
              for="event-attendee-attendedAsRole"
            ></label>
            <input
              type="text"
              class="form-control"
              name="attendedAsRole"
              id="event-attendee-attendedAsRole"
              data-cy="attendedAsRole"
              :class="{ valid: !v$.attendedAsRole.$invalid, invalid: v$.attendedAsRole.$invalid }"
              v-model="v$.attendedAsRole.$model"
              required
            />
            <div v-if="v$.attendedAsRole.$anyDirty && v$.attendedAsRole.$invalid">
              <small class="form-text text-danger" v-for="error of v$.attendedAsRole.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.wasPresentInEvent')"
              for="event-attendee-wasPresentInEvent"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="wasPresentInEvent"
              id="event-attendee-wasPresentInEvent"
              data-cy="wasPresentInEvent"
              :class="{ valid: !v$.wasPresentInEvent.$invalid, invalid: v$.wasPresentInEvent.$invalid }"
              v-model="v$.wasPresentInEvent.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.shouldBuyTicket')"
              for="event-attendee-shouldBuyTicket"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="shouldBuyTicket"
              id="event-attendee-shouldBuyTicket"
              data-cy="shouldBuyTicket"
              :class="{ valid: !v$.shouldBuyTicket.$invalid, invalid: v$.shouldBuyTicket.$invalid }"
              v-model="v$.shouldBuyTicket.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.ticketNumber')"
              for="event-attendee-ticketNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="ticketNumber"
              id="event-attendee-ticketNumber"
              data-cy="ticketNumber"
              :class="{ valid: !v$.ticketNumber.$invalid, invalid: v$.ticketNumber.$invalid }"
              v-model="v$.ticketNumber.$model"
            />
            <div v-if="v$.ticketNumber.$anyDirty && v$.ticketNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.ticketNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.seatNumber')"
              for="event-attendee-seatNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="seatNumber"
              id="event-attendee-seatNumber"
              data-cy="seatNumber"
              :class="{ valid: !v$.seatNumber.$invalid, invalid: v$.seatNumber.$invalid }"
              v-model="v$.seatNumber.$model"
            />
            <div v-if="v$.seatNumber.$anyDirty && v$.seatNumber.$invalid">
              <small class="form-text text-danger" v-for="error of v$.seatNumber.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.attendeePerson')"
              for="event-attendee-attendeePerson"
            ></label>
            <select
              class="form-control"
              id="event-attendee-attendeePerson"
              data-cy="attendeePerson"
              name="attendeePerson"
              v-model="eventAttendee.attendeePerson"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  eventAttendee.attendeePerson && personOption.id === eventAttendee.attendeePerson.id
                    ? eventAttendee.attendeePerson
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.fullname }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.event')"
              for="event-attendee-event"
            ></label>
            <select class="form-control" id="event-attendee-event" data-cy="event" name="event" v-model="eventAttendee.event">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="eventAttendee.event && eventOption.id === eventAttendee.event.id ? eventAttendee.event : eventOption"
                v-for="eventOption in events"
                :key="eventOption.id"
              >
                {{ eventOption.acronym }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventAttendee.ticketPurchased')"
              for="event-attendee-ticketPurchased"
            ></label>
            <select
              class="form-control"
              id="event-attendee-ticketPurchased"
              data-cy="ticketPurchased"
              name="ticketPurchased"
              v-model="eventAttendee.ticketPurchased"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  eventAttendee.ticketPurchased && ticketPurchasedOption.id === eventAttendee.ticketPurchased.id
                    ? eventAttendee.ticketPurchased
                    : ticketPurchasedOption
                "
                v-for="ticketPurchasedOption in ticketPurchaseds"
                :key="ticketPurchasedOption.id"
              >
                {{ ticketPurchasedOption.buyingCode }}
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
<script lang="ts" src="./event-attendee-update.component.ts"></script>
