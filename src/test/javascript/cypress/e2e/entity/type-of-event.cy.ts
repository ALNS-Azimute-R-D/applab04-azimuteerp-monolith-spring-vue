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

describe('TypeOfEvent e2e test', () => {
  const typeOfEventPageUrl = '/type-of-event';
  const typeOfEventPageUrlPattern = new RegExp('/type-of-event(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeOfEventSample = { name: 'exorcise ouch' };

  let typeOfEvent;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-of-events+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-of-events').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-of-events/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (typeOfEvent) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-events/${typeOfEvent.id}`,
      }).then(() => {
        typeOfEvent = undefined;
      });
    }
  });

  it('TypeOfEvents menu should load TypeOfEvents page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-of-event');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeOfEvent').should('exist');
    cy.url().should('match', typeOfEventPageUrlPattern);
  });

  describe('TypeOfEvent page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeOfEventPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeOfEvent page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-of-event/new$'));
        cy.getEntityCreateUpdateHeading('TypeOfEvent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfEventPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-of-events',
          body: typeOfEventSample,
        }).then(({ body }) => {
          typeOfEvent = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-of-events+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-of-events?page=0&size=20>; rel="last",<http://localhost/api/type-of-events?page=0&size=20>; rel="first"',
              },
              body: [typeOfEvent],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeOfEventPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeOfEvent page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeOfEvent');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfEventPageUrlPattern);
      });

      it('edit button click should load edit TypeOfEvent page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfEvent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfEventPageUrlPattern);
      });

      it('edit button click should load edit TypeOfEvent page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfEvent');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfEventPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeOfEvent', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeOfEvent').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfEventPageUrlPattern);

        typeOfEvent = undefined;
      });
    });
  });

  describe('new TypeOfEvent page', () => {
    beforeEach(() => {
      cy.visit(`${typeOfEventPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeOfEvent');
    });

    it('should create an instance of TypeOfEvent', () => {
      cy.get(`[data-cy="acronym"]`).type('for');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'for');

      cy.get(`[data-cy="name"]`).type('than tight gall');
      cy.get(`[data-cy="name"]`).should('have.value', 'than tight gall');

      cy.get(`[data-cy="description"]`).type('gee');
      cy.get(`[data-cy="description"]`).should('have.value', 'gee');

      cy.get(`[data-cy="handlerClazzName"]`).type('grass indeed');
      cy.get(`[data-cy="handlerClazzName"]`).should('have.value', 'grass indeed');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeOfEvent = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeOfEventPageUrlPattern);
    });
  });
});
