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

describe('ArtworkCast e2e test', () => {
  const artworkCastPageUrl = '/artwork-cast';
  const artworkCastPageUrlPattern = new RegExp('/artwork-cast(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const artworkCastSample = {};

  let artworkCast;
  // let artwork;
  // let artist;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/artworks',
      body: {"artworkTitle":"grotesque","productionYear":6768,"seasonNumber":9524,"episodeOrSequenceNumber":26246,"registerIdInIMDB":"telnet","assetsCollectionUUID":"briefly off bat","contentDetailsJSON":"worriedly boring woefully"},
    }).then(({ body }) => {
      artwork = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/artists',
      body: {"acronym":"suppress daintily","publicName":"longingly","realName":"unseat sternly","biographyDetailsJSON":"nurture"},
    }).then(({ body }) => {
      artist = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/artwork-casts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/artwork-casts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/artwork-casts/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/artworks', {
      statusCode: 200,
      body: [artwork],
    });

    cy.intercept('GET', '/api/artists', {
      statusCode: 200,
      body: [artist],
    });

  });
   */

  afterEach(() => {
    if (artworkCast) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artwork-casts/${artworkCast.id}`,
      }).then(() => {
        artworkCast = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (artwork) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artworks/${artwork.id}`,
      }).then(() => {
        artwork = undefined;
      });
    }
    if (artist) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/artists/${artist.id}`,
      }).then(() => {
        artist = undefined;
      });
    }
  });
   */

  it('ArtworkCasts menu should load ArtworkCasts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('artwork-cast');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ArtworkCast').should('exist');
    cy.url().should('match', artworkCastPageUrlPattern);
  });

  describe('ArtworkCast page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(artworkCastPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ArtworkCast page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/artwork-cast/new$'));
        cy.getEntityCreateUpdateHeading('ArtworkCast');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCastPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/artwork-casts',
          body: {
            ...artworkCastSample,
            artwork: artwork,
            artist: artist,
          },
        }).then(({ body }) => {
          artworkCast = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/artwork-casts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/artwork-casts?page=0&size=20>; rel="last",<http://localhost/api/artwork-casts?page=0&size=20>; rel="first"',
              },
              body: [artworkCast],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(artworkCastPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(artworkCastPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details ArtworkCast page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('artworkCast');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCastPageUrlPattern);
      });

      it('edit button click should load edit ArtworkCast page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkCast');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCastPageUrlPattern);
      });

      it('edit button click should load edit ArtworkCast page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ArtworkCast');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCastPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of ArtworkCast', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('artworkCast').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', artworkCastPageUrlPattern);

        artworkCast = undefined;
      });
    });
  });

  describe('new ArtworkCast page', () => {
    beforeEach(() => {
      cy.visit(`${artworkCastPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ArtworkCast');
    });

    it.skip('should create an instance of ArtworkCast', () => {
      cy.get(`[data-cy="orderOfAppearance"]`).type('5629');
      cy.get(`[data-cy="orderOfAppearance"]`).should('have.value', '5629');

      cy.get(`[data-cy="characterName"]`).type('imbalance swiftly');
      cy.get(`[data-cy="characterName"]`).should('have.value', 'imbalance swiftly');

      cy.get(`[data-cy="mainAssetUUID"]`).type('initiative');
      cy.get(`[data-cy="mainAssetUUID"]`).should('have.value', 'initiative');

      cy.get(`[data-cy="characterDetailsJSON"]`).type('oncology once');
      cy.get(`[data-cy="characterDetailsJSON"]`).should('have.value', 'oncology once');

      cy.get(`[data-cy="artwork"]`).select(1);
      cy.get(`[data-cy="artist"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        artworkCast = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', artworkCastPageUrlPattern);
    });
  });
});
