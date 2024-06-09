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

describe('Artwork e2e test', () => {
  const artworkPageUrl = '/artwork';
  const artworkPageUrlPattern = new RegExp('/artwork(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const artworkSample = { artworkTitle: 'common now suspenders' };

  let artwork;
  let typeOfArtmedia;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-artmedias',
      body: { acronym: 'rectangular heavily tinted', name: 'tenderly rainy following', description: 'nautical against rare' },
    }).then(({ body }) => {
      typeOfArtmedia = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/artworks+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artworks').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artworks/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/type-of-artmedias', {
      statusCode: 200,
      body: [typeOfArtmedia],
    });

    cy.intercept('GET', '/api/artworks', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/artwork-casts', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (artwork) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artworks/${artwork.id}`,
      }).then(() => {
        artwork = undefined;
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
  });

  it('Artworks menu should load Artworks page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Artwork').should('exist');
    cy.url().should('match', artworkPageUrlPattern);
  });

  describe('Artwork page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Artwork page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork/new$'));
        cy.getEntityCreateUpdateHeading('Artwork');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artworks',
          body: {
            ...artworkSample,
            typeOfArtmedia: typeOfArtmedia,
          },
        }).then(({ body }) => {
          artwork = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artworks+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/artworks?page=0&size=20>; rel="last",<http://localhost/api/artworks?page=0&size=20>; rel="first"',
              },
              body: [artwork],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Artwork page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artwork');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });

      it('edit button click should load edit Artwork page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Artwork');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });

      it('edit button click should load edit Artwork page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Artwork');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);
      });

      it('last delete button click should delete instance of Artwork', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('artwork').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkPageUrlPattern);

        artwork = undefined;
      });
    });
  });

  describe('new Artwork page', () => {
    beforeEach(() => {
      cy.visit(`${artworkPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Artwork');
    });

    it('should create an instance of Artwork', () => {
      cy.get(`[data-cy="artworkTitle"]`).type('and');
      cy.get(`[data-cy="artworkTitle"]`).should('have.value', 'and');

      cy.get(`[data-cy="productionYear"]`).type('4206');
      cy.get(`[data-cy="productionYear"]`).should('have.value', '4206');

      cy.get(`[data-cy="seasonNumber"]`).type('29639');
      cy.get(`[data-cy="seasonNumber"]`).should('have.value', '29639');

      cy.get(`[data-cy="episodeOrSequenceNumber"]`).type('16155');
      cy.get(`[data-cy="episodeOrSequenceNumber"]`).should('have.value', '16155');

      cy.get(`[data-cy="registerIdInIMDB"]`).type('intercept');
      cy.get(`[data-cy="registerIdInIMDB"]`).should('have.value', 'intercept');

      cy.get(`[data-cy="assetsCollectionUUID"]`).type('concerning fruitful');
      cy.get(`[data-cy="assetsCollectionUUID"]`).should('have.value', 'concerning fruitful');

      cy.get(`[data-cy="contentDetailsJSON"]`).type('curiously numismatist not');
      cy.get(`[data-cy="contentDetailsJSON"]`).should('have.value', 'curiously numismatist not');

      cy.get(`[data-cy="typeOfArtmedia"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        artwork = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkPageUrlPattern);
    });
  });
});
