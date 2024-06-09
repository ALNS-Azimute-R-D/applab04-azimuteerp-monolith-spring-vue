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

describe('Venue e2e test', () => {
  const venuePageUrl = '/venue';
  const venuePageUrlPattern = new RegExp('/venue(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const venueSample = { name: 'monthly' };

  let venue;
  let typeOfVenue;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-venues',
      body: { acronym: 'mispronounce', name: 'glint animated geez', description: 'walnut aw meh', handlerClazzName: 'phew' },
    }).then(({ body }) => {
      typeOfVenue = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/venues+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/venues').as('postEntityRequest');
    cy.intercept('DELETE', '/api/venues/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/type-of-venues', {
      statusCode: 200,
      body: [typeOfVenue],
    });

    cy.intercept('GET', '/api/common-localities', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/events', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (venue) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/venues/${venue.id}`,
      }).then(() => {
        venue = undefined;
      });
    }
  });

  afterEach(() => {
    if (typeOfVenue) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-venues/${typeOfVenue.id}`,
      }).then(() => {
        typeOfVenue = undefined;
      });
    }
  });

  it('Venues menu should load Venues page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('venue');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Venue').should('exist');
    cy.url().should('match', venuePageUrlPattern);
  });

  describe('Venue page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(venuePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Venue page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/venue/new$'));
        cy.getEntityCreateUpdateHeading('Venue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', venuePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/venues',
          body: {
            ...venueSample,
            typeOfVenue: typeOfVenue,
          },
        }).then(({ body }) => {
          venue = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/venues+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/venues?page=0&size=20>; rel="last",<http://localhost/api/venues?page=0&size=20>; rel="first"',
              },
              body: [venue],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(venuePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Venue page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('venue');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', venuePageUrlPattern);
      });

      it('edit button click should load edit Venue page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Venue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', venuePageUrlPattern);
      });

      it('edit button click should load edit Venue page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Venue');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', venuePageUrlPattern);
      });

      it('last delete button click should delete instance of Venue', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('venue').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', venuePageUrlPattern);

        venue = undefined;
      });
    });
  });

  describe('new Venue page', () => {
    beforeEach(() => {
      cy.visit(`${venuePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Venue');
    });

    it('should create an instance of Venue', () => {
      cy.get(`[data-cy="acronym"]`).type('flawless fluffy carefully');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'flawless fluffy carefully');

      cy.get(`[data-cy="name"]`).type('duh skill');
      cy.get(`[data-cy="name"]`).should('have.value', 'duh skill');

      cy.get(`[data-cy="description"]`).type('bloom onto');
      cy.get(`[data-cy="description"]`).should('have.value', 'bloom onto');

      cy.setFieldImageAsBytesOfEntity('geoPointLocation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="typeOfVenue"]`).select(1);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        venue = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', venuePageUrlPattern);
    });
  });
});
