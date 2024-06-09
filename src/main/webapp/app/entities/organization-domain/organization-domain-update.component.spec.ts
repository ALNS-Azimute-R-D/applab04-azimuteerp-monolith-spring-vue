/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationDomainUpdate from './organization-domain-update.vue';
import OrganizationDomainService from './organization-domain.service';
import AlertService from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';

type OrganizationDomainUpdateComponentType = InstanceType<typeof OrganizationDomainUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationDomainSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OrganizationDomainUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OrganizationDomain Management Update Component', () => {
    let comp: OrganizationDomainUpdateComponentType;
    let organizationDomainServiceStub: SinonStubbedInstance<OrganizationDomainService>;

    beforeEach(() => {
      route = {};
      organizationDomainServiceStub = sinon.createStubInstance<OrganizationDomainService>(OrganizationDomainService);
      organizationDomainServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          organizationDomainService: () => organizationDomainServiceStub,
          organizationService: () =>
            sinon.createStubInstance<OrganizationService>(OrganizationService, {
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
        const wrapper = shallowMount(OrganizationDomainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationDomain = organizationDomainSample;
        organizationDomainServiceStub.update.resolves(organizationDomainSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationDomainServiceStub.update.calledWith(organizationDomainSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        organizationDomainServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OrganizationDomainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationDomain = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationDomainServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        organizationDomainServiceStub.find.resolves(organizationDomainSample);
        organizationDomainServiceStub.retrieve.resolves([organizationDomainSample]);

        // WHEN
        route = {
          params: {
            organizationDomainId: '' + organizationDomainSample.id,
          },
        };
        const wrapper = shallowMount(OrganizationDomainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.organizationDomain).toMatchObject(organizationDomainSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationDomainServiceStub.find.resolves(organizationDomainSample);
        const wrapper = shallowMount(OrganizationDomainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
