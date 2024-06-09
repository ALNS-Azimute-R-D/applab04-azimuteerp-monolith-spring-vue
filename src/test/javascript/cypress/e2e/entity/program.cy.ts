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

describe('Program e2e test', () => {
  const programPageUrl = '/program';
  const programPageUrlPattern = new RegExp('/program(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const programSample = { name: 'brave attribute', activationStatus: 'INVALID' };

  let program;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/programs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/programs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/programs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (program) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/programs/${program.id}`,
      }).then(() => {
        program = undefined;
      });
    }
  });

  it('Programs menu should load Programs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('program');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Program').should('exist');
    cy.url().should('match', programPageUrlPattern);
  });

  describe('Program page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(programPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Program page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/program/new$'));
        cy.getEntityCreateUpdateHeading('Program');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', programPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/programs',
          body: programSample,
        }).then(({ body }) => {
          program = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/programs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/programs?page=0&size=20>; rel="last",<http://localhost/api/programs?page=0&size=20>; rel="first"',
              },
              body: [program],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(programPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Program page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('program');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', programPageUrlPattern);
      });

      it('edit button click should load edit Program page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Program');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', programPageUrlPattern);
      });

      it('edit button click should load edit Program page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Program');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', programPageUrlPattern);
      });

      it('last delete button click should delete instance of Program', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('program').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', programPageUrlPattern);

        program = undefined;
      });
    });
  });

  describe('new Program page', () => {
    beforeEach(() => {
      cy.visit(`${programPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Program');
    });

    it('should create an instance of Program', () => {
      cy.get(`[data-cy="acronym"]`).type('lost altruistic rule');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'lost altruistic rule');

      cy.get(`[data-cy="name"]`).type('soft');
      cy.get(`[data-cy="name"]`).should('have.value', 'soft');

      cy.get(`[data-cy="description"]`).type('mmm instead');
      cy.get(`[data-cy="description"]`).should('have.value', 'mmm instead');

      cy.get(`[data-cy="fullDescription"]`).type('kind persevere');
      cy.get(`[data-cy="fullDescription"]`).should('have.value', 'kind persevere');

      cy.get(`[data-cy="targetPublic"]`).type('meh');
      cy.get(`[data-cy="targetPublic"]`).should('have.value', 'meh');

      cy.get(`[data-cy="activationStatus"]`).select('ON_HOLD');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        program = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', programPageUrlPattern);
    });
  });
});
