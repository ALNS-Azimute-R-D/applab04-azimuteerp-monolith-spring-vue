/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import PersonService from './person.service';
import { DATE_FORMAT } from '@/shared/composables/date-format';
import { Person } from '@/shared/model/person.model';

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
  describe('Person Service', () => {
    let service: PersonService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PersonService();
      currentDate = new Date();
      elemDefault = new Person(
        123,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'MALE',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'INACTIVE',
        'image/png',
        'AAAAAAA',
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a Person', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Person', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Person', async () => {
        const returnedFromService = Object.assign(
          {
            firstname: 'BBBBBB',
            lastname: 'BBBBBB',
            fullname: 'BBBBBB',
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
            gender: 'BBBBBB',
            codeBI: 'BBBBBB',
            codeNIF: 'BBBBBB',
            streetAddress: 'BBBBBB',
            houseNumber: 'BBBBBB',
            locationName: 'BBBBBB',
            postalCode: 'BBBBBB',
            mainEmail: 'BBBBBB',
            landPhoneNumber: 'BBBBBB',
            mobilePhoneNumber: 'BBBBBB',
            occupation: 'BBBBBB',
            preferredLanguage: 'BBBBBB',
            usernameInOAuth2: 'BBBBBB',
            userIdInOAuth2: 'BBBBBB',
            customAttributesDetailsJSON: 'BBBBBB',
            activationStatus: 'BBBBBB',
            avatarImg: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Person', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Person', async () => {
        const patchObject = Object.assign(
          {
            firstname: 'BBBBBB',
            fullname: 'BBBBBB',
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
            gender: 'BBBBBB',
            codeBI: 'BBBBBB',
            codeNIF: 'BBBBBB',
            streetAddress: 'BBBBBB',
            houseNumber: 'BBBBBB',
            locationName: 'BBBBBB',
            occupation: 'BBBBBB',
            usernameInOAuth2: 'BBBBBB',
            activationStatus: 'BBBBBB',
            avatarImg: 'BBBBBB',
          },
          new Person(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Person', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Person', async () => {
        const returnedFromService = Object.assign(
          {
            firstname: 'BBBBBB',
            lastname: 'BBBBBB',
            fullname: 'BBBBBB',
            birthDate: dayjs(currentDate).format(DATE_FORMAT),
            gender: 'BBBBBB',
            codeBI: 'BBBBBB',
            codeNIF: 'BBBBBB',
            streetAddress: 'BBBBBB',
            houseNumber: 'BBBBBB',
            locationName: 'BBBBBB',
            postalCode: 'BBBBBB',
            mainEmail: 'BBBBBB',
            landPhoneNumber: 'BBBBBB',
            mobilePhoneNumber: 'BBBBBB',
            occupation: 'BBBBBB',
            preferredLanguage: 'BBBBBB',
            usernameInOAuth2: 'BBBBBB',
            userIdInOAuth2: 'BBBBBB',
            customAttributesDetailsJSON: 'BBBBBB',
            activationStatus: 'BBBBBB',
            avatarImg: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            birthDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Person', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Person', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Person', async () => {
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
