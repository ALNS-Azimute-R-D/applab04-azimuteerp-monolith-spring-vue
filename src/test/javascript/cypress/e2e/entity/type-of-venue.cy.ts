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

describe('TypeOfVenue e2e test', () => {
  const typeOfVenuePageUrl = '/type-of-venue';
  const typeOfVenuePageUrlPattern = new RegExp('/type-of-venue(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeOfVenueSample = { name: 'falsify yippee appear' };

  let typeOfVenue;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-of-venues+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-of-venues').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-of-venues/*').as('deleteEntityRequest');
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

  it('TypeOfVenues menu should load TypeOfVenues page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-of-venue');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeOfVenue').should('exist');
    cy.url().should('match', typeOfVenuePageUrlPattern);
  });

  describe('TypeOfVenue page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeOfVenuePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeOfVenue page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-of-venue/new$'));
        cy.getEntityCreateUpdateHeading('TypeOfVenue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfVenuePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-of-venues',
          body: typeOfVenueSample,
        }).then(({ body }) => {
          typeOfVenue = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-of-venues+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-of-venues?page=0&size=20>; rel="last",<http://localhost/api/type-of-venues?page=0&size=20>; rel="first"',
              },
              body: [typeOfVenue],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeOfVenuePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeOfVenue page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeOfVenue');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfVenuePageUrlPattern);
      });

      it('edit button click should load edit TypeOfVenue page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfVenue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfVenuePageUrlPattern);
      });

      it('edit button click should load edit TypeOfVenue page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfVenue');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfVenuePageUrlPattern);
      });

      it('last delete button click should delete instance of TypeOfVenue', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeOfVenue').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfVenuePageUrlPattern);

        typeOfVenue = undefined;
      });
    });
  });

  describe('new TypeOfVenue page', () => {
    beforeEach(() => {
      cy.visit(`${typeOfVenuePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeOfVenue');
    });

    it('should create an instance of TypeOfVenue', () => {
      cy.get(`[data-cy="acronym"]`).type('lest excluding nimble');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'lest excluding nimble');

      cy.get(`[data-cy="name"]`).type('chick');
      cy.get(`[data-cy="name"]`).should('have.value', 'chick');

      cy.get(`[data-cy="description"]`).type('wherever without optimistically');
      cy.get(`[data-cy="description"]`).should('have.value', 'wherever without optimistically');

      cy.get(`[data-cy="handlerClazzName"]`).type('but spiff');
      cy.get(`[data-cy="handlerClazzName"]`).should('have.value', 'but spiff');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeOfVenue = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeOfVenuePageUrlPattern);
    });
  });
});
