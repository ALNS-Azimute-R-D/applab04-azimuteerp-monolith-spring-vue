package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRoleAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationRoleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationRoleService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationRoleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationRoleMapper;
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
 * Integration tests for the {@link OrganizationRoleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrganizationRoleResourceIT {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/organization-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationRoleRepository organizationRoleRepository;

    @Mock
    private OrganizationRoleRepository organizationRoleRepositoryMock;

    @Autowired
    private OrganizationRoleMapper organizationRoleMapper;

    @Mock
    private OrganizationRoleService organizationRoleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationRoleMockMvc;

    private OrganizationRole organizationRole;

    private OrganizationRole insertedOrganizationRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationRole createEntity(EntityManager em) {
        OrganizationRole organizationRole = new OrganizationRole().roleName(DEFAULT_ROLE_NAME).activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        organizationRole.setOrganization(organization);
        return organizationRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationRole createUpdatedEntity(EntityManager em) {
        OrganizationRole organizationRole = new OrganizationRole().roleName(UPDATED_ROLE_NAME).activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organization = OrganizationResourceIT.createUpdatedEntity(em);
            em.persist(organization);
            em.flush();
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        organizationRole.setOrganization(organization);
        return organizationRole;
    }

    @BeforeEach
    public void initTest() {
        organizationRole = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrganizationRole != null) {
            organizationRoleRepository.delete(insertedOrganizationRole);
            insertedOrganizationRole = null;
        }
    }

    @Test
    @Transactional
    void createOrganizationRole() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);
        var returnedOrganizationRoleDTO = om.readValue(
            restOrganizationRoleMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(organizationRoleDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationRoleDTO.class
        );

        // Validate the OrganizationRole in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganizationRole = organizationRoleMapper.toEntity(returnedOrganizationRoleDTO);
        assertOrganizationRoleUpdatableFieldsEquals(returnedOrganizationRole, getPersistedOrganizationRole(returnedOrganizationRole));

        insertedOrganizationRole = returnedOrganizationRole;
    }

    @Test
    @Transactional
    void createOrganizationRoleWithExistingId() throws Exception {
        // Create the OrganizationRole with an existing ID
        organizationRole.setId(1L);
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationRoleMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRoleNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationRole.setRoleName(null);

        // Create the OrganizationRole, which fails.
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        restOrganizationRoleMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationRole.setActivationStatus(null);

        // Create the OrganizationRole, which fails.
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        restOrganizationRoleMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizationRoles() throws Exception {
        // Initialize the database
        insertedOrganizationRole = organizationRoleRepository.saveAndFlush(organizationRole);

        // Get all the organizationRoleList
        restOrganizationRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationRolesWithEagerRelationshipsIsEnabled() throws Exception {
        when(organizationRoleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationRoleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(organizationRoleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(organizationRoleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationRoleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(organizationRoleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrganizationRole() throws Exception {
        // Initialize the database
        insertedOrganizationRole = organizationRoleRepository.saveAndFlush(organizationRole);

        // Get the organizationRole
        restOrganizationRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationRole.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationRole() throws Exception {
        // Get the organizationRole
        restOrganizationRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationRole() throws Exception {
        // Initialize the database
        insertedOrganizationRole = organizationRoleRepository.saveAndFlush(organizationRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationRole
        OrganizationRole updatedOrganizationRole = organizationRoleRepository.findById(organizationRole.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationRole are not directly saved in db
        em.detach(updatedOrganizationRole);
        updatedOrganizationRole.roleName(UPDATED_ROLE_NAME).activationStatus(UPDATED_ACTIVATION_STATUS);
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(updatedOrganizationRole);

        restOrganizationRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationRoleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationRoleToMatchAllProperties(updatedOrganizationRole);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationRole.setId(longCount.incrementAndGet());

        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationRoleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationRole.setId(longCount.incrementAndGet());

        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationRole.setId(longCount.incrementAndGet());

        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationRoleMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationRoleWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationRole = organizationRoleRepository.saveAndFlush(organizationRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationRole using partial update
        OrganizationRole partialUpdatedOrganizationRole = new OrganizationRole();
        partialUpdatedOrganizationRole.setId(organizationRole.getId());

        partialUpdatedOrganizationRole.roleName(UPDATED_ROLE_NAME).activationStatus(UPDATED_ACTIVATION_STATUS);

        restOrganizationRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationRole.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationRole))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationRole in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationRoleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationRole, organizationRole),
            getPersistedOrganizationRole(organizationRole)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationRoleWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationRole = organizationRoleRepository.saveAndFlush(organizationRole);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationRole using partial update
        OrganizationRole partialUpdatedOrganizationRole = new OrganizationRole();
        partialUpdatedOrganizationRole.setId(organizationRole.getId());

        partialUpdatedOrganizationRole.roleName(UPDATED_ROLE_NAME).activationStatus(UPDATED_ACTIVATION_STATUS);

        restOrganizationRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationRole.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationRole))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationRole in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationRoleUpdatableFieldsEquals(
            partialUpdatedOrganizationRole,
            getPersistedOrganizationRole(partialUpdatedOrganizationRole)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationRole.setId(longCount.incrementAndGet());

        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationRoleDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationRole.setId(longCount.incrementAndGet());

        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationRole() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationRole.setId(longCount.incrementAndGet());

        // Create the OrganizationRole
        OrganizationRoleDTO organizationRoleDTO = organizationRoleMapper.toDto(organizationRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationRoleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationRole in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationRole() throws Exception {
        // Initialize the database
        insertedOrganizationRole = organizationRoleRepository.saveAndFlush(organizationRole);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationRole
        restOrganizationRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationRole.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationRoleRepository.count();
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

    protected OrganizationRole getPersistedOrganizationRole(OrganizationRole organizationRole) {
        return organizationRoleRepository.findById(organizationRole.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationRoleToMatchAllProperties(OrganizationRole expectedOrganizationRole) {
        assertOrganizationRoleAllPropertiesEquals(expectedOrganizationRole, getPersistedOrganizationRole(expectedOrganizationRole));
    }

    protected void assertPersistedOrganizationRoleToMatchUpdatableProperties(OrganizationRole expectedOrganizationRole) {
        assertOrganizationRoleAllUpdatablePropertiesEquals(
            expectedOrganizationRole,
            getPersistedOrganizationRole(expectedOrganizationRole)
        );
    }
}
