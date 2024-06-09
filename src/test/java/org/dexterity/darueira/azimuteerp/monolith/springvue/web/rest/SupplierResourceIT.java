package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.SupplierAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.SupplierRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.SupplierService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.SupplierDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.SupplierMapper;
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
 * Integration tests for the {@link SupplierResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SupplierResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REGION = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REGION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_POINT_LOCATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POINT_LOCATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POINT_LOCATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POINT_LOCATION_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MAIN_EMAIL = "VHo?@vrK.rS9(.";
    private static final String UPDATED_MAIN_EMAIL = "[(ka9@m}.{+HDDM";

    private static final String DEFAULT_PHONE_NUMBER_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/suppliers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierRepository supplierRepositoryMock;

    @Autowired
    private SupplierMapper supplierMapper;

    @Mock
    private SupplierService supplierServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    private Supplier insertedSupplier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .acronym(DEFAULT_ACRONYM)
            .companyName(DEFAULT_COMPANY_NAME)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .locationName(DEFAULT_LOCATION_NAME)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .zipPostalCode(DEFAULT_ZIP_POSTAL_CODE)
            .countryRegion(DEFAULT_COUNTRY_REGION)
            .pointLocation(DEFAULT_POINT_LOCATION)
            .pointLocationContentType(DEFAULT_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(DEFAULT_MAIN_EMAIL)
            .phoneNumber1(DEFAULT_PHONE_NUMBER_1)
            .phoneNumber2(DEFAULT_PHONE_NUMBER_2)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        supplier.setRepresentativePerson(person);
        return supplier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createUpdatedEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .acronym(UPDATED_ACRONYM)
            .companyName(UPDATED_COMPANY_NAME)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .phoneNumber1(UPDATED_PHONE_NUMBER_1)
            .phoneNumber2(UPDATED_PHONE_NUMBER_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        supplier.setRepresentativePerson(person);
        return supplier;
    }

    @BeforeEach
    public void initTest() {
        supplier = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSupplier != null) {
            supplierRepository.delete(insertedSupplier);
            insertedSupplier = null;
        }
    }

    @Test
    @Transactional
    void createSupplier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);
        var returnedSupplierDTO = om.readValue(
            restSupplierMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SupplierDTO.class
        );

        // Validate the Supplier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSupplier = supplierMapper.toEntity(returnedSupplierDTO);
        assertSupplierUpdatableFieldsEquals(returnedSupplier, getPersistedSupplier(returnedSupplier));

        insertedSupplier = returnedSupplier;
    }

    @Test
    @Transactional
    void createSupplierWithExistingId() throws Exception {
        // Create the Supplier with an existing ID
        supplier.setId(1L);
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplier.setAcronym(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplier.setCompanyName(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplier.setStreetAddress(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplier.setActivationStatus(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSuppliers() throws Exception {
        // Initialize the database
        insertedSupplier = supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].zipPostalCode").value(hasItem(DEFAULT_ZIP_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].countryRegion").value(hasItem(DEFAULT_COUNTRY_REGION)))
            .andExpect(jsonPath("$.[*].pointLocationContentType").value(hasItem(DEFAULT_POINT_LOCATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pointLocation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_POINT_LOCATION))))
            .andExpect(jsonPath("$.[*].mainEmail").value(hasItem(DEFAULT_MAIN_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber1").value(hasItem(DEFAULT_PHONE_NUMBER_1)))
            .andExpect(jsonPath("$.[*].phoneNumber2").value(hasItem(DEFAULT_PHONE_NUMBER_2)))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSuppliersWithEagerRelationshipsIsEnabled() throws Exception {
        when(supplierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupplierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(supplierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSuppliersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(supplierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupplierMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(supplierRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSupplier() throws Exception {
        // Initialize the database
        insertedSupplier = supplierRepository.saveAndFlush(supplier);

        // Get the supplier
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL_ID, supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplier.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE))
            .andExpect(jsonPath("$.zipPostalCode").value(DEFAULT_ZIP_POSTAL_CODE))
            .andExpect(jsonPath("$.countryRegion").value(DEFAULT_COUNTRY_REGION))
            .andExpect(jsonPath("$.pointLocationContentType").value(DEFAULT_POINT_LOCATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.pointLocation").value(Base64.getEncoder().encodeToString(DEFAULT_POINT_LOCATION)))
            .andExpect(jsonPath("$.mainEmail").value(DEFAULT_MAIN_EMAIL))
            .andExpect(jsonPath("$.phoneNumber1").value(DEFAULT_PHONE_NUMBER_1))
            .andExpect(jsonPath("$.phoneNumber2").value(DEFAULT_PHONE_NUMBER_2))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSupplier() throws Exception {
        // Get the supplier
        restSupplierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSupplier() throws Exception {
        // Initialize the database
        insertedSupplier = supplierRepository.saveAndFlush(supplier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supplier
        Supplier updatedSupplier = supplierRepository.findById(supplier.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSupplier are not directly saved in db
        em.detach(updatedSupplier);
        updatedSupplier
            .acronym(UPDATED_ACRONYM)
            .companyName(UPDATED_COMPANY_NAME)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .phoneNumber1(UPDATED_PHONE_NUMBER_1)
            .phoneNumber2(UPDATED_PHONE_NUMBER_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        SupplierDTO supplierDTO = supplierMapper.toDto(updatedSupplier);

        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supplierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSupplierToMatchAllProperties(updatedSupplier);
    }

    @Test
    @Transactional
    void putNonExistingSupplier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplier.setId(longCount.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplier.setId(longCount.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplier.setId(longCount.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierWithPatch() throws Exception {
        // Initialize the database
        insertedSupplier = supplierRepository.saveAndFlush(supplier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supplier using partial update
        Supplier partialUpdatedSupplier = new Supplier();
        partialUpdatedSupplier.setId(supplier.getId());

        partialUpdatedSupplier
            .streetAddress(UPDATED_STREET_ADDRESS)
            .locationName(UPDATED_LOCATION_NAME)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .phoneNumber2(UPDATED_PHONE_NUMBER_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);

        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplier.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSupplierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSupplier, supplier), getPersistedSupplier(supplier));
    }

    @Test
    @Transactional
    void fullUpdateSupplierWithPatch() throws Exception {
        // Initialize the database
        insertedSupplier = supplierRepository.saveAndFlush(supplier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supplier using partial update
        Supplier partialUpdatedSupplier = new Supplier();
        partialUpdatedSupplier.setId(supplier.getId());

        partialUpdatedSupplier
            .acronym(UPDATED_ACRONYM)
            .companyName(UPDATED_COMPANY_NAME)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .phoneNumber1(UPDATED_PHONE_NUMBER_1)
            .phoneNumber2(UPDATED_PHONE_NUMBER_2)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplier.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSupplierUpdatableFieldsEquals(partialUpdatedSupplier, getPersistedSupplier(partialUpdatedSupplier));
    }

    @Test
    @Transactional
    void patchNonExistingSupplier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplier.setId(longCount.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplierDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplier.setId(longCount.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplier.setId(longCount.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(supplierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplier() throws Exception {
        // Initialize the database
        insertedSupplier = supplierRepository.saveAndFlush(supplier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the supplier
        restSupplierMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplier.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return supplierRepository.count();
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

    protected Supplier getPersistedSupplier(Supplier supplier) {
        return supplierRepository.findById(supplier.getId()).orElseThrow();
    }

    protected void assertPersistedSupplierToMatchAllProperties(Supplier expectedSupplier) {
        assertSupplierAllPropertiesEquals(expectedSupplier, getPersistedSupplier(expectedSupplier));
    }

    protected void assertPersistedSupplierToMatchUpdatableProperties(Supplier expectedSupplier) {
        assertSupplierAllUpdatablePropertiesEquals(expectedSupplier, getPersistedSupplier(expectedSupplier));
    }
}
