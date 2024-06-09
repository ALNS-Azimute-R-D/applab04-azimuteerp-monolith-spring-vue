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

describe('EventProgram e2e test', () => {
  const eventProgramPageUrl = '/event-program';
  const eventProgramPageUrlPattern = new RegExp('/event-program(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const eventProgramSample = {};

  let eventProgram;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/event-programs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/event-programs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/event-programs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (eventProgram) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/event-programs/${eventProgram.id}`,
      }).then(() => {
        eventProgram = undefined;
      });
    }
  });

  it('EventPrograms menu should load EventPrograms page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event-program');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EventProgram').should('exist');
    cy.url().should('match', eventProgramPageUrlPattern);
  });

  describe('EventProgram page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventProgramPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EventProgram page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event-program/new$'));
        cy.getEntityCreateUpdateHeading('EventProgram');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventProgramPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/event-programs',
          body: eventProgramSample,
        }).then(({ body }) => {
          eventProgram = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/event-programs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/event-programs?page=0&size=20>; rel="last",<http://localhost/api/event-programs?page=0&size=20>; rel="first"',
              },
              body: [eventProgram],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventProgramPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EventProgram page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('eventProgram');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventProgramPageUrlPattern);
      });

      it('edit button click should load edit EventProgram page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventProgram');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventProgramPageUrlPattern);
      });

      it('edit button click should load edit EventProgram page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventProgram');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventProgramPageUrlPattern);
      });

      it('last delete button click should delete instance of EventProgram', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('eventProgram').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventProgramPageUrlPattern);

        eventProgram = undefined;
      });
    });
  });

  describe('new EventProgram page', () => {
    beforeEach(() => {
      cy.visit(`${eventProgramPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EventProgram');
    });

    it('should create an instance of EventProgram', () => {
      cy.get(`[data-cy="percentageExecution"]`).type('13313.76');
      cy.get(`[data-cy="percentageExecution"]`).should('have.value', '13313.76');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        eventProgram = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', eventProgramPageUrlPattern);
    });
  });
});
