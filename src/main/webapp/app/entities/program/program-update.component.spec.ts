/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProgramUpdate from './program-update.vue';
import ProgramService from './program.service';
import AlertService from '@/shared/alert/alert.service';

type ProgramUpdateComponentType = InstanceType<typeof ProgramUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const programSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ProgramUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Program Management Update Component', () => {
    let comp: ProgramUpdateComponentType;
    let programServiceStub: SinonStubbedInstance<ProgramService>;

    beforeEach(() => {
      route = {};
      programServiceStub = sinon.createStubInstance<ProgramService>(ProgramService);
      programServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          programService: () => programServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.program = programSample;
        programServiceStub.update.resolves(programSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(programServiceStub.update.calledWith(programSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        programServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.program = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(programServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        programServiceStub.find.resolves(programSample);
        programServiceStub.retrieve.resolves([programSample]);

        // WHEN
        route = {
          params: {
            programId: '' + programSample.id,
          },
        };
        const wrapper = shallowMount(ProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.program).toMatchObject(programSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        programServiceStub.find.resolves(programSample);
        const wrapper = shallowMount(ProgramUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
