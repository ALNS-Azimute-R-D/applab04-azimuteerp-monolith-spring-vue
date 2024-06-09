/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PersonUpdate from './person-update.vue';
import PersonService from './person.service';
import AlertService from '@/shared/alert/alert.service';

import TypeOfPersonService from '@/entities/type-of-person/type-of-person.service';
import DistrictService from '@/entities/district/district.service';

type PersonUpdateComponentType = InstanceType<typeof PersonUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const personSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PersonUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Person Management Update Component', () => {
    let comp: PersonUpdateComponentType;
    let personServiceStub: SinonStubbedInstance<PersonService>;

    beforeEach(() => {
      route = {};
      personServiceStub = sinon.createStubInstance<PersonService>(PersonService);
      personServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          personService: () => personServiceStub,
          typeOfPersonService: () =>
            sinon.createStubInstance<TypeOfPersonService>(TypeOfPersonService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          districtService: () =>
            sinon.createStubInstance<DistrictService>(DistrictService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.person = personSample;
        personServiceStub.update.resolves(personSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personServiceStub.update.calledWith(personSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        personServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.person = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        personServiceStub.find.resolves(personSample);
        personServiceStub.retrieve.resolves([personSample]);

        // WHEN
        route = {
          params: {
            personId: '' + personSample.id,
          },
        };
        const wrapper = shallowMount(PersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.person).toMatchObject(personSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        personServiceStub.find.resolves(personSample);
        const wrapper = shallowMount(PersonUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
