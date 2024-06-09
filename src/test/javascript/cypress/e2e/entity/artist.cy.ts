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

describe('Artist e2e test', () => {
  const artistPageUrl = '/artist';
  const artistPageUrlPattern = new RegExp('/artist(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artistSample = { acronym: 'harmless', publicName: 'who interestingly' };

  let artist;
  let typeOfArtmedia;
  let typeOfArtist;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-artmedias',
      body: { acronym: 'as', name: 'healthily gate an', description: 'past frozen' },
    }).then(({ body }) => {
      typeOfArtmedia = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-artists',
      body: { acronym: 'whereas viciously', name: 'yesterday amusing via', description: 'as' },
    }).then(({ body }) => {
      typeOfArtist = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artists+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artists').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artists/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/type-of-artmedias', {
      statusCode: 200,
      body: [typeOfArtmedia],
    });

    cy.intercept('GET', '/api/type-of-artists', {
      statusCode: 200,
      body: [typeOfArtist],
    });

    cy.intercept('GET', '/api/artists', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/artistic-genres', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/artwork-casts', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (artist) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artists/${artist.id}`,
      }).then(() => {
        artist = undefined;
      });
    }
  });

  afterEach(() => {
    if (typeOfArtmedia) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-artmedias/${typeOfArtmedia.id}`,
      }).then(() => {
        typeOfArtmedia = undefined;
      });
    }
    if (typeOfArtist) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-artists/${typeOfArtist.id}`,
      }).then(() => {
        typeOfArtist = undefined;
      });
    }
  });

  it('Artists menu should load Artists page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artist');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Artist').should('exist');
    cy.url().should('match', artistPageUrlPattern);
  });

  describe('Artist page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artistPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Artist page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artist/new$'));
        cy.getEntityCreateUpdateHeading('Artist');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artistPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artists',
          body: {
            ...artistSample,
            typeOfArtmedia: typeOfArtmedia,
            typeOfArtist: typeOfArtist,
          },
        }).then(({ body }) => {
          artist = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artists+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/artists?page=0&size=20>; rel="last",<http://localhost/api/artists?page=0&size=20>; rel="first"',
              },
              body: [artist],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artistPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Artist page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artist');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artistPageUrlPattern);
      });

      it('edit button click should load edit Artist page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Artist');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artistPageUrlPattern);
      });

      it('edit button click should load edit Artist page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Artist');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artistPageUrlPattern);
      });

      it('last delete button click should delete instance of Artist', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('artist').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artistPageUrlPattern);

        artist = undefined;
      });
    });
  });

  describe('new Artist page', () => {
    beforeEach(() => {
      cy.visit(`${artistPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Artist');
    });

    it('should create an instance of Artist', () => {
      cy.get(`[data-cy="acronym"]`).type('especially but now');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'especially but now');

      cy.get(`[data-cy="publicName"]`).type('furthermore grotesque');
      cy.get(`[data-cy="publicName"]`).should('have.value', 'furthermore grotesque');

      cy.get(`[data-cy="realName"]`).type('whose');
      cy.get(`[data-cy="realName"]`).should('have.value', 'whose');

      cy.get(`[data-cy="biographyDetailsJSON"]`).type('solidly');
      cy.get(`[data-cy="biographyDetailsJSON"]`).should('have.value', 'solidly');

      cy.get(`[data-cy="typeOfArtmedia"]`).select(1);
      cy.get(`[data-cy="typeOfArtist"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        artist = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', artistPageUrlPattern);
    });
  });
});
