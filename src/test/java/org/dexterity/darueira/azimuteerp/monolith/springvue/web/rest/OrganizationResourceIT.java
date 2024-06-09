package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.OrganizationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationMapper;
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
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrganizationResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HIERARCHICAL_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_HIERARCHICAL_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_HANDLER_CLAZZ = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_HANDLER_CLAZZ = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final OrganizationStatusEnum DEFAULT_ORGANIZATION_STATUS = OrganizationStatusEnum.UNDER_EVALUATION;
    private static final OrganizationStatusEnum UPDATED_ORGANIZATION_STATUS = OrganizationStatusEnum.ONBOARDING;

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final byte[] DEFAULT_LOGO_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_IMG_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationRepository organizationRepositoryMock;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Mock
    private OrganizationService organizationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    private Organization insertedOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .acronym(DEFAULT_ACRONYM)
            .businessCode(DEFAULT_BUSINESS_CODE)
            .hierarchicalLevel(DEFAULT_HIERARCHICAL_LEVEL)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .businessHandlerClazz(DEFAULT_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .organizationStatus(DEFAULT_ORGANIZATION_STATUS)
            .activationStatus(DEFAULT_ACTIVATION_STATUS)
            .logoImg(DEFAULT_LOGO_IMG)
            .logoImgContentType(DEFAULT_LOGO_IMG_CONTENT_TYPE);
        // Add required entity
        Tenant tenant;
        if (TestUtil.findAll(em, Tenant.class).isEmpty()) {
            tenant = TenantResourceIT.createEntity(em);
            em.persist(tenant);
            em.flush();
        } else {
            tenant = TestUtil.findAll(em, Tenant.class).get(0);
        }
        organization.setTenant(tenant);
        // Add required entity
        TypeOfOrganization typeOfOrganization;
        if (TestUtil.findAll(em, TypeOfOrganization.class).isEmpty()) {
            typeOfOrganization = TypeOfOrganizationResourceIT.createEntity(em);
            em.persist(typeOfOrganization);
            em.flush();
        } else {
            typeOfOrganization = TestUtil.findAll(em, TypeOfOrganization.class).get(0);
        }
        organization.setTypeOfOrganization(typeOfOrganization);
        return organization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity(EntityManager em) {
        Organization organization = new Organization()
            .acronym(UPDATED_ACRONYM)
            .businessCode(UPDATED_BUSINESS_CODE)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .organizationStatus(UPDATED_ORGANIZATION_STATUS)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE);
        // Add required entity
        Tenant tenant;
        if (TestUtil.findAll(em, Tenant.class).isEmpty()) {
            tenant = TenantResourceIT.createUpdatedEntity(em);
            em.persist(tenant);
            em.flush();
        } else {
            tenant = TestUtil.findAll(em, Tenant.class).get(0);
        }
        organization.setTenant(tenant);
        // Add required entity
        TypeOfOrganization typeOfOrganization;
        if (TestUtil.findAll(em, TypeOfOrganization.class).isEmpty()) {
            typeOfOrganization = TypeOfOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(typeOfOrganization);
            em.flush();
        } else {
            typeOfOrganization = TestUtil.findAll(em, TypeOfOrganization.class).get(0);
        }
        organization.setTypeOfOrganization(typeOfOrganization);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organization = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrganization != null) {
            organizationRepository.delete(insertedOrganization);
            insertedOrganization = null;
        }
    }

    @Test
    @Transactional
    void createOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        var returnedOrganizationDTO = om.readValue(
            restOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationDTO.class
        );

        // Validate the Organization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganization = organizationMapper.toEntity(returnedOrganizationDTO);
        assertOrganizationUpdatableFieldsEquals(returnedOrganization, getPersistedOrganization(returnedOrganization));

        insertedOrganization = returnedOrganization;
    }

    @Test
    @Transactional
    void createOrganizationWithExistingId() throws Exception {
        // Create the Organization with an existing ID
        organization.setId(1L);
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organization.setAcronym(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBusinessCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organization.setBusinessCode(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organization.setName(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organization.setDescription(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrganizationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organization.setOrganizationStatus(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organization.setActivationStatus(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizations() throws Exception {
        // Initialize the database
        insertedOrganization = organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].businessCode").value(hasItem(DEFAULT_BUSINESS_CODE)))
            .andExpect(jsonPath("$.[*].hierarchicalLevel").value(hasItem(DEFAULT_HIERARCHICAL_LEVEL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].businessHandlerClazz").value(hasItem(DEFAULT_BUSINESS_HANDLER_CLAZZ)))
            .andExpect(jsonPath("$.[*].mainContactPersonDetailsJSON").value(hasItem(DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].technicalEnvironmentsDetailsJSON").value(hasItem(DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].organizationStatus").value(hasItem(DEFAULT_ORGANIZATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoImgContentType").value(hasItem(DEFAULT_LOGO_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoImg").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO_IMG))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(organizationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(organizationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(organizationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(organizationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrganization() throws Exception {
        // Initialize the database
        insertedOrganization = organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.businessCode").value(DEFAULT_BUSINESS_CODE))
            .andExpect(jsonPath("$.hierarchicalLevel").value(DEFAULT_HIERARCHICAL_LEVEL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.businessHandlerClazz").value(DEFAULT_BUSINESS_HANDLER_CLAZZ))
            .andExpect(jsonPath("$.mainContactPersonDetailsJSON").value(DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON))
            .andExpect(jsonPath("$.technicalEnvironmentsDetailsJSON").value(DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON))
            .andExpect(jsonPath("$.organizationStatus").value(DEFAULT_ORGANIZATION_STATUS.toString()))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()))
            .andExpect(jsonPath("$.logoImgContentType").value(DEFAULT_LOGO_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoImg").value(Base64.getEncoder().encodeToString(DEFAULT_LOGO_IMG)));
    }

    @Test
    @Transactional
    void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganization() throws Exception {
        // Initialize the database
        insertedOrganization = organizationRepository.saveAndFlush(organization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .acronym(UPDATED_ACRONYM)
            .businessCode(UPDATED_BUSINESS_CODE)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .organizationStatus(UPDATED_ORGANIZATION_STATUS)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationToMatchAllProperties(updatedOrganization);
    }

    @Test
    @Transactional
    void putNonExistingOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organization.setId(longCount.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organization.setId(longCount.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organization.setId(longCount.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        insertedOrganization = organizationRepository.saveAndFlush(organization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .acronym(UPDATED_ACRONYM)
            .businessCode(UPDATED_BUSINESS_CODE)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .organizationStatus(UPDATED_ORGANIZATION_STATUS)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganization, organization),
            getPersistedOrganization(organization)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        insertedOrganization = organizationRepository.saveAndFlush(organization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .acronym(UPDATED_ACRONYM)
            .businessCode(UPDATED_BUSINESS_CODE)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .organizationStatus(UPDATED_ORGANIZATION_STATUS)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationUpdatableFieldsEquals(partialUpdatedOrganization, getPersistedOrganization(partialUpdatedOrganization));
    }

    @Test
    @Transactional
    void patchNonExistingOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organization.setId(longCount.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organization.setId(longCount.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organization.setId(longCount.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganization() throws Exception {
        // Initialize the database
        insertedOrganization = organizationRepository.saveAndFlush(organization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organization
        restOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organization.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationRepository.count();
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

    protected Organization getPersistedOrganization(Organization organization) {
        return organizationRepository.findById(organization.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationToMatchAllProperties(Organization expectedOrganization) {
        assertOrganizationAllPropertiesEquals(expectedOrganization, getPersistedOrganization(expectedOrganization));
    }

    protected void assertPersistedOrganizationToMatchUpdatableProperties(Organization expectedOrganization) {
        assertOrganizationAllUpdatablePropertiesEquals(expectedOrganization, getPersistedOrganization(expectedOrganization));
    }
}
