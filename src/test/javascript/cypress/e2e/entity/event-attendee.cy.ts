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

describe('EventAttendee e2e test', () => {
  const eventAttendeePageUrl = '/event-attendee';
  const eventAttendeePageUrlPattern = new RegExp('/event-attendee(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const eventAttendeeSample = { attendedAsRole: 'anxiously before billboard' };

  let eventAttendee;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/event-attendees+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/event-attendees').as('postEntityRequest');
    cy.intercept('DELETE', '/api/event-attendees/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (eventAttendee) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/event-attendees/${eventAttendee.id}`,
      }).then(() => {
        eventAttendee = undefined;
      });
    }
  });

  it('EventAttendees menu should load EventAttendees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event-attendee');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EventAttendee').should('exist');
    cy.url().should('match', eventAttendeePageUrlPattern);
  });

  describe('EventAttendee page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventAttendeePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EventAttendee page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event-attendee/new$'));
        cy.getEntityCreateUpdateHeading('EventAttendee');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventAttendeePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/event-attendees',
          body: eventAttendeeSample,
        }).then(({ body }) => {
          eventAttendee = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/event-attendees+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/event-attendees?page=0&size=20>; rel="last",<http://localhost/api/event-attendees?page=0&size=20>; rel="first"',
              },
              body: [eventAttendee],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventAttendeePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EventAttendee page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('eventAttendee');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventAttendeePageUrlPattern);
      });

      it('edit button click should load edit EventAttendee page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventAttendee');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventAttendeePageUrlPattern);
      });

      it('edit button click should load edit EventAttendee page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventAttendee');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventAttendeePageUrlPattern);
      });

      it('last delete button click should delete instance of EventAttendee', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('eventAttendee').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventAttendeePageUrlPattern);

        eventAttendee = undefined;
      });
    });
  });

  describe('new EventAttendee page', () => {
    beforeEach(() => {
      cy.visit(`${eventAttendeePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EventAttendee');
    });

    it('should create an instance of EventAttendee', () => {
      cy.get(`[data-cy="attendedAsRole"]`).type('status');
      cy.get(`[data-cy="attendedAsRole"]`).should('have.value', 'status');

      cy.get(`[data-cy="wasPresentInEvent"]`).should('not.be.checked');
      cy.get(`[data-cy="wasPresentInEvent"]`).click();
      cy.get(`[data-cy="wasPresentInEvent"]`).should('be.checked');

      cy.get(`[data-cy="shouldBuyTicket"]`).should('not.be.checked');
      cy.get(`[data-cy="shouldBuyTicket"]`).click();
      cy.get(`[data-cy="shouldBuyTicket"]`).should('be.checked');

      cy.get(`[data-cy="ticketNumber"]`).type('without phooey');
      cy.get(`[data-cy="ticketNumber"]`).should('have.value', 'without phooey');

      cy.get(`[data-cy="seatNumber"]`).type('meanwhile ');
      cy.get(`[data-cy="seatNumber"]`).should('have.value', 'meanwhile ');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        eventAttendee = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', eventAttendeePageUrlPattern);
    });
  });
});
