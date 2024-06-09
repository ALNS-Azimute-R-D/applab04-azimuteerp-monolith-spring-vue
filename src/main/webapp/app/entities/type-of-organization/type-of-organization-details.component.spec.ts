/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeOfOrganizationDetails from './type-of-organization-details.vue';
import TypeOfOrganizationService from './type-of-organization.service';
import AlertService from '@/shared/alert/alert.service';

type TypeOfOrganizationDetailsComponentType = InstanceType<typeof TypeOfOrganizationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeOfOrganizationSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TypeOfOrganization Management Detail Component', () => {
    let typeOfOrganizationServiceStub: SinonStubbedInstance<TypeOfOrganizationService>;
    let mountOptions: MountingOptions<TypeOfOrganizationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeOfOrganizationServiceStub = sinon.createStubInstance<TypeOfOrganizationService>(TypeOfOrganizationService);

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
          typeOfOrganizationService: () => typeOfOrganizationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeOfOrganizationServiceStub.find.resolves(typeOfOrganizationSample);
        route = {
          params: {
            typeOfOrganizationId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeOfOrganizationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.typeOfOrganization).toMatchObject(typeOfOrganizationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeOfOrganizationServiceStub.find.resolves(typeOfOrganizationSample);
        const wrapper = shallowMount(TypeOfOrganizationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
