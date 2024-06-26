/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import PaymentService from './payment.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Payment } from '@/shared/model/payment.model';

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
  describe('Payment Service', () => {
    let service: PaymentService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PaymentService();
      currentDate = new Date();
      elemDefault = new Payment(123, 0, currentDate, currentDate, 0, 'CASH', 'OPEN', 'AAAAAAA', 'INACTIVE');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            paymentDueDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentPaidDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a Payment', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            paymentDueDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentPaidDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            paymentDueDate: currentDate,
            paymentPaidDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Payment', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Payment', async () => {
        const returnedFromService = Object.assign(
          {
            installmentNumber: 1,
            paymentDueDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentPaidDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentAmount: 1,
            typeOfPayment: 'BBBBBB',
            statusPayment: 'BBBBBB',
            customAttributesDetailsJSON: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            paymentDueDate: currentDate,
            paymentPaidDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Payment', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Payment', async () => {
        const patchObject = Object.assign(
          {
            installmentNumber: 1,
            paymentDueDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentAmount: 1,
            statusPayment: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          new Payment(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            paymentDueDate: currentDate,
            paymentPaidDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Payment', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Payment', async () => {
        const returnedFromService = Object.assign(
          {
            installmentNumber: 1,
            paymentDueDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentPaidDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            paymentAmount: 1,
            typeOfPayment: 'BBBBBB',
            statusPayment: 'BBBBBB',
            customAttributesDetailsJSON: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            paymentDueDate: currentDate,
            paymentPaidDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Payment', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Payment', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Payment', async () => {
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
