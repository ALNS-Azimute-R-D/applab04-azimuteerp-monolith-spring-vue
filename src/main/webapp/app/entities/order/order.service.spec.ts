/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import OrderService from './order.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Order } from '@/shared/model/order.model';

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
  describe('Order Service', () => {
    let service: OrderService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new OrderService();
      currentDate = new Date();
      elemDefault = new Order(123, 'AAAAAAA', currentDate, 0, 0, 'COMPLETED', currentDate, 'INACTIVE');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            placedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            estimatedDeliveryDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a Order', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            placedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            estimatedDeliveryDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            placedDate: currentDate,
            estimatedDeliveryDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Order', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Order', async () => {
        const returnedFromService = Object.assign(
          {
            businessCode: 'BBBBBB',
            placedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            totalTaxValue: 1,
            totalDueValue: 1,
            status: 'BBBBBB',
            estimatedDeliveryDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            placedDate: currentDate,
            estimatedDeliveryDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Order', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Order', async () => {
        const patchObject = Object.assign(
          {
            businessCode: 'BBBBBB',
            totalTaxValue: 1,
            status: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          new Order(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            placedDate: currentDate,
            estimatedDeliveryDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Order', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Order', async () => {
        const returnedFromService = Object.assign(
          {
            businessCode: 'BBBBBB',
            placedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            totalTaxValue: 1,
            totalDueValue: 1,
            status: 'BBBBBB',
            estimatedDeliveryDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            placedDate: currentDate,
            estimatedDeliveryDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Order', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Order', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Order', async () => {
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
