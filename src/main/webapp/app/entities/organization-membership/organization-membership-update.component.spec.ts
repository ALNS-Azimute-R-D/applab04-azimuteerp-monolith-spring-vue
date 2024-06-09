/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OrganizationMembershipUpdate from './organization-membership-update.vue';
import OrganizationMembershipService from './organization-membership.service';
import AlertService from '@/shared/alert/alert.service';

import OrganizationService from '@/entities/organization/organization.service';
import PersonService from '@/entities/person/person.service';

type OrganizationMembershipUpdateComponentType = InstanceType<typeof OrganizationMembershipUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const organizationMembershipSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OrganizationMembershipUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OrganizationMembership Management Update Component', () => {
    let comp: OrganizationMembershipUpdateComponentType;
    let organizationMembershipServiceStub: SinonStubbedInstance<OrganizationMembershipService>;

    beforeEach(() => {
      route = {};
      organizationMembershipServiceStub = sinon.createStubInstance<OrganizationMembershipService>(OrganizationMembershipService);
      organizationMembershipServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          organizationMembershipService: () => organizationMembershipServiceStub,
          organizationService: () =>
            sinon.createStubInstance<OrganizationService>(OrganizationService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          personService: () =>
            sinon.createStubInstance<PersonService>(PersonService, {
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
        const wrapper = shallowMount(OrganizationMembershipUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationMembership = organizationMembershipSample;
        organizationMembershipServiceStub.update.resolves(organizationMembershipSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationMembershipServiceStub.update.calledWith(organizationMembershipSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        organizationMembershipServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OrganizationMembershipUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.organizationMembership = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationMembershipServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        organizationMembershipServiceStub.find.resolves(organizationMembershipSample);
        organizationMembershipServiceStub.retrieve.resolves([organizationMembershipSample]);

        // WHEN
        route = {
          params: {
            organizationMembershipId: '' + organizationMembershipSample.id,
          },
        };
        const wrapper = shallowMount(OrganizationMembershipUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.organizationMembership).toMatchObject(organizationMembershipSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        organizationMembershipServiceStub.find.resolves(organizationMembershipSample);
        const wrapper = shallowMount(OrganizationMembershipUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
