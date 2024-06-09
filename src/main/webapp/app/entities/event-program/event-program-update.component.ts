import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EventProgramService from './event-program.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EventService from '@/entities/event/event.service';
import { type IEvent } from '@/shared/model/event.model';
import ProgramService from '@/entities/program/program.service';
import { type IProgram } from '@/shared/model/program.model';
import PersonService from '@/entities/person/person.service';
import { type IPerson } from '@/shared/model/person.model';
import { type IEventProgram, EventProgram } from '@/shared/model/event-program.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EventProgramUpdate',
  setup() {
    const eventProgramService = inject('eventProgramService', () => new EventProgramService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const eventProgram: Ref<IEventProgram> = ref(new EventProgram());

    const eventService = inject('eventService', () => new EventService());

    const events: Ref<IEvent[]> = ref([]);

    const programService = inject('programService', () => new ProgramService());

    const programs: Ref<IProgram[]> = ref([]);

    const personService = inject('personService', () => new PersonService());

    const people: Ref<IPerson[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEventProgram = async eventProgramId => {
      try {
        const res = await eventProgramService().find(eventProgramId);
        eventProgram.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.eventProgramId) {
      retrieveEventProgram(route.params.eventProgramId);
    }

    const initRelationships = () => {
      eventService()
        .retrieve()
        .then(res => {
          events.value = res.data;
        });
      programService()
        .retrieve()
        .then(res => {
          programs.value = res.data;
        });
      personService()
        .retrieve()
        .then(res => {
          people.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      percentageExecution: {},
      event: {},
      program: {},
      responsiblePerson: {},
    };
    const v$ = useVuelidate(validationRules, eventProgram as any);
    v$.value.$validate();

    return {
      eventProgramService,
      alertService,
      eventProgram,
      previousState,
      isSaving,
      currentLanguage,
      events,
      programs,
      people,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.eventProgram.id) {
        this.eventProgramService()
          .update(this.eventProgram)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('azimuteErpSpringVueMonolith04App.eventProgram.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.eventProgramService()
          .create(this.eventProgram)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('azimuteErpSpringVueMonolith04App.eventProgram.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
