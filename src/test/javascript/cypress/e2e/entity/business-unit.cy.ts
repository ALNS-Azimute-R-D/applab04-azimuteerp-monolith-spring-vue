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

describe('BusinessUnit e2e test', () => {
  const businessUnitPageUrl = '/business-unit';
  const businessUnitPageUrlPattern = new RegExp('/business-unit(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const businessUnitSample = {"acronym":"keep macadamia fooey","name":"likewise for","activationStatus":"ACTIVE"};

  let businessUnit;
  // let organization;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/organizations',
      body: {"acronym":"surrounds anti reluc","businessCode":"ladder","hierarchicalLevel":"um","name":"whoa","description":"yahoo backcomb","businessHandlerClazz":"cilantro","mainContactPersonDetailsJSON":"out wilted exotic","technicalEnvironmentsDetailsJSON":"why","customAttributesDetailsJSON":"angrily gah ramp","organizationStatus":"PENDENT","activationStatus":"BLOCKED","logoImg":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","logoImgContentType":"unknown"},
    }).then(({ body }) => {
      organization = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/business-units+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/business-units').as('postEntityRequest');
    cy.intercept('DELETE', '/api/business-units/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/organizations', {
      statusCode: 200,
      body: [organization],
    });

    cy.intercept('GET', '/api/business-units', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (businessUnit) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/business-units/${businessUnit.id}`,
      }).then(() => {
        businessUnit = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (organization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/organizations/${organization.id}`,
      }).then(() => {
        organization = undefined;
      });
    }
  });
   */

  it('BusinessUnits menu should load BusinessUnits page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('business-unit');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BusinessUnit').should('exist');
    cy.url().should('match', businessUnitPageUrlPattern);
  });

  describe('BusinessUnit page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(businessUnitPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BusinessUnit page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/business-unit/new$'));
        cy.getEntityCreateUpdateHeading('BusinessUnit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', businessUnitPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/business-units',
          body: {
            ...businessUnitSample,
            organization: organization,
          },
        }).then(({ body }) => {
          businessUnit = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/business-units+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/business-units?page=0&size=20>; rel="last",<http://localhost/api/business-units?page=0&size=20>; rel="first"',
              },
              body: [businessUnit],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(businessUnitPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(businessUnitPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details BusinessUnit page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('businessUnit');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', businessUnitPageUrlPattern);
      });

      it('edit button click should load edit BusinessUnit page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BusinessUnit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', businessUnitPageUrlPattern);
      });

      it('edit button click should load edit BusinessUnit page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BusinessUnit');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', businessUnitPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of BusinessUnit', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('businessUnit').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', businessUnitPageUrlPattern);

        businessUnit = undefined;
      });
    });
  });

  describe('new BusinessUnit page', () => {
    beforeEach(() => {
      cy.visit(`${businessUnitPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BusinessUnit');
    });

    it.skip('should create an instance of BusinessUnit', () => {
      cy.get(`[data-cy="acronym"]`).type('tart');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'tart');

      cy.get(`[data-cy="hierarchicalLevel"]`).type('alloy from');
      cy.get(`[data-cy="hierarchicalLevel"]`).should('have.value', 'alloy from');

      cy.get(`[data-cy="name"]`).type('yuck considering vice');
      cy.get(`[data-cy="name"]`).should('have.value', 'yuck considering vice');

      cy.get(`[data-cy="activationStatus"]`).select('BLOCKED');

      cy.get(`[data-cy="organization"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        businessUnit = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', businessUnitPageUrlPattern);
    });
  });
});
