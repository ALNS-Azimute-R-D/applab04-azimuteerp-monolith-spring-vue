package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomainAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationDomainRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationDomainService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDomainDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationDomainMapper;
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
 * Integration tests for the {@link OrganizationDomainResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrganizationDomainResourceIT {

    private static final String DEFAULT_DOMAIN_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VERIFIED = false;
    private static final Boolean UPDATED_IS_VERIFIED = true;

    private static final String DEFAULT_BUSINESS_HANDLER_CLAZZ = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_HANDLER_CLAZZ = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/organization-domains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationDomainRepository organizationDomainRepository;

    @Mock
    private OrganizationDomainRepository organizationDomainRepositoryMock;

    @Autowired
    private OrganizationDomainMapper organizationDomainMapper;

    @Mock
    private OrganizationDomainService organizationDomainServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationDomainMockMvc;

    private OrganizationDomain organizationDomain;

    private OrganizationDomain insertedOrganizationDomain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationDomain createEntity(EntityManager em) {
        OrganizationDomain organizationDomain = new OrganizationDomain()
            .domainAcronym(DEFAULT_DOMAIN_ACRONYM)
            .name(DEFAULT_NAME)
            .isVerified(DEFAULT_IS_VERIFIED)
            .businessHandlerClazz(DEFAULT_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        organizationDomain.setOrganization(organization);
        return organizationDomain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationDomain createUpdatedEntity(EntityManager em) {
        OrganizationDomain organizationDomain = new OrganizationDomain()
            .domainAcronym(UPDATED_DOMAIN_ACRONYM)
            .name(UPDATED_NAME)
            .isVerified(UPDATED_IS_VERIFIED)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createUpdatedEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        organizationDomain.setOrganization(organization);
        return organizationDomain;
    }

    @BeforeEach
    public void initTest() {
        organizationDomain = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrganizationDomain != null) {
            organizationDomainRepository.delete(insertedOrganizationDomain);
            insertedOrganizationDomain = null;
        }
    }

    @Test
    @Transactional
    void createOrganizationDomain() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);
        var returnedOrganizationDomainDTO = om.readValue(
            restOrganizationDomainMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(organizationDomainDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationDomainDTO.class
        );

        // Validate the OrganizationDomain in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganizationDomain = organizationDomainMapper.toEntity(returnedOrganizationDomainDTO);
        assertOrganizationDomainUpdatableFieldsEquals(
            returnedOrganizationDomain,
            getPersistedOrganizationDomain(returnedOrganizationDomain)
        );

        insertedOrganizationDomain = returnedOrganizationDomain;
    }

    @Test
    @Transactional
    void createOrganizationDomainWithExistingId() throws Exception {
        // Create the OrganizationDomain with an existing ID
        organizationDomain.setId(1L);
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationDomainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDomainAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationDomain.setDomainAcronym(null);

        // Create the OrganizationDomain, which fails.
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        restOrganizationDomainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationDomain.setName(null);

        // Create the OrganizationDomain, which fails.
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        restOrganizationDomainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsVerifiedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationDomain.setIsVerified(null);

        // Create the OrganizationDomain, which fails.
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        restOrganizationDomainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationDomain.setActivationStatus(null);

        // Create the OrganizationDomain, which fails.
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        restOrganizationDomainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizationDomains() throws Exception {
        // Initialize the database
        insertedOrganizationDomain = organizationDomainRepository.saveAndFlush(organizationDomain);

        // Get all the organizationDomainList
        restOrganizationDomainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].domainAcronym").value(hasItem(DEFAULT_DOMAIN_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isVerified").value(hasItem(DEFAULT_IS_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].businessHandlerClazz").value(hasItem(DEFAULT_BUSINESS_HANDLER_CLAZZ)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationDomainsWithEagerRelationshipsIsEnabled() throws Exception {
        when(organizationDomainServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationDomainMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(organizationDomainServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationDomainsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(organizationDomainServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationDomainMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(organizationDomainRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrganizationDomain() throws Exception {
        // Initialize the database
        insertedOrganizationDomain = organizationDomainRepository.saveAndFlush(organizationDomain);

        // Get the organizationDomain
        restOrganizationDomainMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationDomain.getId().intValue()))
            .andExpect(jsonPath("$.domainAcronym").value(DEFAULT_DOMAIN_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isVerified").value(DEFAULT_IS_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.businessHandlerClazz").value(DEFAULT_BUSINESS_HANDLER_CLAZZ))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationDomain() throws Exception {
        // Get the organizationDomain
        restOrganizationDomainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationDomain() throws Exception {
        // Initialize the database
        insertedOrganizationDomain = organizationDomainRepository.saveAndFlush(organizationDomain);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationDomain
        OrganizationDomain updatedOrganizationDomain = organizationDomainRepository.findById(organizationDomain.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationDomain are not directly saved in db
        em.detach(updatedOrganizationDomain);
        updatedOrganizationDomain
            .domainAcronym(UPDATED_DOMAIN_ACRONYM)
            .name(UPDATED_NAME)
            .isVerified(UPDATED_IS_VERIFIED)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(updatedOrganizationDomain);

        restOrganizationDomainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDomainDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationDomainToMatchAllProperties(updatedOrganizationDomain);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationDomain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationDomain.setId(longCount.incrementAndGet());

        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationDomainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDomainDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationDomain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationDomain.setId(longCount.incrementAndGet());

        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationDomainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationDomain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationDomain.setId(longCount.incrementAndGet());

        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationDomainMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationDomainWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationDomain = organizationDomainRepository.saveAndFlush(organizationDomain);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationDomain using partial update
        OrganizationDomain partialUpdatedOrganizationDomain = new OrganizationDomain();
        partialUpdatedOrganizationDomain.setId(organizationDomain.getId());

        partialUpdatedOrganizationDomain.domainAcronym(UPDATED_DOMAIN_ACRONYM).name(UPDATED_NAME).isVerified(UPDATED_IS_VERIFIED);

        restOrganizationDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationDomain.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationDomain))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationDomain in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationDomainUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationDomain, organizationDomain),
            getPersistedOrganizationDomain(organizationDomain)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationDomainWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationDomain = organizationDomainRepository.saveAndFlush(organizationDomain);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationDomain using partial update
        OrganizationDomain partialUpdatedOrganizationDomain = new OrganizationDomain();
        partialUpdatedOrganizationDomain.setId(organizationDomain.getId());

        partialUpdatedOrganizationDomain
            .domainAcronym(UPDATED_DOMAIN_ACRONYM)
            .name(UPDATED_NAME)
            .isVerified(UPDATED_IS_VERIFIED)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restOrganizationDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationDomain.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationDomain))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationDomain in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationDomainUpdatableFieldsEquals(
            partialUpdatedOrganizationDomain,
            getPersistedOrganizationDomain(partialUpdatedOrganizationDomain)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationDomain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationDomain.setId(longCount.incrementAndGet());

        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationDomainDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationDomain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationDomain.setId(longCount.incrementAndGet());

        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationDomainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationDomain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationDomain.setId(longCount.incrementAndGet());

        // Create the OrganizationDomain
        OrganizationDomainDTO organizationDomainDTO = organizationDomainMapper.toDto(organizationDomain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationDomainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationDomainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationDomain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationDomain() throws Exception {
        // Initialize the database
        insertedOrganizationDomain = organizationDomainRepository.saveAndFlush(organizationDomain);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationDomain
        restOrganizationDomainMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationDomain.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationDomainRepository.count();
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

    protected OrganizationDomain getPersistedOrganizationDomain(OrganizationDomain organizationDomain) {
        return organizationDomainRepository.findById(organizationDomain.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationDomainToMatchAllProperties(OrganizationDomain expectedOrganizationDomain) {
        assertOrganizationDomainAllPropertiesEquals(expectedOrganizationDomain, getPersistedOrganizationDomain(expectedOrganizationDomain));
    }

    protected void assertPersistedOrganizationDomainToMatchUpdatableProperties(OrganizationDomain expectedOrganizationDomain) {
        assertOrganizationDomainAllUpdatablePropertiesEquals(
            expectedOrganizationDomain,
            getPersistedOrganizationDomain(expectedOrganizationDomain)
        );
    }
}
