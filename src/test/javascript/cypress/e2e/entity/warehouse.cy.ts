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

describe('Warehouse e2e test', () => {
  const warehousePageUrl = '/warehouse';
  const warehousePageUrlPattern = new RegExp('/warehouse(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const warehouseSample = {
    acronym: 'irritating gatecrash',
    name: 'physical',
    streetAddress: 'scientific',
    postalCode: 'toward',
    activationStatus: 'ACTIVE',
  };

  let warehouse;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/warehouses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/warehouses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/warehouses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (warehouse) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/warehouses/${warehouse.id}`,
      }).then(() => {
        warehouse = undefined;
      });
    }
  });

  it('Warehouses menu should load Warehouses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('warehouse');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Warehouse').should('exist');
    cy.url().should('match', warehousePageUrlPattern);
  });

  describe('Warehouse page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(warehousePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Warehouse page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/warehouse/new$'));
        cy.getEntityCreateUpdateHeading('Warehouse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', warehousePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/warehouses',
          body: warehouseSample,
        }).then(({ body }) => {
          warehouse = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/warehouses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/warehouses?page=0&size=20>; rel="last",<http://localhost/api/warehouses?page=0&size=20>; rel="first"',
              },
              body: [warehouse],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(warehousePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Warehouse page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('warehouse');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', warehousePageUrlPattern);
      });

      it('edit button click should load edit Warehouse page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Warehouse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', warehousePageUrlPattern);
      });

      it('edit button click should load edit Warehouse page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Warehouse');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', warehousePageUrlPattern);
      });

      it('last delete button click should delete instance of Warehouse', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('warehouse').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', warehousePageUrlPattern);

        warehouse = undefined;
      });
    });
  });

  describe('new Warehouse page', () => {
    beforeEach(() => {
      cy.visit(`${warehousePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Warehouse');
    });

    it('should create an instance of Warehouse', () => {
      cy.get(`[data-cy="acronym"]`).type('accidentally');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'accidentally');

      cy.get(`[data-cy="name"]`).type('dependable');
      cy.get(`[data-cy="name"]`).should('have.value', 'dependable');

      cy.get(`[data-cy="description"]`).type('clear-cut plush woefully');
      cy.get(`[data-cy="description"]`).should('have.value', 'clear-cut plush woefully');

      cy.get(`[data-cy="streetAddress"]`).type('genuine pish');
      cy.get(`[data-cy="streetAddress"]`).should('have.value', 'genuine pish');

      cy.get(`[data-cy="houseNumber"]`).type('until');
      cy.get(`[data-cy="houseNumber"]`).should('have.value', 'until');

      cy.get(`[data-cy="locationName"]`).type('fret enraged lesson');
      cy.get(`[data-cy="locationName"]`).should('have.value', 'fret enraged lesson');

      cy.get(`[data-cy="postalCode"]`).type('underneat');
      cy.get(`[data-cy="postalCode"]`).should('have.value', 'underneat');

      cy.setFieldImageAsBytesOfEntity('pointLocation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="mainEmail"]`).type('C:Jv@*J.q0-5K');
      cy.get(`[data-cy="mainEmail"]`).should('have.value', 'C:Jv@*J.q0-5K');

      cy.get(`[data-cy="landPhoneNumber"]`).type('when full chimp');
      cy.get(`[data-cy="landPhoneNumber"]`).should('have.value', 'when full chimp');

      cy.get(`[data-cy="mobilePhoneNumber"]`).type('on but');
      cy.get(`[data-cy="mobilePhoneNumber"]`).should('have.value', 'on but');

      cy.get(`[data-cy="faxNumber"]`).type('withdraw whethe');
      cy.get(`[data-cy="faxNumber"]`).should('have.value', 'withdraw whethe');

      cy.get(`[data-cy="customAttributesDetailsJSON"]`).type('phew');
      cy.get(`[data-cy="customAttributesDetailsJSON"]`).should('have.value', 'phew');

      cy.get(`[data-cy="activationStatus"]`).select('BLOCKED');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        warehouse = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', warehousePageUrlPattern);
    });
  });
});
