/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ScheduledActivity from './scheduled-activity.vue';
import ScheduledActivityService from './scheduled-activity.service';
import AlertService from '@/shared/alert/alert.service';

type ScheduledActivityComponentType = InstanceType<typeof ScheduledActivity>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ScheduledActivity Management Component', () => {
    let scheduledActivityServiceStub: SinonStubbedInstance<ScheduledActivityService>;
    let mountOptions: MountingOptions<ScheduledActivityComponentType>['global'];

    beforeEach(() => {
      scheduledActivityServiceStub = sinon.createStubInstance<ScheduledActivityService>(ScheduledActivityService);
      scheduledActivityServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          scheduledActivityService: () => scheduledActivityServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        scheduledActivityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ScheduledActivity, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(scheduledActivityServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.scheduledActivities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(ScheduledActivity, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(scheduledActivityServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: ScheduledActivityComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ScheduledActivity, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        scheduledActivityServiceStub.retrieve.reset();
        scheduledActivityServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        scheduledActivityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(scheduledActivityServiceStub.retrieve.called).toBeTruthy();
        expect(comp.scheduledActivities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(scheduledActivityServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        scheduledActivityServiceStub.retrieve.reset();
        scheduledActivityServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(scheduledActivityServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.scheduledActivities[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(scheduledActivityServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        scheduledActivityServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeScheduledActivity();
        await comp.$nextTick(); // clear components

        // THEN
        expect(scheduledActivityServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(scheduledActivityServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
