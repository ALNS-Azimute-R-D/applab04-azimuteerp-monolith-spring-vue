import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Supplier e2e test', () => {
  const supplierPageUrl = '/supplier';
  const supplierPageUrlPattern = new RegExp('/supplier(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const supplierSample = {"acronym":"cull","companyName":"kindly among","streetAddress":"consequently","activationStatus":"INACTIVE"};

  let supplier;
  // let person;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/people',
      body: {"firstname":"above but","lastname":"guilty an espouse","fullname":"brr majestic speak","birthDate":"2024-06-09","gender":"MALE","codeBI":"sick","codeNIF":"ambitious","streetAddress":"down ferociously statute","houseNumber":"performance arrogant","locationName":"whenever","postalCode":"meh to","mainEmail":"R]c`)0@g,Gxu2.vYv","landPhoneNumber":"hence","mobilePhoneNumber":"mmm before","occupation":"fowl","preferredLanguage":"booho","usernameInOAuth2":"to off hm","userIdInOAuth2":"pish","customAttributesDetailsJSON":"unethically so","activationStatus":"ACTIVE","avatarImg":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","avatarImgContentType":"unknown"},
    }).then(({ body }) => {
      person = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/suppliers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/suppliers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/suppliers/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/people', {
      statusCode: 200,
      body: [person],
    });

    cy.intercept('GET', '/api/inventory-transactions', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/products', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (supplier) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/suppliers/${supplier.id}`,
      }).then(() => {
        supplier = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (person) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/people/${person.id}`,
      }).then(() => {
        person = undefined;
      });
    }
  });
   */

  it('Suppliers menu should load Suppliers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('supplier');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Supplier').should('exist');
    cy.url().should('match', supplierPageUrlPattern);
  });

  describe('Supplier page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(supplierPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Supplier page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/supplier/new$'));
        cy.getEntityCreateUpdateHeading('Supplier');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', supplierPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/suppliers',
          body: {
            ...supplierSample,
            representativePerson: person,
          },
        }).then(({ body }) => {
          supplier = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/suppliers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/suppliers?page=0&size=20>; rel="last",<http://localhost/api/suppliers?page=0&size=20>; rel="first"',
              },
              body: [supplier],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(supplierPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(supplierPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Supplier page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('supplier');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', supplierPageUrlPattern);
      });

      it('edit button click should load edit Supplier page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Supplier');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', supplierPageUrlPattern);
      });

      it('edit button click should load edit Supplier page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Supplier');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', supplierPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Supplier', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('supplier').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', supplierPageUrlPattern);

        supplier = undefined;
      });
    });
  });

  describe('new Supplier page', () => {
    beforeEach(() => {
      cy.visit(`${supplierPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Supplier');
    });

    it.skip('should create an instance of Supplier', () => {
      cy.get(`[data-cy="acronym"]`).type('amongst versus');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'amongst versus');

      cy.get(`[data-cy="companyName"]`).type('now prevent');
      cy.get(`[data-cy="companyName"]`).should('have.value', 'now prevent');

      cy.get(`[data-cy="streetAddress"]`).type('invoke hastily');
      cy.get(`[data-cy="streetAddress"]`).should('have.value', 'invoke hastily');

      cy.get(`[data-cy="houseNumber"]`).type('quirkily');
      cy.get(`[data-cy="houseNumber"]`).should('have.value', 'quirkily');

      cy.get(`[data-cy="locationName"]`).type('till');
      cy.get(`[data-cy="locationName"]`).should('have.value', 'till');

      cy.get(`[data-cy="city"]`).type('Harveyboro');
      cy.get(`[data-cy="city"]`).should('have.value', 'Harveyboro');

      cy.get(`[data-cy="stateProvince"]`).type('bah fake bucket');
      cy.get(`[data-cy="stateProvince"]`).should('have.value', 'bah fake bucket');

      cy.get(`[data-cy="zipPostalCode"]`).type('psst');
      cy.get(`[data-cy="zipPostalCode"]`).should('have.value', 'psst');

      cy.get(`[data-cy="countryRegion"]`).type('afterwards');
      cy.get(`[data-cy="countryRegion"]`).should('have.value', 'afterwards');

      cy.setFieldImageAsBytesOfEntity('pointLocation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="mainEmail"]`).type('`@C.2|R.=DJ(/');
      cy.get(`[data-cy="mainEmail"]`).should('have.value', '`@C.2|R.=DJ(/');

      cy.get(`[data-cy="phoneNumber1"]`).type('pointed as');
      cy.get(`[data-cy="phoneNumber1"]`).should('have.value', 'pointed as');

      cy.get(`[data-cy="phoneNumber2"]`).type('until close');
      cy.get(`[data-cy="phoneNumber2"]`).should('have.value', 'until close');

      cy.get(`[data-cy="customAttributesDetailsJSON"]`).type('wherever near venti');
      cy.get(`[data-cy="customAttributesDetailsJSON"]`).should('have.value', 'wherever near venti');

      cy.get(`[data-cy="activationStatus"]`).select('INACTIVE');

      cy.get(`[data-cy="representativePerson"]`).select(1);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        supplier = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', supplierPageUrlPattern);
    });
  });
});
