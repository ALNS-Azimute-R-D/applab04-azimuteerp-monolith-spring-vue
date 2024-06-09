/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationDomainDetails from './organization-domain-details.vue';
import OrganizationDomainService from './organization-domain.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationDomainDetailsComponentType = InstanceType<typeof OrganizationDomainDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationDomainSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OrganizationDomain Management Detail Component', () => {
    let organizationDomainServiceStub: SinonStubbedInstance<OrganizationDomainService>;
    let mountOptions: MountingOptions<OrganizationDomainDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      organizationDomainServiceStub = sinon.createStubInstance<OrganizationDomainService>(OrganizationDomainService);

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
          organizationDomainService: () => organizationDomainServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationDomainServiceStub.find.resolves(organizationDomainSample);
        route = {
          params: {
            organizationDomainId: '' + 123,
          },
        };
        const wrapper = shallowMount(OrganizationDomainDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.organizationDomain).toMatchObject(organizationDomainSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationDomainServiceStub.find.resolves(organizationDomainSample);
        const wrapper = shallowMount(OrganizationDomainDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
