/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationAttributeDetails from './organization-attribute-details.vue';
import OrganizationAttributeService from './organization-attribute.service';
import AlertService from '@/shared/alert/alert.service';

type OrganizationAttributeDetailsComponentType = InstanceType<typeof OrganizationAttributeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationAttributeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OrganizationAttribute Management Detail Component', () => {
    let organizationAttributeServiceStub: SinonStubbedInstance<OrganizationAttributeService>;
    let mountOptions: MountingOptions<OrganizationAttributeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      organizationAttributeServiceStub = sinon.createStubInstance<OrganizationAttributeService>(OrganizationAttributeService);

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
          organizationAttributeService: () => organizationAttributeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        organizationAttributeServiceStub.find.resolves(organizationAttributeSample);
        route = {
          params: {
            organizationAttributeId: '' + 123,
          },
        };
        const wrapper = shallowMount(OrganizationAttributeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.organizationAttribute).toMatchObject(organizationAttributeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationAttributeServiceStub.find.resolves(organizationAttributeSample);
        const wrapper = shallowMount(OrganizationAttributeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
