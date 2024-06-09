/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import InventoryTransactionService from './inventory-transaction.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { InventoryTransaction } from '@/shared/model/inventory-transaction.model';

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
  describe('InventoryTransaction Service', () => {
    let service: InventoryTransactionService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new InventoryTransactionService();
      currentDate = new Date();
      elemDefault = new InventoryTransaction(123, 0, currentDate, currentDate, 0, 'AAAAAAA', 'INACTIVE');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            transactionCreatedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            transactionModifiedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a InventoryTransaction', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            transactionCreatedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            transactionModifiedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            transactionCreatedDate: currentDate,
            transactionModifiedDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a InventoryTransaction', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a InventoryTransaction', async () => {
        const returnedFromService = Object.assign(
          {
            invoiceId: 1,
            transactionCreatedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            transactionModifiedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            quantity: 1,
            transactionComments: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            transactionCreatedDate: currentDate,
            transactionModifiedDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a InventoryTransaction', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a InventoryTransaction', async () => {
        const patchObject = Object.assign(
          {
            invoiceId: 1,
            transactionModifiedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            quantity: 1,
            transactionComments: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          new InventoryTransaction(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            transactionCreatedDate: currentDate,
            transactionModifiedDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a InventoryTransaction', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of InventoryTransaction', async () => {
        const returnedFromService = Object.assign(
          {
            invoiceId: 1,
            transactionCreatedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            transactionModifiedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            quantity: 1,
            transactionComments: 'BBBBBB',
            activationStatus: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            transactionCreatedDate: currentDate,
            transactionModifiedDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of InventoryTransaction', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a InventoryTransaction', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a InventoryTransaction', async () => {
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
