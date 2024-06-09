/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProgramDetails from './program-details.vue';
import ProgramService from './program.service';
import AlertService from '@/shared/alert/alert.service';

type ProgramDetailsComponentType = InstanceType<typeof ProgramDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const programSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Program Management Detail Component', () => {
    let programServiceStub: SinonStubbedInstance<ProgramService>;
    let mountOptions: MountingOptions<ProgramDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      programServiceStub = sinon.createStubInstance<ProgramService>(ProgramService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          programService: () => programServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        programServiceStub.find.resolves(programSample);
        route = {
          params: {
            programId: '' + 123,
          },
        };
        const wrapper = shallowMount(ProgramDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.program).toMatchObject(programSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        programServiceStub.find.resolves(programSample);
        const wrapper = shallowMount(ProgramDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
