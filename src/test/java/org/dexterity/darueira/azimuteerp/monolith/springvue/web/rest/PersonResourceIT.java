package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.GenderEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.PersonRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.PersonService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PersonDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.PersonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final GenderEnum DEFAULT_GENDER = GenderEnum.MALE;
    private static final GenderEnum UPDATED_GENDER = GenderEnum.FEMALE;

    private static final String DEFAULT_CODE_BI = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BI = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NIF = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_MAIN_EMAIL = "Q1X2V@wrdi7./";
    private static final String UPDATED_MAIN_EMAIL = "iv>|@!KI'E.hdy";

    private static final String DEFAULT_LAND_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LAND_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERRED_LANGUAGE = "AAAAA";
    private static final String UPDATED_PREFERRED_LANGUAGE = "BBBBB";

    private static final String DEFAULT_USERNAME_IN_O_AUTH_2 = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME_IN_O_AUTH_2 = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID_IN_O_AUTH_2 = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID_IN_O_AUTH_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final byte[] DEFAULT_AVATAR_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_AVATAR_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PersonRepository personRepository;

    @Mock
    private PersonRepository personRepositoryMock;

    @Autowired
    private PersonMapper personMapper;

    @Mock
    private PersonService personServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    private Person insertedPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .fullname(DEFAULT_FULLNAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .gender(DEFAULT_GENDER)
            .codeBI(DEFAULT_CODE_BI)
            .codeNIF(DEFAULT_CODE_NIF)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .locationName(DEFAULT_LOCATION_NAME)
            .postalCode(DEFAULT_POSTAL_CODE)
            .mainEmail(DEFAULT_MAIN_EMAIL)
            .landPhoneNumber(DEFAULT_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER)
            .occupation(DEFAULT_OCCUPATION)
            .preferredLanguage(DEFAULT_PREFERRED_LANGUAGE)
            .usernameInOAuth2(DEFAULT_USERNAME_IN_O_AUTH_2)
            .userIdInOAuth2(DEFAULT_USER_ID_IN_O_AUTH_2)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(DEFAULT_ACTIVATION_STATUS)
            .avatarImg(DEFAULT_AVATAR_IMG)
            .avatarImgContentType(DEFAULT_AVATAR_IMG_CONTENT_TYPE);
        // Add required entity
        TypeOfPerson typeOfPerson;
        if (TestUtil.findAll(em, TypeOfPerson.class).isEmpty()) {
            typeOfPerson = TypeOfPersonResourceIT.createEntity(em);
            em.persist(typeOfPerson);
            em.flush();
        } else {
            typeOfPerson = TestUtil.findAll(em, TypeOfPerson.class).get(0);
        }
        person.setTypeOfPerson(typeOfPerson);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .fullname(UPDATED_FULLNAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .codeBI(UPDATED_CODE_BI)
            .codeNIF(UPDATED_CODE_NIF)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .occupation(UPDATED_OCCUPATION)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .usernameInOAuth2(UPDATED_USERNAME_IN_O_AUTH_2)
            .userIdInOAuth2(UPDATED_USER_ID_IN_O_AUTH_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .avatarImg(UPDATED_AVATAR_IMG)
            .avatarImgContentType(UPDATED_AVATAR_IMG_CONTENT_TYPE);
        // Add required entity
        TypeOfPerson typeOfPerson;
        if (TestUtil.findAll(em, TypeOfPerson.class).isEmpty()) {
            typeOfPerson = TypeOfPersonResourceIT.createUpdatedEntity(em);
            em.persist(typeOfPerson);
            em.flush();
        } else {
            typeOfPerson = TestUtil.findAll(em, TypeOfPerson.class).get(0);
        }
        person.setTypeOfPerson(typeOfPerson);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerson != null) {
            personRepository.delete(insertedPerson);
            insertedPerson = null;
        }
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        var returnedPersonDTO = om.readValue(
            restPersonMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PersonDTO.class
        );

        // Validate the Person in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPerson = personMapper.toEntity(returnedPersonDTO);
        assertPersonUpdatableFieldsEquals(returnedPerson, getPersistedPerson(returnedPerson));

        insertedPerson = returnedPerson;
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstnameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setFirstname(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastnameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setLastname(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setBirthDate(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setGender(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setStreetAddress(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setPostalCode(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMainEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setMainEmail(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        person.setActivationStatus(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].codeBI").value(hasItem(DEFAULT_CODE_BI)))
            .andExpect(jsonPath("$.[*].codeNIF").value(hasItem(DEFAULT_CODE_NIF)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].mainEmail").value(hasItem(DEFAULT_MAIN_EMAIL)))
            .andExpect(jsonPath("$.[*].landPhoneNumber").value(hasItem(DEFAULT_LAND_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].preferredLanguage").value(hasItem(DEFAULT_PREFERRED_LANGUAGE)))
            .andExpect(jsonPath("$.[*].usernameInOAuth2").value(hasItem(DEFAULT_USERNAME_IN_O_AUTH_2)))
            .andExpect(jsonPath("$.[*].userIdInOAuth2").value(hasItem(DEFAULT_USER_ID_IN_O_AUTH_2)))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].avatarImgContentType").value(hasItem(DEFAULT_AVATAR_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatarImg").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_AVATAR_IMG))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeopleWithEagerRelationshipsIsEnabled() throws Exception {
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(personServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeopleWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(personRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.codeBI").value(DEFAULT_CODE_BI))
            .andExpect(jsonPath("$.codeNIF").value(DEFAULT_CODE_NIF))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.mainEmail").value(DEFAULT_MAIN_EMAIL))
            .andExpect(jsonPath("$.landPhoneNumber").value(DEFAULT_LAND_PHONE_NUMBER))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.preferredLanguage").value(DEFAULT_PREFERRED_LANGUAGE))
            .andExpect(jsonPath("$.usernameInOAuth2").value(DEFAULT_USERNAME_IN_O_AUTH_2))
            .andExpect(jsonPath("$.userIdInOAuth2").value(DEFAULT_USER_ID_IN_O_AUTH_2))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()))
            .andExpect(jsonPath("$.avatarImgContentType").value(DEFAULT_AVATAR_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatarImg").value(Base64.getEncoder().encodeToString(DEFAULT_AVATAR_IMG)));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerson() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .fullname(UPDATED_FULLNAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .codeBI(UPDATED_CODE_BI)
            .codeNIF(UPDATED_CODE_NIF)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .occupation(UPDATED_OCCUPATION)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .usernameInOAuth2(UPDATED_USERNAME_IN_O_AUTH_2)
            .userIdInOAuth2(UPDATED_USER_ID_IN_O_AUTH_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .avatarImg(UPDATED_AVATAR_IMG)
            .avatarImgContentType(UPDATED_AVATAR_IMG_CONTENT_TYPE);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personDTO))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPersonToMatchAllProperties(updatedPerson);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .codeBI(UPDATED_CODE_BI)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .avatarImg(UPDATED_AVATAR_IMG)
            .avatarImgContentType(UPDATED_AVATAR_IMG_CONTENT_TYPE);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPerson, person), getPersistedPerson(person));
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .fullname(UPDATED_FULLNAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .codeBI(UPDATED_CODE_BI)
            .codeNIF(UPDATED_CODE_NIF)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .occupation(UPDATED_OCCUPATION)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE)
            .usernameInOAuth2(UPDATED_USERNAME_IN_O_AUTH_2)
            .userIdInOAuth2(UPDATED_USER_ID_IN_O_AUTH_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .avatarImg(UPDATED_AVATAR_IMG)
            .avatarImgContentType(UPDATED_AVATAR_IMG_CONTENT_TYPE);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonUpdatableFieldsEquals(partialUpdatedPerson, getPersistedPerson(partialUpdatedPerson));
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(personDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return personRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Person getPersistedPerson(Person person) {
        return personRepository.findById(person.getId()).orElseThrow();
    }

    protected void assertPersistedPersonToMatchAllProperties(Person expectedPerson) {
        assertPersonAllPropertiesEquals(expectedPerson, getPersistedPerson(expectedPerson));
    }

    protected void assertPersistedPersonToMatchUpdatableProperties(Person expectedPerson) {
        assertPersonAllUpdatablePropertiesEquals(expectedPerson, getPersistedPerson(expectedPerson));
    }
}
