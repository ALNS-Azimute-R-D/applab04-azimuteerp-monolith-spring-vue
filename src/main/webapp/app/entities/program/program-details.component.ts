import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ProgramService from './program.service';
import { type IProgram } from '@/shared/model/program.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProgramDetails',
  setup() {
    const programService = inject('programService', () => new ProgramService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const program: Ref<IProgram> = ref({});

    const retrieveProgram = async programId => {
      try {
        const res = await programService().find(programId);
        program.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.programId) {
      retrieveProgram(route.params.programId);
    }

    return {
      alertService,
      program,

      previousState,
      t$: useI18n().t,
    };
  },
});
