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

describe('Person e2e test', () => {
  const personPageUrl = '/person';
  const personPageUrlPattern = new RegExp('/person(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const personSample = {
    firstname: 'since',
    lastname: 'valuable devastate bite',
    birthDate: '2024-06-08',
    gender: 'OTHER',
    streetAddress: 'off badly wound',
    postalCode: 'alongside',
    mainEmail: 'N@Q&n.L.',
    activationStatus: 'ON_HOLD',
  };

  let person;
  let typeOfPerson;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/type-of-people',
      body: { code: 'ick g', description: 'prolong defray eek' },
    }).then(({ body }) => {
      typeOfPerson = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/people+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/people').as('postEntityRequest');
    cy.intercept('DELETE', '/api/people/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/type-of-people', {
      statusCode: 200,
      body: [typeOfPerson],
    });

    cy.intercept('GET', '/api/districts', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/people', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/organization-memberships', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/suppliers', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/customers', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/activities', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/events', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/event-programs', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/scheduled-activities', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/event-attendees', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (person) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/people/${person.id}`,
      }).then(() => {
        person = undefined;
      });
    }
  });

  afterEach(() => {
    if (typeOfPerson) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-of-people/${typeOfPerson.id}`,
      }).then(() => {
        typeOfPerson = undefined;
      });
    }
  });

  it('People menu should load People page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('person');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Person').should('exist');
    cy.url().should('match', personPageUrlPattern);
  });

  describe('Person page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(personPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Person page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/person/new$'));
        cy.getEntityCreateUpdateHeading('Person');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/people',
          body: {
            ...personSample,
            typeOfPerson: typeOfPerson,
          },
        }).then(({ body }) => {
          person = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/people+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/people?page=0&size=20>; rel="last",<http://localhost/api/people?page=0&size=20>; rel="first"',
              },
              body: [person],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(personPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Person page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('person');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });

      it('edit button click should load edit Person page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Person');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });

      it('edit button click should load edit Person page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Person');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);
      });

      it('last delete button click should delete instance of Person', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('person').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', personPageUrlPattern);

        person = undefined;
      });
    });
  });

  describe('new Person page', () => {
    beforeEach(() => {
      cy.visit(`${personPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Person');
    });

    it('should create an instance of Person', () => {
      cy.get(`[data-cy="firstname"]`).type('remarry consequently');
      cy.get(`[data-cy="firstname"]`).should('have.value', 'remarry consequently');

      cy.get(`[data-cy="lastname"]`).type('plus');
      cy.get(`[data-cy="lastname"]`).should('have.value', 'plus');

      cy.get(`[data-cy="fullname"]`).type('wry');
      cy.get(`[data-cy="fullname"]`).should('have.value', 'wry');

      cy.get(`[data-cy="birthDate"]`).type('2024-06-09');
      cy.get(`[data-cy="birthDate"]`).blur();
      cy.get(`[data-cy="birthDate"]`).should('have.value', '2024-06-09');

      cy.get(`[data-cy="gender"]`).select('FEMALE');

      cy.get(`[data-cy="codeBI"]`).type('among front about');
      cy.get(`[data-cy="codeBI"]`).should('have.value', 'among front about');

      cy.get(`[data-cy="codeNIF"]`).type('green serialise spoo');
      cy.get(`[data-cy="codeNIF"]`).should('have.value', 'green serialise spoo');

      cy.get(`[data-cy="streetAddress"]`).type('harmless sadly');
      cy.get(`[data-cy="streetAddress"]`).should('have.value', 'harmless sadly');

      cy.get(`[data-cy="houseNumber"]`).type('huzzah alpha yuck');
      cy.get(`[data-cy="houseNumber"]`).should('have.value', 'huzzah alpha yuck');

      cy.get(`[data-cy="locationName"]`).type('aw');
      cy.get(`[data-cy="locationName"]`).should('have.value', 'aw');

      cy.get(`[data-cy="postalCode"]`).type('since unl');
      cy.get(`[data-cy="postalCode"]`).should('have.value', 'since unl');

      cy.get(`[data-cy="mainEmail"]`).type(';tE@<=n.%_');
      cy.get(`[data-cy="mainEmail"]`).should('have.value', ';tE@<=n.%_');

      cy.get(`[data-cy="landPhoneNumber"]`).type('party tremendou');
      cy.get(`[data-cy="landPhoneNumber"]`).should('have.value', 'party tremendou');

      cy.get(`[data-cy="mobilePhoneNumber"]`).type('supposing till ');
      cy.get(`[data-cy="mobilePhoneNumber"]`).should('have.value', 'supposing till ');

      cy.get(`[data-cy="occupation"]`).type('upon');
      cy.get(`[data-cy="occupation"]`).should('have.value', 'upon');

      cy.get(`[data-cy="preferredLanguage"]`).type('repor');
      cy.get(`[data-cy="preferredLanguage"]`).should('have.value', 'repor');

      cy.get(`[data-cy="usernameInOAuth2"]`).type('gently');
      cy.get(`[data-cy="usernameInOAuth2"]`).should('have.value', 'gently');

      cy.get(`[data-cy="userIdInOAuth2"]`).type('even toot');
      cy.get(`[data-cy="userIdInOAuth2"]`).should('have.value', 'even toot');

      cy.get(`[data-cy="customAttributesDetailsJSON"]`).type('or');
      cy.get(`[data-cy="customAttributesDetailsJSON"]`).should('have.value', 'or');

      cy.get(`[data-cy="activationStatus"]`).select('INVALID');

      cy.setFieldImageAsBytesOfEntity('avatarImg', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="typeOfPerson"]`).select(1);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        person = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', personPageUrlPattern);
    });
  });
});
