/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import TicketPurchasedService from './ticket-purchased.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { TicketPurchased } from '@/shared/model/ticket-purchased.model';

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
  describe('TicketPurchased Service', () => {
    let service: TicketPurchasedService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new TicketPurchasedService();
      currentDate = new Date();
      elemDefault = new TicketPurchased(123, 'AAAAAAA', currentDate, 0, 0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            duePaymentDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
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

      it('should create a TicketPurchased', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            duePaymentDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            duePaymentDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a TicketPurchased', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a TicketPurchased', async () => {
        const returnedFromService = Object.assign(
          {
            buyingCode: 'BBBBBB',
            duePaymentDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            amountOfTickets: 1,
            taxValue: 1,
            ticketValue: 1,
            acquiredSeatsNumbers: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            duePaymentDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a TicketPurchased', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a TicketPurchased', async () => {
        const patchObject = Object.assign({}, new TicketPurchased());
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            duePaymentDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a TicketPurchased', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of TicketPurchased', async () => {
        const returnedFromService = Object.assign(
          {
            buyingCode: 'BBBBBB',
            duePaymentDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            amountOfTickets: 1,
            taxValue: 1,
            ticketValue: 1,
            acquiredSeatsNumbers: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            duePaymentDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of TicketPurchased', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a TicketPurchased', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a TicketPurchased', async () => {
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
