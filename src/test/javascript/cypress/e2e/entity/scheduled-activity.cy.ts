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

describe('ScheduledActivity e2e test', () => {
  const scheduledActivityPageUrl = '/scheduled-activity';
  const scheduledActivityPageUrlPattern = new RegExp('/scheduled-activity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const scheduledActivitySample = { startTime: '2024-06-09T10:07:14.142Z', activationStatus: 'INACTIVE' };

  let scheduledActivity;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/scheduled-activities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/scheduled-activities').as('postEntityRequest');
    cy.intercept('DELETE', '/api/scheduled-activities/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (scheduledActivity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/scheduled-activities/${scheduledActivity.id}`,
      }).then(() => {
        scheduledActivity = undefined;
      });
    }
  });

  it('ScheduledActivities menu should load ScheduledActivities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('scheduled-activity');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ScheduledActivity').should('exist');
    cy.url().should('match', scheduledActivityPageUrlPattern);
  });

  describe('ScheduledActivity page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(scheduledActivityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ScheduledActivity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/scheduled-activity/new$'));
        cy.getEntityCreateUpdateHeading('ScheduledActivity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', scheduledActivityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/scheduled-activities',
          body: scheduledActivitySample,
        }).then(({ body }) => {
          scheduledActivity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/scheduled-activities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/scheduled-activities?page=0&size=20>; rel="last",<http://localhost/api/scheduled-activities?page=0&size=20>; rel="first"',
              },
              body: [scheduledActivity],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(scheduledActivityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ScheduledActivity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('scheduledActivity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', scheduledActivityPageUrlPattern);
      });

      it('edit button click should load edit ScheduledActivity page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ScheduledActivity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', scheduledActivityPageUrlPattern);
      });

      it('edit button click should load edit ScheduledActivity page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ScheduledActivity');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', scheduledActivityPageUrlPattern);
      });

      it('last delete button click should delete instance of ScheduledActivity', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('scheduledActivity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', scheduledActivityPageUrlPattern);

        scheduledActivity = undefined;
      });
    });
  });

  describe('new ScheduledActivity page', () => {
    beforeEach(() => {
      cy.visit(`${scheduledActivityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ScheduledActivity');
    });

    it('should create an instance of ScheduledActivity', () => {
      cy.get(`[data-cy="customizedName"]`).type('likewise negative um');
      cy.get(`[data-cy="customizedName"]`).should('have.value', 'likewise negative um');

      cy.get(`[data-cy="startTime"]`).type('2024-06-09T15:09');
      cy.get(`[data-cy="startTime"]`).blur();
      cy.get(`[data-cy="startTime"]`).should('have.value', '2024-06-09T15:09');

      cy.get(`[data-cy="endTime"]`).type('2024-06-08T16:52');
      cy.get(`[data-cy="endTime"]`).blur();
      cy.get(`[data-cy="endTime"]`).should('have.value', '2024-06-08T16:52');

      cy.get(`[data-cy="numberOfAttendees"]`).type('7268');
      cy.get(`[data-cy="numberOfAttendees"]`).should('have.value', '7268');

      cy.get(`[data-cy="averageEvaluationInStars"]`).type('12464');
      cy.get(`[data-cy="averageEvaluationInStars"]`).should('have.value', '12464');

      cy.get(`[data-cy="customAttributtesDetailsJSON"]`).type('out');
      cy.get(`[data-cy="customAttributtesDetailsJSON"]`).should('have.value', 'out');

      cy.get(`[data-cy="activationStatus"]`).select('BLOCKED');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        scheduledActivity = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', scheduledActivityPageUrlPattern);
    });
  });
});
