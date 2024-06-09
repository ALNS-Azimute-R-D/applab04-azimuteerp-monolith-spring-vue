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

describe('Event e2e test', () => {
  const eventPageUrl = '/event';
  const eventPageUrlPattern = new RegExp('/event(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const eventSample = {
    name: 'out qua',
    description: 'greedily yet',
    startTime: '2024-06-08T19:44:26.635Z',
    defaultTicketValue: 25231.79,
    status: 'IN_PREVIEM',
    activationStatus: 'BLOCKED',
  };

  let event;
  let typeOfEvent;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-events',
      body: {
        acronym: 'gadzooks shoe unto',
        name: 'that actually subedit',
        description: 'deem bow direct',
        handlerClazzName: 'ban regarding',
      },
    }).then(({ body }) => {
      typeOfEvent = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/events+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/events').as('postEntityRequest');
    cy.intercept('DELETE', '/api/events/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/venues', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/type-of-events', {
      statusCode: 200,
      body: [typeOfEvent],
    });

    cy.intercept('GET', '/api/people', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/asset-collections', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/event-programs', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/ticket-purchaseds', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/event-attendees', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (event) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/events/${event.id}`,
      }).then(() => {
        event = undefined;
      });
    }
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

  it('Events menu should load Events page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Event').should('exist');
    cy.url().should('match', eventPageUrlPattern);
  });

  describe('Event page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Event page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event/new$'));
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/events',
          body: {
            ...eventSample,
            typeOfEvent: typeOfEvent,
          },
        }).then(({ body }) => {
          event = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/events+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/events?page=0&size=20>; rel="last",<http://localhost/api/events?page=0&size=20>; rel="first"',
              },
              body: [event],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Event page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('event');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('edit button click should load edit Event page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('edit button click should load edit Event page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Event');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);
      });

      it('last delete button click should delete instance of Event', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('event').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventPageUrlPattern);

        event = undefined;
      });
    });
  });

  describe('new Event page', () => {
    beforeEach(() => {
      cy.visit(`${eventPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Event');
    });

    it('should create an instance of Event', () => {
      cy.get(`[data-cy="acronym"]`).type('aw aw disinherit');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'aw aw disinherit');

      cy.get(`[data-cy="name"]`).type('triumphantly where');
      cy.get(`[data-cy="name"]`).should('have.value', 'triumphantly where');

      cy.get(`[data-cy="description"]`).type('minus adverb');
      cy.get(`[data-cy="description"]`).should('have.value', 'minus adverb');

      cy.get(`[data-cy="fullDescription"]`).type('per');
      cy.get(`[data-cy="fullDescription"]`).should('have.value', 'per');

      cy.get(`[data-cy="startTime"]`).type('2024-06-08T20:35');
      cy.get(`[data-cy="startTime"]`).blur();
      cy.get(`[data-cy="startTime"]`).should('have.value', '2024-06-08T20:35');

      cy.get(`[data-cy="endTime"]`).type('2024-06-09T00:49');
      cy.get(`[data-cy="endTime"]`).blur();
      cy.get(`[data-cy="endTime"]`).should('have.value', '2024-06-09T00:49');

      cy.get(`[data-cy="defaultTicketValue"]`).type('16506.13');
      cy.get(`[data-cy="defaultTicketValue"]`).should('have.value', '16506.13');

      cy.get(`[data-cy="status"]`).select('OTHER');

      cy.get(`[data-cy="activationStatus"]`).select('INVALID');

      cy.get(`[data-cy="typeOfEvent"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        event = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', eventPageUrlPattern);
    });
  });
});
