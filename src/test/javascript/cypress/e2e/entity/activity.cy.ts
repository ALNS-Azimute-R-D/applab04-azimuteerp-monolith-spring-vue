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

describe('Activity e2e test', () => {
  const activityPageUrl = '/activity';
  const activityPageUrlPattern = new RegExp('/activity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const activitySample = { name: 'tabby soliloquize', activationStatus: 'INVALID' };

  let activity;
  let typeOfActivity;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-activities',
      body: { acronym: 'underneath expectation', name: 'inasmuch', description: 'across', handlerClazzName: 'against brief' },
    }).then(({ body }) => {
      typeOfActivity = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/activities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/activities').as('postEntityRequest');
    cy.intercept('DELETE', '/api/activities/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/type-of-activities', {
      statusCode: 200,
      body: [typeOfActivity],
    });

    cy.intercept('GET', '/api/people', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/asset-collections', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/scheduled-activities', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (activity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/activities/${activity.id}`,
      }).then(() => {
        activity = undefined;
      });
    }
  });

  afterEach(() => {
    if (typeOfActivity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-activities/${typeOfActivity.id}`,
      }).then(() => {
        typeOfActivity = undefined;
      });
    }
  });

  it('Activities menu should load Activities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('activity');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Activity').should('exist');
    cy.url().should('match', activityPageUrlPattern);
  });

  describe('Activity page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(activityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Activity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/activity/new$'));
        cy.getEntityCreateUpdateHeading('Activity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', activityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/activities',
          body: {
            ...activitySample,
            typeOfActivity: typeOfActivity,
          },
        }).then(({ body }) => {
          activity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/activities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/activities?page=0&size=20>; rel="last",<http://localhost/api/activities?page=0&size=20>; rel="first"',
              },
              body: [activity],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(activityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Activity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('activity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', activityPageUrlPattern);
      });

      it('edit button click should load edit Activity page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Activity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', activityPageUrlPattern);
      });

      it('edit button click should load edit Activity page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Activity');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', activityPageUrlPattern);
      });

      it('last delete button click should delete instance of Activity', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('activity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', activityPageUrlPattern);

        activity = undefined;
      });
    });
  });

  describe('new Activity page', () => {
    beforeEach(() => {
      cy.visit(`${activityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Activity');
    });

    it('should create an instance of Activity', () => {
      cy.get(`[data-cy="name"]`).type('to poor');
      cy.get(`[data-cy="name"]`).should('have.value', 'to poor');

      cy.get(`[data-cy="shortDescription"]`).type('lopsided kick');
      cy.get(`[data-cy="shortDescription"]`).should('have.value', 'lopsided kick');

      cy.get(`[data-cy="fullDescription"]`).type('er dizzy');
      cy.get(`[data-cy="fullDescription"]`).should('have.value', 'er dizzy');

      cy.get(`[data-cy="mainGoals"]`).type('except dissemble');
      cy.get(`[data-cy="mainGoals"]`).should('have.value', 'except dissemble');

      cy.get(`[data-cy="estimatedDurationTime"]`).type('PT56M');
      cy.get(`[data-cy="estimatedDurationTime"]`).blur();
      cy.get(`[data-cy="estimatedDurationTime"]`).should('have.value', 'PT56M');

      cy.get(`[data-cy="lastPerformedDate"]`).type('2024-06-08');
      cy.get(`[data-cy="lastPerformedDate"]`).blur();
      cy.get(`[data-cy="lastPerformedDate"]`).should('have.value', '2024-06-08');

      cy.get(`[data-cy="createdAt"]`).type('2024-06-08T23:44');
      cy.get(`[data-cy="createdAt"]`).blur();
      cy.get(`[data-cy="createdAt"]`).should('have.value', '2024-06-08T23:44');

      cy.get(`[data-cy="activationStatus"]`).select('INVALID');

      cy.get(`[data-cy="typeOfActivity"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        activity = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', activityPageUrlPattern);
    });
  });
});
