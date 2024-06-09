/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import ActivityService from './activity.service';
import { DATE_FORMAT, DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Activity } from '@/shared/model/activity.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Activity Service', () => {
    let service: ActivityService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ActivityService();
      currentDate = new Date();
      elemDefault = new Activity(123, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'PT1S', currentDate, currentDate, 'INACTIVE');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            lastPerformedDate: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Activity', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            lastPerformedDate: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            lastPerformedDate: currentDate,
            createdAt: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Activity', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Activity', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            shortDescription: 'BBBBBB',
            fullDescription: 'BBBBBB',
            mainGoals: 'BBBBBB',
            estimatedDurationTime: 'PT2S',
            lastPerformedDate: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            lastPerformedDate: currentDate,
            createdAt: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Activity', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Activity', async () => {
        const patchObject = Object.assign(
          {
            shortDescription: 'BBBBBB',
            fullDescription: 'BBBBBB',
            estimatedDurationTime: 'PT2S',
            lastPerformedDate: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            activationStatus: 'BBBBBB',
          },
          new Activity(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            lastPerformedDate: currentDate,
            createdAt: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Activity', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Activity', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            shortDescription: 'BBBBBB',
            fullDescription: 'BBBBBB',
            mainGoals: 'BBBBBB',
            estimatedDurationTime: 'PT2S',
            lastPerformedDate: dayjs(currentDate).format(DATE_FORMAT),
            createdAt: dayjs(currentDate).format(DATE_TIME_FORMAT),
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            lastPerformedDate: currentDate,
            createdAt: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Activity', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Activity', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Activity', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
