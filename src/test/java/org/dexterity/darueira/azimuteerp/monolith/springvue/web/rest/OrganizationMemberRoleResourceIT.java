package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRoleAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationMemberRoleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMemberRoleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationMemberRoleMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganizationMemberRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationMemberRoleResourceIT {

    private static final LocalDate DEFAULT_JOINED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/organization-member-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationMemberRoleRepository organizationMemberRoleRepository;

    @Autowired
    private OrganizationMemberRoleMapper organizationMemberRoleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMemberRoleMockMvc;

    private OrganizationMemberRole organizationMemberRole;

    private OrganizationMemberRole insertedOrganizationMemberRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationMemberRole createEntity(EntityManager em) {
        OrganizationMemberRole organizationMemberRole = new OrganizationMemberRole().joinedAt(DEFAULT_JOINED_AT);
        // Add required entity
        OrganizationMembership organizationMembership;
        if (TestUtil.findAll(em, OrganizationMembership.class).isEmpty()) {
            organizationMembership = OrganizationMembershipResourceIT.createEntity(em);
            em.persist(organizationMembership);
            em.flush();
        } else {
            organizationMembership = TestUtil.findAll(em, OrganizationMembership.class).get(0);
        }
        organizationMemberRole.setOrganizationMembership(organizationMembership);
        // Add required entity
        OrganizationRole organizationRole;
        if (TestUtil.findAll(em, OrganizationRole.class).isEmpty()) {
            organizationRole = OrganizationRoleResourceIT.createEntity(em);
            em.persist(organizationRole);
            em.flush();
        } else {
            organizationRole = TestUtil.findAll(em, OrganizationRole.class).get(0);
        }
        organizationMemberRole.setOrganizationRole(organizationRole);
        return organizationMemberRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationMemberRole createUpdatedEntity(EntityManager em) {
        OrganizationMemberRole organizationMemberRole = new OrganizationMemberRole().joinedAt(UPDATED_JOINED_AT);
        // Add required entity
        OrganizationMembership organizationMembership;
        if (TestUtil.findAll(em, OrganizationMembership.class).isEmpty()) {
            organizationMembership = OrganizationMembershipResourceIT.createUpdatedEntity(em);
            em.persist(organizationMembership);
            em.flush();
        } else {
            organizationMembership = TestUtil.findAll(em, OrganizationMembership.class).get(0);
        }
        organizationMemberRole.setOrganizationMembership(organizationMembership);
        // Add required entity
        OrganizationRole organizationRole;
        if (TestUtil.findAll(em, OrganizationRole.class).isEmpty()) {
            organizationRole = OrganizationRoleResourceIT.createUpdatedEntity(em);
            em.persist(organizationRole);
            em.flush();
        } else {
            organizationRole = TestUtil.findAll(em, OrganizationRole.class).get(0);
        }
        organizationMemberRole.setOrganizationRole(organizationRole);
        return organizationMemberRole;
    }

    @BeforeEach
    public void initTest() {
        organizationMemberRole = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrganizationMemberRole != null) {
            organizationMemberRoleRepository.delete(insertedOrganizationMemberRole);
            insertedOrganizationMemberRole = null;
        }
    }

    @Test
    @Transactional
    void createOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);
        var returnedOrganizationMemberRoleDTO = om.readValue(
            restOrganizationMemberRoleMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(organizationMemberRoleDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationMemberRoleDTO.class
        );

        // Validate the OrganizationMemberRole in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganizationMemberRole = organizationMemberRoleMapper.toEntity(returnedOrganizationMemberRoleDTO);
        assertOrganizationMemberRoleUpdatableFieldsEquals(
            returnedOrganizationMemberRole,
            getPersistedOrganizationMemberRole(returnedOrganizationMemberRole)
        );

        insertedOrganizationMemberRole = returnedOrganizationMemberRole;
    }

    @Test
    @Transactional
    void createOrganizationMemberRoleWithExistingId() throws Exception {
        // Create the OrganizationMemberRole with an existing ID
        organizationMemberRole.setId(1L);
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMemberRoleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkJoinedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationMemberRole.setJoinedAt(null);

        // Create the OrganizationMemberRole, which fails.
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        restOrganizationMemberRoleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizationMemberRoles() throws Exception {
        // Initialize the database
        insertedOrganizationMemberRole = organizationMemberRoleRepository.saveAndFlush(organizationMemberRole);

        // Get all the organizationMemberRoleList
        restOrganizationMemberRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationMemberRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].joinedAt").value(hasItem(DEFAULT_JOINED_AT.toString())));
    }

    @Test
    @Transactional
    void getOrganizationMemberRole() throws Exception {
        // Initialize the database
        insertedOrganizationMemberRole = organizationMemberRoleRepository.saveAndFlush(organizationMemberRole);

        // Get the organizationMemberRole
        restOrganizationMemberRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationMemberRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationMemberRole.getId().intValue()))
            .andExpect(jsonPath("$.joinedAt").value(DEFAULT_JOINED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationMemberRole() throws Exception {
        // Get the organizationMemberRole
        restOrganizationMemberRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationMemberRole() throws Exception {
        // Initialize the database
        insertedOrganizationMemberRole = organizationMemberRoleRepository.saveAndFlush(organizationMemberRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMemberRole
        OrganizationMemberRole updatedOrganizationMemberRole = organizationMemberRoleRepository
            .findById(organizationMemberRole.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationMemberRole are not directly saved in db
        em.detach(updatedOrganizationMemberRole);
        updatedOrganizationMemberRole.joinedAt(UPDATED_JOINED_AT);
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(updatedOrganizationMemberRole);

        restOrganizationMemberRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationMemberRoleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationMemberRoleToMatchAllProperties(updatedOrganizationMemberRole);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMemberRole.setId(longCount.incrementAndGet());

        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMemberRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationMemberRoleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMemberRole.setId(longCount.incrementAndGet());

        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMemberRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMemberRole.setId(longCount.incrementAndGet());

        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMemberRoleMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationMemberRoleWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationMemberRole = organizationMemberRoleRepository.saveAndFlush(organizationMemberRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMemberRole using partial update
        OrganizationMemberRole partialUpdatedOrganizationMemberRole = new OrganizationMemberRole();
        partialUpdatedOrganizationMemberRole.setId(organizationMemberRole.getId());

        restOrganizationMemberRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationMemberRole.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationMemberRole))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMemberRole in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationMemberRoleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationMemberRole, organizationMemberRole),
            getPersistedOrganizationMemberRole(organizationMemberRole)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationMemberRoleWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationMemberRole = organizationMemberRoleRepository.saveAndFlush(organizationMemberRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMemberRole using partial update
        OrganizationMemberRole partialUpdatedOrganizationMemberRole = new OrganizationMemberRole();
        partialUpdatedOrganizationMemberRole.setId(organizationMemberRole.getId());

        partialUpdatedOrganizationMemberRole.joinedAt(UPDATED_JOINED_AT);

        restOrganizationMemberRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationMemberRole.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationMemberRole))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMemberRole in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationMemberRoleUpdatableFieldsEquals(
            partialUpdatedOrganizationMemberRole,
            getPersistedOrganizationMemberRole(partialUpdatedOrganizationMemberRole)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMemberRole.setId(longCount.incrementAndGet());

        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMemberRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationMemberRoleDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMemberRole.setId(longCount.incrementAndGet());

        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMemberRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationMemberRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMemberRole.setId(longCount.incrementAndGet());

        // Create the OrganizationMemberRole
        OrganizationMemberRoleDTO organizationMemberRoleDTO = organizationMemberRoleMapper.toDto(organizationMemberRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMemberRoleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMemberRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationMemberRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationMemberRole() throws Exception {
        // Initialize the database
        insertedOrganizationMemberRole = organizationMemberRoleRepository.saveAndFlush(organizationMemberRole);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationMemberRole
        restOrganizationMemberRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationMemberRole.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationMemberRoleRepository.count();
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

    protected OrganizationMemberRole getPersistedOrganizationMemberRole(OrganizationMemberRole organizationMemberRole) {
        return organizationMemberRoleRepository.findById(organizationMemberRole.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationMemberRoleToMatchAllProperties(OrganizationMemberRole expectedOrganizationMemberRole) {
        assertOrganizationMemberRoleAllPropertiesEquals(
            expectedOrganizationMemberRole,
            getPersistedOrganizationMemberRole(expectedOrganizationMemberRole)
        );
    }

    protected void assertPersistedOrganizationMemberRoleToMatchUpdatableProperties(OrganizationMemberRole expectedOrganizationMemberRole) {
        assertOrganizationMemberRoleAllUpdatablePropertiesEquals(
            expectedOrganizationMemberRole,
            getPersistedOrganizationMemberRole(expectedOrganizationMemberRole)
        );
    }
}
