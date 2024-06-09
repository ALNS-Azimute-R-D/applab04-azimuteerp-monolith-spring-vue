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

describe('TypeOfArtist e2e test', () => {
  const typeOfArtistPageUrl = '/type-of-artist';
  const typeOfArtistPageUrlPattern = new RegExp('/type-of-artist(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeOfArtistSample = { acronym: 'idle whoa how', name: 'until', description: 'quince so' };

  let typeOfArtist;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-of-artists+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-of-artists').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-of-artists/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (typeOfArtist) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-artists/${typeOfArtist.id}`,
      }).then(() => {
        typeOfArtist = undefined;
      });
    }
  });

  it('TypeOfArtists menu should load TypeOfArtists page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-of-artist');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeOfArtist').should('exist');
    cy.url().should('match', typeOfArtistPageUrlPattern);
  });

  describe('TypeOfArtist page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeOfArtistPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeOfArtist page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-of-artist/new$'));
        cy.getEntityCreateUpdateHeading('TypeOfArtist');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtistPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-of-artists',
          body: typeOfArtistSample,
        }).then(({ body }) => {
          typeOfArtist = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-of-artists+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-of-artists?page=0&size=20>; rel="last",<http://localhost/api/type-of-artists?page=0&size=20>; rel="first"',
              },
              body: [typeOfArtist],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeOfArtistPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeOfArtist page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeOfArtist');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtistPageUrlPattern);
      });

      it('edit button click should load edit TypeOfArtist page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfArtist');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtistPageUrlPattern);
      });

      it('edit button click should load edit TypeOfArtist page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfArtist');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtistPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeOfArtist', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeOfArtist').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtistPageUrlPattern);

        typeOfArtist = undefined;
      });
    });
  });

  describe('new TypeOfArtist page', () => {
    beforeEach(() => {
      cy.visit(`${typeOfArtistPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeOfArtist');
    });

    it('should create an instance of TypeOfArtist', () => {
      cy.get(`[data-cy="acronym"]`).type('strident huzzah');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'strident huzzah');

      cy.get(`[data-cy="name"]`).type('briskly dismal');
      cy.get(`[data-cy="name"]`).should('have.value', 'briskly dismal');

      cy.get(`[data-cy="description"]`).type('over grizzled');
      cy.get(`[data-cy="description"]`).should('have.value', 'over grizzled');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeOfArtist = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeOfArtistPageUrlPattern);
    });
  });
});
