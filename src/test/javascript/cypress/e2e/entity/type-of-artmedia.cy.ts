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

describe('TypeOfArtmedia e2e test', () => {
  const typeOfArtmediaPageUrl = '/type-of-artmedia';
  const typeOfArtmediaPageUrlPattern = new RegExp('/type-of-artmedia(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeOfArtmediaSample = { acronym: 'yet', name: 'ossify', description: 'wonderfully if' };

  let typeOfArtmedia;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-of-artmedias+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-of-artmedias').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-of-artmedias/*').as('deleteEntityRequest');
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

  it('TypeOfArtmedias menu should load TypeOfArtmedias page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-of-artmedia');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeOfArtmedia').should('exist');
    cy.url().should('match', typeOfArtmediaPageUrlPattern);
  });

  describe('TypeOfArtmedia page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeOfArtmediaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeOfArtmedia page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-of-artmedia/new$'));
        cy.getEntityCreateUpdateHeading('TypeOfArtmedia');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtmediaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-of-artmedias',
          body: typeOfArtmediaSample,
        }).then(({ body }) => {
          typeOfArtmedia = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-of-artmedias+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-of-artmedias?page=0&size=20>; rel="last",<http://localhost/api/type-of-artmedias?page=0&size=20>; rel="first"',
              },
              body: [typeOfArtmedia],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeOfArtmediaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeOfArtmedia page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeOfArtmedia');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtmediaPageUrlPattern);
      });

      it('edit button click should load edit TypeOfArtmedia page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfArtmedia');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtmediaPageUrlPattern);
      });

      it('edit button click should load edit TypeOfArtmedia page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfArtmedia');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtmediaPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeOfArtmedia', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeOfArtmedia').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfArtmediaPageUrlPattern);

        typeOfArtmedia = undefined;
      });
    });
  });

  describe('new TypeOfArtmedia page', () => {
    beforeEach(() => {
      cy.visit(`${typeOfArtmediaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeOfArtmedia');
    });

    it('should create an instance of TypeOfArtmedia', () => {
      cy.get(`[data-cy="acronym"]`).type('measly faith towards');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'measly faith towards');

      cy.get(`[data-cy="name"]`).type('hm');
      cy.get(`[data-cy="name"]`).should('have.value', 'hm');

      cy.get(`[data-cy="description"]`).type('oh');
      cy.get(`[data-cy="description"]`).should('have.value', 'oh');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeOfArtmedia = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeOfArtmediaPageUrlPattern);
    });
  });
});
