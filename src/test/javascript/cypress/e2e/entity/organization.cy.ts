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

describe('Organization e2e test', () => {
  const organizationPageUrl = '/organization';
  const organizationPageUrlPattern = new RegExp('/organization(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const organizationSample = {
    acronym: 'likewise above wease',
    businessCode: 'what off',
    name: 'enigma but subtract',
    description: 'huzzah laryngitis',
    organizationStatus: 'ONBOARDING',
    activationStatus: 'INVALID',
  };

  let organization;
  let tenant;
  let typeOfOrganization;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/tenants',
      body: {
        acronym: 'provided lighthearte',
        name: 'mouse yowza',
        description: 'fabulous',
        customerBusinessCode: 'lieutenant aggr',
        businessHandlerClazz: 'why',
        mainContactPersonDetailsJSON: 'during',
        billingDetailsJSON: 'deceivingly curio amidst',
        technicalEnvironmentsDetailsJSON: 'yowza agree amid',
        customAttributesDetailsJSON: 'duh if',
        logoImg: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
        logoImgContentType: 'unknown',
        activationStatus: 'INACTIVE',
      },
    }).then(({ body }) => {
      tenant = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-organizations',
      body: {
        acronym: 'deliberately quiver ',
        name: 'furthermore to',
        description: 'redo drat forenenst',
        businessHandlerClazz: 'fragrance',
      },
    }).then(({ body }) => {
      typeOfOrganization = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/organizations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/organizations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/organizations/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/tenants', {
      statusCode: 200,
      body: [tenant],
    });

    cy.intercept('GET', '/api/type-of-organizations', {
      statusCode: 200,
      body: [typeOfOrganization],
    });

    cy.intercept('GET', '/api/organizations', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/organization-domains', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/organization-attributes', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/business-units', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/organization-roles', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/organization-memberships', {
      statusCode: 200,
      body: [],
    });
  });

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

  afterEach(() => {
    if (tenant) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tenants/${tenant.id}`,
      }).then(() => {
        tenant = undefined;
      });
    }
    if (typeOfOrganization) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-organizations/${typeOfOrganization.id}`,
      }).then(() => {
        typeOfOrganization = undefined;
      });
    }
  });

  it('Organizations menu should load Organizations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('organization');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Organization').should('exist');
    cy.url().should('match', organizationPageUrlPattern);
  });

  describe('Organization page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(organizationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Organization page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/organization/new$'));
        cy.getEntityCreateUpdateHeading('Organization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', organizationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/organizations',
          body: {
            ...organizationSample,
            tenant: tenant,
            typeOfOrganization: typeOfOrganization,
          },
        }).then(({ body }) => {
          organization = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/organizations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/organizations?page=0&size=20>; rel="last",<http://localhost/api/organizations?page=0&size=20>; rel="first"',
              },
              body: [organization],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(organizationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Organization page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('organization');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', organizationPageUrlPattern);
      });

      it('edit button click should load edit Organization page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Organization');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', organizationPageUrlPattern);
      });

      it('edit button click should load edit Organization page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Organization');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', organizationPageUrlPattern);
      });

      it('last delete button click should delete instance of Organization', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('organization').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', organizationPageUrlPattern);

        organization = undefined;
      });
    });
  });

  describe('new Organization page', () => {
    beforeEach(() => {
      cy.visit(`${organizationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Organization');
    });

    it('should create an instance of Organization', () => {
      cy.get(`[data-cy="acronym"]`).type('canine');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'canine');

      cy.get(`[data-cy="businessCode"]`).type('yesterday fairl');
      cy.get(`[data-cy="businessCode"]`).should('have.value', 'yesterday fairl');

      cy.get(`[data-cy="hierarchicalLevel"]`).type('ideology solution');
      cy.get(`[data-cy="hierarchicalLevel"]`).should('have.value', 'ideology solution');

      cy.get(`[data-cy="name"]`).type('tomato hence yowza');
      cy.get(`[data-cy="name"]`).should('have.value', 'tomato hence yowza');

      cy.get(`[data-cy="description"]`).type('why');
      cy.get(`[data-cy="description"]`).should('have.value', 'why');

      cy.get(`[data-cy="businessHandlerClazz"]`).type('punctually or');
      cy.get(`[data-cy="businessHandlerClazz"]`).should('have.value', 'punctually or');

      cy.get(`[data-cy="mainContactPersonDetailsJSON"]`).type('whoever');
      cy.get(`[data-cy="mainContactPersonDetailsJSON"]`).should('have.value', 'whoever');

      cy.get(`[data-cy="technicalEnvironmentsDetailsJSON"]`).type('ouch');
      cy.get(`[data-cy="technicalEnvironmentsDetailsJSON"]`).should('have.value', 'ouch');

      cy.get(`[data-cy="customAttributesDetailsJSON"]`).type('low regarding above');
      cy.get(`[data-cy="customAttributesDetailsJSON"]`).should('have.value', 'low regarding above');

      cy.get(`[data-cy="organizationStatus"]`).select('UNDER_EVALUATION');

      cy.get(`[data-cy="activationStatus"]`).select('ON_HOLD');

      cy.setFieldImageAsBytesOfEntity('logoImg', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="tenant"]`).select(1);
      cy.get(`[data-cy="typeOfOrganization"]`).select(1);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        organization = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', organizationPageUrlPattern);
    });
  });
});
