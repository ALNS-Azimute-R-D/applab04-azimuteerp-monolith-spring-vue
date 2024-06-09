/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationMembershipDetails from './organization-membership-details.vue';
import OrganizationMembershipService from './organization-membership.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationMembershipDetailsComponentType = InstanceType<typeof OrganizationMembershipDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationMembershipSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OrganizationMembership Management Detail Component', () => {
    let organizationMembershipServiceStub: SinonStubbedInstance<OrganizationMembershipService>;
    let mountOptions: MountingOptions<OrganizationMembershipDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      organizationMembershipServiceStub = sinon.createStubInstance<OrganizationMembershipService>(OrganizationMembershipService);

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
          organizationMembershipService: () => organizationMembershipServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationMembershipServiceStub.find.resolves(organizationMembershipSample);
        route = {
          params: {
            organizationMembershipId: '' + 123,
          },
        };
        const wrapper = shallowMount(OrganizationMembershipDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.organizationMembership).toMatchObject(organizationMembershipSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationMembershipServiceStub.find.resolves(organizationMembershipSample);
        const wrapper = shallowMount(OrganizationMembershipDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
