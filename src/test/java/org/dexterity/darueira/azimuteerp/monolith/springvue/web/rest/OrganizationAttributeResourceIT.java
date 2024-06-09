package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttributeAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationAttributeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationAttributeService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationAttributeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationAttributeMapper;
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
 * Integration tests for the {@link OrganizationAttributeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrganizationAttributeResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organization-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationAttributeRepository organizationAttributeRepository;

    @Mock
    private OrganizationAttributeRepository organizationAttributeRepositoryMock;

    @Autowired
    private OrganizationAttributeMapper organizationAttributeMapper;

    @Mock
    private OrganizationAttributeService organizationAttributeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationAttributeMockMvc;

    private OrganizationAttribute organizationAttribute;

    private OrganizationAttribute insertedOrganizationAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationAttribute createEntity(EntityManager em) {
        OrganizationAttribute organizationAttribute = new OrganizationAttribute()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        organizationAttribute.setOrganization(organization);
        return organizationAttribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationAttribute createUpdatedEntity(EntityManager em) {
        OrganizationAttribute organizationAttribute = new OrganizationAttribute()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createUpdatedEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        organizationAttribute.setOrganization(organization);
        return organizationAttribute;
    }

    @BeforeEach
    public void initTest() {
        organizationAttribute = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrganizationAttribute != null) {
            organizationAttributeRepository.delete(insertedOrganizationAttribute);
            insertedOrganizationAttribute = null;
        }
    }

    @Test
    @Transactional
    void createOrganizationAttribute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);
        var returnedOrganizationAttributeDTO = om.readValue(
            restOrganizationAttributeMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(organizationAttributeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationAttributeDTO.class
        );

        // Validate the OrganizationAttribute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganizationAttribute = organizationAttributeMapper.toEntity(returnedOrganizationAttributeDTO);
        assertOrganizationAttributeUpdatableFieldsEquals(
            returnedOrganizationAttribute,
            getPersistedOrganizationAttribute(returnedOrganizationAttribute)
        );

        insertedOrganizationAttribute = returnedOrganizationAttribute;
    }

    @Test
    @Transactional
    void createOrganizationAttributeWithExistingId() throws Exception {
        // Create the OrganizationAttribute with an existing ID
        organizationAttribute.setId(1L);
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationAttributeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganizationAttributes() throws Exception {
        // Initialize the database
        insertedOrganizationAttribute = organizationAttributeRepository.saveAndFlush(organizationAttribute);

        // Get all the organizationAttributeList
        restOrganizationAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationAttributesWithEagerRelationshipsIsEnabled() throws Exception {
        when(organizationAttributeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationAttributeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(organizationAttributeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationAttributesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(organizationAttributeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationAttributeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(organizationAttributeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrganizationAttribute() throws Exception {
        // Initialize the database
        insertedOrganizationAttribute = organizationAttributeRepository.saveAndFlush(organizationAttribute);

        // Get the organizationAttribute
        restOrganizationAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationAttribute() throws Exception {
        // Get the organizationAttribute
        restOrganizationAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationAttribute() throws Exception {
        // Initialize the database
        insertedOrganizationAttribute = organizationAttributeRepository.saveAndFlush(organizationAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationAttribute
        OrganizationAttribute updatedOrganizationAttribute = organizationAttributeRepository
            .findById(organizationAttribute.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationAttribute are not directly saved in db
        em.detach(updatedOrganizationAttribute);
        updatedOrganizationAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).attributeValue(UPDATED_ATTRIBUTE_VALUE);
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(updatedOrganizationAttribute);

        restOrganizationAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationAttributeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationAttributeToMatchAllProperties(updatedOrganizationAttribute);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationAttribute.setId(longCount.incrementAndGet());

        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationAttributeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationAttribute.setId(longCount.incrementAndGet());

        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationAttribute.setId(longCount.incrementAndGet());

        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationAttributeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationAttribute = organizationAttributeRepository.saveAndFlush(organizationAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationAttribute using partial update
        OrganizationAttribute partialUpdatedOrganizationAttribute = new OrganizationAttribute();
        partialUpdatedOrganizationAttribute.setId(organizationAttribute.getId());

        partialUpdatedOrganizationAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restOrganizationAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationAttribute.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationAttribute))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationAttributeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationAttribute, organizationAttribute),
            getPersistedOrganizationAttribute(organizationAttribute)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationAttribute = organizationAttributeRepository.saveAndFlush(organizationAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationAttribute using partial update
        OrganizationAttribute partialUpdatedOrganizationAttribute = new OrganizationAttribute();
        partialUpdatedOrganizationAttribute.setId(organizationAttribute.getId());

        partialUpdatedOrganizationAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restOrganizationAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationAttribute.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationAttribute))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationAttributeUpdatableFieldsEquals(
            partialUpdatedOrganizationAttribute,
            getPersistedOrganizationAttribute(partialUpdatedOrganizationAttribute)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationAttribute.setId(longCount.incrementAndGet());

        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationAttributeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationAttribute.setId(longCount.incrementAndGet());

        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationAttribute.setId(longCount.incrementAndGet());

        // Create the OrganizationAttribute
        OrganizationAttributeDTO organizationAttributeDTO = organizationAttributeMapper.toDto(organizationAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationAttributeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationAttribute() throws Exception {
        // Initialize the database
        insertedOrganizationAttribute = organizationAttributeRepository.saveAndFlush(organizationAttribute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationAttribute
        restOrganizationAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationAttribute.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationAttributeRepository.count();
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

    protected OrganizationAttribute getPersistedOrganizationAttribute(OrganizationAttribute organizationAttribute) {
        return organizationAttributeRepository.findById(organizationAttribute.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationAttributeToMatchAllProperties(OrganizationAttribute expectedOrganizationAttribute) {
        assertOrganizationAttributeAllPropertiesEquals(
            expectedOrganizationAttribute,
            getPersistedOrganizationAttribute(expectedOrganizationAttribute)
        );
    }

    protected void assertPersistedOrganizationAttributeToMatchUpdatableProperties(OrganizationAttribute expectedOrganizationAttribute) {
        assertOrganizationAttributeAllUpdatablePropertiesEquals(
            expectedOrganizationAttribute,
            getPersistedOrganizationAttribute(expectedOrganizationAttribute)
        );
    }
}
