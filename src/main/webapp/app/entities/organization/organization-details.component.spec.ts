/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationDetails from './organization-details.vue';
import OrganizationService from './organization.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationDetailsComponentType = InstanceType<typeof OrganizationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Organization Management Detail Component', () => {
    let organizationServiceStub: SinonStubbedInstance<OrganizationService>;
    let mountOptions: MountingOptions<OrganizationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      organizationServiceStub = sinon.createStubInstance<OrganizationService>(OrganizationService);

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
          organizationService: () => organizationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationServiceStub.find.resolves(organizationSample);
        route = {
          params: {
            organizationId: '' + 123,
          },
        };
        const wrapper = shallowMount(OrganizationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.organization).toMatchObject(organizationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationServiceStub.find.resolves(organizationSample);
        const wrapper = shallowMount(OrganizationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
