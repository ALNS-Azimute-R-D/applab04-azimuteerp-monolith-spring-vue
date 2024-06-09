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

describe('ArtisticGenre e2e test', () => {
  const artisticGenrePageUrl = '/artistic-genre';
  const artisticGenrePageUrlPattern = new RegExp('/artistic-genre(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artisticGenreSample = { acronym: 'inside luck', name: 'likewise', description: 'hmph' };

  let artisticGenre;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artistic-genres+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artistic-genres').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artistic-genres/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (artisticGenre) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artistic-genres/${artisticGenre.id}`,
      }).then(() => {
        artisticGenre = undefined;
      });
    }
  });

  it('ArtisticGenres menu should load ArtisticGenres page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artistic-genre');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtisticGenre').should('exist');
    cy.url().should('match', artisticGenrePageUrlPattern);
  });

  describe('ArtisticGenre page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artisticGenrePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtisticGenre page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artistic-genre/new$'));
        cy.getEntityCreateUpdateHeading('ArtisticGenre');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artisticGenrePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artistic-genres',
          body: artisticGenreSample,
        }).then(({ body }) => {
          artisticGenre = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artistic-genres+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/artistic-genres?page=0&size=20>; rel="last",<http://localhost/api/artistic-genres?page=0&size=20>; rel="first"',
              },
              body: [artisticGenre],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artisticGenrePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ArtisticGenre page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artisticGenre');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artisticGenrePageUrlPattern);
      });

      it('edit button click should load edit ArtisticGenre page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtisticGenre');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artisticGenrePageUrlPattern);
      });

      it('edit button click should load edit ArtisticGenre page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtisticGenre');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artisticGenrePageUrlPattern);
      });

      it('last delete button click should delete instance of ArtisticGenre', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('artisticGenre').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artisticGenrePageUrlPattern);

        artisticGenre = undefined;
      });
    });
  });

  describe('new ArtisticGenre page', () => {
    beforeEach(() => {
      cy.visit(`${artisticGenrePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtisticGenre');
    });

    it('should create an instance of ArtisticGenre', () => {
      cy.get(`[data-cy="acronym"]`).type('next delightfully shine');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'next delightfully shine');

      cy.get(`[data-cy="name"]`).type('ugly');
      cy.get(`[data-cy="name"]`).should('have.value', 'ugly');

      cy.get(`[data-cy="description"]`).type('incidentally junior');
      cy.get(`[data-cy="description"]`).should('have.value', 'incidentally junior');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        artisticGenre = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', artisticGenrePageUrlPattern);
    });
  });
});
