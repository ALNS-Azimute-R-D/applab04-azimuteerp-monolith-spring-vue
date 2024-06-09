/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfPersonDetails from './type-of-person-details.vue';
import TypeOfPersonService from './type-of-person.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfPersonDetailsComponentType = InstanceType<typeof TypeOfPersonDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfPersonSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfPerson Management Detail Component', () => {
    let typeOfPersonServiceStub: SinonStubbedInstance<TypeOfPersonService>;
    let mountOptions: MountingOptions<TypeOfPersonDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfPersonServiceStub = sinon.createStubInstance<TypeOfPersonService>(TypeOfPersonService);

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
          typeOfPersonService: () => typeOfPersonServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfPersonServiceStub.find.resolves(typeOfPersonSample);
        route = {
          params: {
            typeOfPersonId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfPersonDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfPerson).toMatchObject(typeOfPersonSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfPersonServiceStub.find.resolves(typeOfPersonSample);
        const wrapper = shallowMount(TypeOfPersonDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
