/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import OrganizationService from './organization.service';
import { Organization } from '@/shared/model/organization.model';

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
  describe('Organization Service', () => {
    let service: OrganizationService;
    let elemDefault;

    beforeEach(() => {
      service = new OrganizationService();
      elemDefault = new Organization(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'UNDER_EVALUATION',
        'INACTIVE',
        'image/png',
        'AAAAAAA',
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
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

      it('should create a Organization', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Organization', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Organization', async () => {
        const returnedFromService = Object.assign(
          {
            acronym: 'BBBBBB',
            businessCode: 'BBBBBB',
            hierarchicalLevel: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            businessHandlerClazz: 'BBBBBB',
            mainContactPersonDetailsJSON: 'BBBBBB',
            technicalEnvironmentsDetailsJSON: 'BBBBBB',
            customAttributesDetailsJSON: 'BBBBBB',
            organizationStatus: 'BBBBBB',
            activationStatus: 'BBBBBB',
            logoImg: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Organization', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Organization', async () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            organizationStatus: 'BBBBBB',
            logoImg: 'BBBBBB',
          },
          new Organization(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Organization', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Organization', async () => {
        const returnedFromService = Object.assign(
          {
            acronym: 'BBBBBB',
            businessCode: 'BBBBBB',
            hierarchicalLevel: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            businessHandlerClazz: 'BBBBBB',
            mainContactPersonDetailsJSON: 'BBBBBB',
            technicalEnvironmentsDetailsJSON: 'BBBBBB',
            customAttributesDetailsJSON: 'BBBBBB',
            organizationStatus: 'BBBBBB',
            activationStatus: 'BBBBBB',
            logoImg: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Organization', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Organization', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Organization', async () => {
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
