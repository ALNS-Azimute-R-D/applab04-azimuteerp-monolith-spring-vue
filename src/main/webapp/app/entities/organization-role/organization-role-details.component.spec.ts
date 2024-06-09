/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationRoleDetails from './organization-role-details.vue';
import OrganizationRoleService from './organization-role.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationRoleDetailsComponentType = InstanceType<typeof OrganizationRoleDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationRoleSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OrganizationRole Management Detail Component', () => {
    let organizationRoleServiceStub: SinonStubbedInstance<OrganizationRoleService>;
    let mountOptions: MountingOptions<OrganizationRoleDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      organizationRoleServiceStub = sinon.createStubInstance<OrganizationRoleService>(OrganizationRoleService);

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
          organizationRoleService: () => organizationRoleServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationRoleServiceStub.find.resolves(organizationRoleSample);
        route = {
          params: {
            organizationRoleId: '' + 123,
          },
        };
        const wrapper = shallowMount(OrganizationRoleDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.organizationRole).toMatchObject(organizationRoleSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationRoleServiceStub.find.resolves(organizationRoleSample);
        const wrapper = shallowMount(OrganizationRoleDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
