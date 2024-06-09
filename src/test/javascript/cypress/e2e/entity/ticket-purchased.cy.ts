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

describe('TicketPurchased e2e test', () => {
  const ticketPurchasedPageUrl = '/ticket-purchased';
  const ticketPurchasedPageUrlPattern = new RegExp('/ticket-purchased(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ticketPurchasedSample = {};

  let ticketPurchased;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ticket-purchaseds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ticket-purchaseds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ticket-purchaseds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ticketPurchased) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ticket-purchaseds/${ticketPurchased.id}`,
      }).then(() => {
        ticketPurchased = undefined;
      });
    }
  });

  it('TicketPurchaseds menu should load TicketPurchaseds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ticket-purchased');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TicketPurchased').should('exist');
    cy.url().should('match', ticketPurchasedPageUrlPattern);
  });

  describe('TicketPurchased page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ticketPurchasedPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TicketPurchased page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ticket-purchased/new$'));
        cy.getEntityCreateUpdateHeading('TicketPurchased');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', ticketPurchasedPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ticket-purchaseds',
          body: ticketPurchasedSample,
        }).then(({ body }) => {
          ticketPurchased = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ticket-purchaseds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ticket-purchaseds?page=0&size=20>; rel="last",<http://localhost/api/ticket-purchaseds?page=0&size=20>; rel="first"',
              },
              body: [ticketPurchased],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(ticketPurchasedPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TicketPurchased page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ticketPurchased');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', ticketPurchasedPageUrlPattern);
      });

      it('edit button click should load edit TicketPurchased page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TicketPurchased');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', ticketPurchasedPageUrlPattern);
      });

      it('edit button click should load edit TicketPurchased page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TicketPurchased');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', ticketPurchasedPageUrlPattern);
      });

      it('last delete button click should delete instance of TicketPurchased', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('ticketPurchased').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', ticketPurchasedPageUrlPattern);

        ticketPurchased = undefined;
      });
    });
  });

  describe('new TicketPurchased page', () => {
    beforeEach(() => {
      cy.visit(`${ticketPurchasedPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TicketPurchased');
    });

    it('should create an instance of TicketPurchased', () => {
      cy.get(`[data-cy="buyingCode"]`).type('yippee equality');
      cy.get(`[data-cy="buyingCode"]`).should('have.value', 'yippee equality');

      cy.get(`[data-cy="duePaymentDate"]`).type('2024-06-09T12:14');
      cy.get(`[data-cy="duePaymentDate"]`).blur();
      cy.get(`[data-cy="duePaymentDate"]`).should('have.value', '2024-06-09T12:14');

      cy.get(`[data-cy="amountOfTickets"]`).type('18767');
      cy.get(`[data-cy="amountOfTickets"]`).should('have.value', '18767');

      cy.get(`[data-cy="taxValue"]`).type('29928.71');
      cy.get(`[data-cy="taxValue"]`).should('have.value', '29928.71');

      cy.get(`[data-cy="ticketValue"]`).type('31758.92');
      cy.get(`[data-cy="ticketValue"]`).should('have.value', '31758.92');

      cy.get(`[data-cy="acquiredSeatsNumbers"]`).type('lashes inflate');
      cy.get(`[data-cy="acquiredSeatsNumbers"]`).should('have.value', 'lashes inflate');

      cy.get(`[data-cy="description"]`).type('before');
      cy.get(`[data-cy="description"]`).should('have.value', 'before');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        ticketPurchased = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', ticketPurchasedPageUrlPattern);
    });
  });
});
