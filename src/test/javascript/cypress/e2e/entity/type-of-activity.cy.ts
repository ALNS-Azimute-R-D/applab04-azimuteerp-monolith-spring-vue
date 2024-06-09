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

describe('TypeOfActivity e2e test', () => {
  const typeOfActivityPageUrl = '/type-of-activity';
  const typeOfActivityPageUrlPattern = new RegExp('/type-of-activity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeOfActivitySample = { name: 'ugh trumpet' };

  let typeOfActivity;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-of-activities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-of-activities').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-of-activities/*').as('deleteEntityRequest');
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

  it('TypeOfActivities menu should load TypeOfActivities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-of-activity');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeOfActivity').should('exist');
    cy.url().should('match', typeOfActivityPageUrlPattern);
  });

  describe('TypeOfActivity page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeOfActivityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeOfActivity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-of-activity/new$'));
        cy.getEntityCreateUpdateHeading('TypeOfActivity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfActivityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-of-activities',
          body: typeOfActivitySample,
        }).then(({ body }) => {
          typeOfActivity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-of-activities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-of-activities?page=0&size=20>; rel="last",<http://localhost/api/type-of-activities?page=0&size=20>; rel="first"',
              },
              body: [typeOfActivity],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeOfActivityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeOfActivity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeOfActivity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfActivityPageUrlPattern);
      });

      it('edit button click should load edit TypeOfActivity page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfActivity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfActivityPageUrlPattern);
      });

      it('edit button click should load edit TypeOfActivity page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeOfActivity');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfActivityPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeOfActivity', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeOfActivity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeOfActivityPageUrlPattern);

        typeOfActivity = undefined;
      });
    });
  });

  describe('new TypeOfActivity page', () => {
    beforeEach(() => {
      cy.visit(`${typeOfActivityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeOfActivity');
    });

    it('should create an instance of TypeOfActivity', () => {
      cy.get(`[data-cy="acronym"]`).type('racing dishonour whelp');
      cy.get(`[data-cy="acronym"]`).should('have.value', 'racing dishonour whelp');

      cy.get(`[data-cy="name"]`).type('meh');
      cy.get(`[data-cy="name"]`).should('have.value', 'meh');

      cy.get(`[data-cy="description"]`).type('kaleidoscopic');
      cy.get(`[data-cy="description"]`).should('have.value', 'kaleidoscopic');

      cy.get(`[data-cy="handlerClazzName"]`).type('lighting');
      cy.get(`[data-cy="handlerClazzName"]`).should('have.value', 'lighting');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeOfActivity = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeOfActivityPageUrlPattern);
    });
  });
});
