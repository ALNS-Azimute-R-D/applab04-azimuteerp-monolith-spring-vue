package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnitAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.BusinessUnitRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.BusinessUnitService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.BusinessUnitDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.BusinessUnitMapper;
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
 * Integration tests for the {@link BusinessUnitResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BusinessUnitResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_HIERARCHICAL_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_HIERARCHICAL_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/business-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private BusinessUnitRepository businessUnitRepositoryMock;

    @Autowired
    private BusinessUnitMapper businessUnitMapper;

    @Mock
    private BusinessUnitService businessUnitServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessUnitMockMvc;

    private BusinessUnit businessUnit;

    private BusinessUnit insertedBusinessUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit()
            .acronym(DEFAULT_ACRONYM)
            .hierarchicalLevel(DEFAULT_HIERARCHICAL_LEVEL)
            .name(DEFAULT_NAME)
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
        businessUnit.setOrganization(organization);
        return businessUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createUpdatedEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit()
            .acronym(UPDATED_ACRONYM)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
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
        businessUnit.setOrganization(organization);
        return businessUnit;
    }

    @BeforeEach
    public void initTest() {
        businessUnit = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBusinessUnit != null) {
            businessUnitRepository.delete(insertedBusinessUnit);
            insertedBusinessUnit = null;
        }
    }

    @Test
    @Transactional
    void createBusinessUnit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);
        var returnedBusinessUnitDTO = om.readValue(
            restBusinessUnitMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(businessUnitDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BusinessUnitDTO.class
        );

        // Validate the BusinessUnit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBusinessUnit = businessUnitMapper.toEntity(returnedBusinessUnitDTO);
        assertBusinessUnitUpdatableFieldsEquals(returnedBusinessUnit, getPersistedBusinessUnit(returnedBusinessUnit));

        insertedBusinessUnit = returnedBusinessUnit;
    }

    @Test
    @Transactional
    void createBusinessUnitWithExistingId() throws Exception {
        // Create the BusinessUnit with an existing ID
        businessUnit.setId(1L);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessUnitMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        businessUnit.setAcronym(null);

        // Create the BusinessUnit, which fails.
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        restBusinessUnitMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        businessUnit.setName(null);

        // Create the BusinessUnit, which fails.
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        restBusinessUnitMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        businessUnit.setActivationStatus(null);

        // Create the BusinessUnit, which fails.
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        restBusinessUnitMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBusinessUnits() throws Exception {
        // Initialize the database
        insertedBusinessUnit = businessUnitRepository.saveAndFlush(businessUnit);

        // Get all the businessUnitList
        restBusinessUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].hierarchicalLevel").value(hasItem(DEFAULT_HIERARCHICAL_LEVEL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBusinessUnitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(businessUnitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBusinessUnitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(businessUnitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBusinessUnitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(businessUnitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBusinessUnitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(businessUnitRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBusinessUnit() throws Exception {
        // Initialize the database
        insertedBusinessUnit = businessUnitRepository.saveAndFlush(businessUnit);

        // Get the businessUnit
        restBusinessUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, businessUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessUnit.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.hierarchicalLevel").value(DEFAULT_HIERARCHICAL_LEVEL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBusinessUnit() throws Exception {
        // Get the businessUnit
        restBusinessUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBusinessUnit() throws Exception {
        // Initialize the database
        insertedBusinessUnit = businessUnitRepository.saveAndFlush(businessUnit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the businessUnit
        BusinessUnit updatedBusinessUnit = businessUnitRepository.findById(businessUnit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBusinessUnit are not directly saved in db
        em.detach(updatedBusinessUnit);
        updatedBusinessUnit
            .acronym(UPDATED_ACRONYM)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(updatedBusinessUnit);

        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBusinessUnitToMatchAllProperties(updatedBusinessUnit);
    }

    @Test
    @Transactional
    void putNonExistingBusinessUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        businessUnit.setId(longCount.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        businessUnit.setId(longCount.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        businessUnit.setId(longCount.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessUnitWithPatch() throws Exception {
        // Initialize the database
        insertedBusinessUnit = businessUnitRepository.saveAndFlush(businessUnit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the businessUnit using partial update
        BusinessUnit partialUpdatedBusinessUnit = new BusinessUnit();
        partialUpdatedBusinessUnit.setId(businessUnit.getId());

        partialUpdatedBusinessUnit.hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL);

        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBusinessUnitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBusinessUnit, businessUnit),
            getPersistedBusinessUnit(businessUnit)
        );
    }

    @Test
    @Transactional
    void fullUpdateBusinessUnitWithPatch() throws Exception {
        // Initialize the database
        insertedBusinessUnit = businessUnitRepository.saveAndFlush(businessUnit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the businessUnit using partial update
        BusinessUnit partialUpdatedBusinessUnit = new BusinessUnit();
        partialUpdatedBusinessUnit.setId(businessUnit.getId());

        partialUpdatedBusinessUnit
            .acronym(UPDATED_ACRONYM)
            .hierarchicalLevel(UPDATED_HIERARCHICAL_LEVEL)
            .name(UPDATED_NAME)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBusinessUnit))
            )
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBusinessUnitUpdatableFieldsEquals(partialUpdatedBusinessUnit, getPersistedBusinessUnit(partialUpdatedBusinessUnit));
    }

    @Test
    @Transactional
    void patchNonExistingBusinessUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        businessUnit.setId(longCount.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessUnitDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        businessUnit.setId(longCount.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessUnit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        businessUnit.setId(longCount.incrementAndGet());

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.toDto(businessUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(businessUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessUnit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessUnit() throws Exception {
        // Initialize the database
        insertedBusinessUnit = businessUnitRepository.saveAndFlush(businessUnit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the businessUnit
        restBusinessUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessUnit.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return businessUnitRepository.count();
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

    protected BusinessUnit getPersistedBusinessUnit(BusinessUnit businessUnit) {
        return businessUnitRepository.findById(businessUnit.getId()).orElseThrow();
    }

    protected void assertPersistedBusinessUnitToMatchAllProperties(BusinessUnit expectedBusinessUnit) {
        assertBusinessUnitAllPropertiesEquals(expectedBusinessUnit, getPersistedBusinessUnit(expectedBusinessUnit));
    }

    protected void assertPersistedBusinessUnitToMatchUpdatableProperties(BusinessUnit expectedBusinessUnit) {
        assertBusinessUnitAllUpdatablePropertiesEquals(expectedBusinessUnit, getPersistedBusinessUnit(expectedBusinessUnit));
    }
}
