<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="azimuteErpSpringVueMonolith04App.eventProgram.home.createOrEditLabel"
          data-cy="EventProgramCreateUpdateHeading"
          v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="eventProgram.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="eventProgram.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.percentageExecution')"
              for="event-program-percentageExecution"
            ></label>
            <input
              type="number"
              class="form-control"
              name="percentageExecution"
              id="event-program-percentageExecution"
              data-cy="percentageExecution"
              :class="{ valid: !v$.percentageExecution.$invalid, invalid: v$.percentageExecution.$invalid }"
              v-model.number="v$.percentageExecution.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.event')"
              for="event-program-event"
            ></label>
            <select class="form-control" id="event-program-event" data-cy="event" name="event" v-model="eventProgram.event">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="eventProgram.event && eventOption.id === eventProgram.event.id ? eventProgram.event : eventOption"
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
              v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.program')"
              for="event-program-program"
            ></label>
            <select class="form-control" id="event-program-program" data-cy="program" name="program" v-model="eventProgram.program">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="eventProgram.program && programOption.id === eventProgram.program.id ? eventProgram.program : programOption"
                v-for="programOption in programs"
                :key="programOption.id"
              >
                {{ programOption.acronym }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('azimuteErpSpringVueMonolith04App.eventProgram.responsiblePerson')"
              for="event-program-responsiblePerson"
            ></label>
            <select
              class="form-control"
              id="event-program-responsiblePerson"
              data-cy="responsiblePerson"
              name="responsiblePerson"
              v-model="eventProgram.responsiblePerson"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  eventProgram.responsiblePerson && personOption.id === eventProgram.responsiblePerson.id
                    ? eventProgram.responsiblePerson
                    : personOption
                "
                v-for="personOption in people"
                :key="personOption.id"
              >
                {{ personOption.fullname }}
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
<script lang="ts" src="./event-program-update.component.ts"></script>
