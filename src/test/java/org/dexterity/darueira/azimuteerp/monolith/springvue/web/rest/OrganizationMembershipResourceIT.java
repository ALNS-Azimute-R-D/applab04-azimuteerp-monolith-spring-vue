package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembershipAsserts.*;
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
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationMembershipRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationMembershipService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMembershipDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationMembershipMapper;
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
 * Integration tests for the {@link OrganizationMembershipResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrganizationMembershipResourceIT {

    private static final LocalDate DEFAULT_JOINED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/organization-memberships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationMembershipRepository organizationMembershipRepository;

    @Mock
    private OrganizationMembershipRepository organizationMembershipRepositoryMock;

    @Autowired
    private OrganizationMembershipMapper organizationMembershipMapper;

    @Mock
    private OrganizationMembershipService organizationMembershipServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMembershipMockMvc;

    private OrganizationMembership organizationMembership;

    private OrganizationMembership insertedOrganizationMembership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationMembership createEntity(EntityManager em) {
        OrganizationMembership organizationMembership = new OrganizationMembership()
            .joinedAt(DEFAULT_JOINED_AT)
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
        organizationMembership.setOrganization(organization);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        organizationMembership.setPerson(person);
        return organizationMembership;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationMembership createUpdatedEntity(EntityManager em) {
        OrganizationMembership organizationMembership = new OrganizationMembership()
            .joinedAt(UPDATED_JOINED_AT)
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
        organizationMembership.setOrganization(organization);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        organizationMembership.setPerson(person);
        return organizationMembership;
    }

    @BeforeEach
    public void initTest() {
        organizationMembership = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrganizationMembership != null) {
            organizationMembershipRepository.delete(insertedOrganizationMembership);
            insertedOrganizationMembership = null;
        }
    }

    @Test
    @Transactional
    void createOrganizationMembership() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);
        var returnedOrganizationMembershipDTO = om.readValue(
            restOrganizationMembershipMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(organizationMembershipDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationMembershipDTO.class
        );

        // Validate the OrganizationMembership in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganizationMembership = organizationMembershipMapper.toEntity(returnedOrganizationMembershipDTO);
        assertOrganizationMembershipUpdatableFieldsEquals(
            returnedOrganizationMembership,
            getPersistedOrganizationMembership(returnedOrganizationMembership)
        );

        insertedOrganizationMembership = returnedOrganizationMembership;
    }

    @Test
    @Transactional
    void createOrganizationMembershipWithExistingId() throws Exception {
        // Create the OrganizationMembership with an existing ID
        organizationMembership.setId(1L);
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkJoinedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationMembership.setJoinedAt(null);

        // Create the OrganizationMembership, which fails.
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        restOrganizationMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationMembership.setActivationStatus(null);

        // Create the OrganizationMembership, which fails.
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        restOrganizationMembershipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizationMemberships() throws Exception {
        // Initialize the database
        insertedOrganizationMembership = organizationMembershipRepository.saveAndFlush(organizationMembership);

        // Get all the organizationMembershipList
        restOrganizationMembershipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationMembership.getId().intValue())))
            .andExpect(jsonPath("$.[*].joinedAt").value(hasItem(DEFAULT_JOINED_AT.toString())))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationMembershipsWithEagerRelationshipsIsEnabled() throws Exception {
        when(organizationMembershipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationMembershipMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(organizationMembershipServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrganizationMembershipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(organizationMembershipServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrganizationMembershipMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(organizationMembershipRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrganizationMembership() throws Exception {
        // Initialize the database
        insertedOrganizationMembership = organizationMembershipRepository.saveAndFlush(organizationMembership);

        // Get the organizationMembership
        restOrganizationMembershipMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationMembership.getId().intValue()))
            .andExpect(jsonPath("$.joinedAt").value(DEFAULT_JOINED_AT.toString()))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationMembership() throws Exception {
        // Get the organizationMembership
        restOrganizationMembershipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationMembership() throws Exception {
        // Initialize the database
        insertedOrganizationMembership = organizationMembershipRepository.saveAndFlush(organizationMembership);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMembership
        OrganizationMembership updatedOrganizationMembership = organizationMembershipRepository
            .findById(organizationMembership.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationMembership are not directly saved in db
        em.detach(updatedOrganizationMembership);
        updatedOrganizationMembership.joinedAt(UPDATED_JOINED_AT).activationStatus(UPDATED_ACTIVATION_STATUS);
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(updatedOrganizationMembership);

        restOrganizationMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationMembershipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationMembershipToMatchAllProperties(updatedOrganizationMembership);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationMembership() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMembership.setId(longCount.incrementAndGet());

        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationMembershipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationMembership() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMembership.setId(longCount.incrementAndGet());

        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMembershipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationMembership() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMembership.setId(longCount.incrementAndGet());

        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMembershipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationMembershipWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationMembership = organizationMembershipRepository.saveAndFlush(organizationMembership);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMembership using partial update
        OrganizationMembership partialUpdatedOrganizationMembership = new OrganizationMembership();
        partialUpdatedOrganizationMembership.setId(organizationMembership.getId());

        partialUpdatedOrganizationMembership.activationStatus(UPDATED_ACTIVATION_STATUS);

        restOrganizationMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationMembership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationMembership))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMembership in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationMembershipUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationMembership, organizationMembership),
            getPersistedOrganizationMembership(organizationMembership)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationMembershipWithPatch() throws Exception {
        // Initialize the database
        insertedOrganizationMembership = organizationMembershipRepository.saveAndFlush(organizationMembership);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMembership using partial update
        OrganizationMembership partialUpdatedOrganizationMembership = new OrganizationMembership();
        partialUpdatedOrganizationMembership.setId(organizationMembership.getId());

        partialUpdatedOrganizationMembership.joinedAt(UPDATED_JOINED_AT).activationStatus(UPDATED_ACTIVATION_STATUS);

        restOrganizationMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationMembership.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationMembership))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMembership in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationMembershipUpdatableFieldsEquals(
            partialUpdatedOrganizationMembership,
            getPersistedOrganizationMembership(partialUpdatedOrganizationMembership)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationMembership() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMembership.setId(longCount.incrementAndGet());

        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationMembershipDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationMembership() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMembership.setId(longCount.incrementAndGet());

        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationMembership() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMembership.setId(longCount.incrementAndGet());

        // Create the OrganizationMembership
        OrganizationMembershipDTO organizationMembershipDTO = organizationMembershipMapper.toDto(organizationMembership);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMembershipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMembershipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationMembership in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationMembership() throws Exception {
        // Initialize the database
        insertedOrganizationMembership = organizationMembershipRepository.saveAndFlush(organizationMembership);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationMembership
        restOrganizationMembershipMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationMembership.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationMembershipRepository.count();
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

    protected OrganizationMembership getPersistedOrganizationMembership(OrganizationMembership organizationMembership) {
        return organizationMembershipRepository.findById(organizationMembership.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationMembershipToMatchAllProperties(OrganizationMembership expectedOrganizationMembership) {
        assertOrganizationMembershipAllPropertiesEquals(
            expectedOrganizationMembership,
            getPersistedOrganizationMembership(expectedOrganizationMembership)
        );
    }

    protected void assertPersistedOrganizationMembershipToMatchUpdatableProperties(OrganizationMembership expectedOrganizationMembership) {
        assertOrganizationMembershipAllUpdatablePropertiesEquals(
            expectedOrganizationMembership,
            getPersistedOrganizationMembership(expectedOrganizationMembership)
        );
    }
}
