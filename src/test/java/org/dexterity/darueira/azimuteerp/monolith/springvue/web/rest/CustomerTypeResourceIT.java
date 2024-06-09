package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTypeAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.CustomerTypeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CustomerTypeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.CustomerTypeMapper;
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
 * Integration tests for the {@link CustomerTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_HANDLER_CLAZZ = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_HANDLER_CLAZZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customer-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private CustomerTypeMapper customerTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerTypeMockMvc;

    private CustomerType customerType;

    private CustomerType insertedCustomerType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerType createEntity(EntityManager em) {
        CustomerType customerType = new CustomerType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .businessHandlerClazz(DEFAULT_BUSINESS_HANDLER_CLAZZ);
        return customerType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerType createUpdatedEntity(EntityManager em) {
        CustomerType customerType = new CustomerType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);
        return customerType;
    }

    @BeforeEach
    public void initTest() {
        customerType = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCustomerType != null) {
            customerTypeRepository.delete(insertedCustomerType);
            insertedCustomerType = null;
        }
    }

    @Test
    @Transactional
    void createCustomerType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);
        var returnedCustomerTypeDTO = om.readValue(
            restCustomerTypeMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customerTypeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CustomerTypeDTO.class
        );

        // Validate the CustomerType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCustomerType = customerTypeMapper.toEntity(returnedCustomerTypeDTO);
        assertCustomerTypeUpdatableFieldsEquals(returnedCustomerType, getPersistedCustomerType(returnedCustomerType));

        insertedCustomerType = returnedCustomerType;
    }

    @Test
    @Transactional
    void createCustomerTypeWithExistingId() throws Exception {
        // Create the CustomerType with an existing ID
        customerType.setId(1L);
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTypeMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        customerType.setName(null);

        // Create the CustomerType, which fails.
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        restCustomerTypeMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomerTypes() throws Exception {
        // Initialize the database
        insertedCustomerType = customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList
        restCustomerTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].businessHandlerClazz").value(hasItem(DEFAULT_BUSINESS_HANDLER_CLAZZ)));
    }

    @Test
    @Transactional
    void getCustomerType() throws Exception {
        // Initialize the database
        insertedCustomerType = customerTypeRepository.saveAndFlush(customerType);

        // Get the customerType
        restCustomerTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, customerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.businessHandlerClazz").value(DEFAULT_BUSINESS_HANDLER_CLAZZ));
    }

    @Test
    @Transactional
    void getNonExistingCustomerType() throws Exception {
        // Get the customerType
        restCustomerTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomerType() throws Exception {
        // Initialize the database
        insertedCustomerType = customerTypeRepository.saveAndFlush(customerType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customerType
        CustomerType updatedCustomerType = customerTypeRepository.findById(customerType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCustomerType are not directly saved in db
        em.detach(updatedCustomerType);
        updatedCustomerType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(updatedCustomerType);

        restCustomerTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCustomerTypeToMatchAllProperties(updatedCustomerType);
    }

    @Test
    @Transactional
    void putNonExistingCustomerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customerType.setId(longCount.incrementAndGet());

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customerType.setId(longCount.incrementAndGet());

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customerType.setId(longCount.incrementAndGet());

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerTypeWithPatch() throws Exception {
        // Initialize the database
        insertedCustomerType = customerTypeRepository.saveAndFlush(customerType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customerType using partial update
        CustomerType partialUpdatedCustomerType = new CustomerType();
        partialUpdatedCustomerType.setId(customerType.getId());

        partialUpdatedCustomerType.name(UPDATED_NAME).businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);

        restCustomerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCustomerType))
            )
            .andExpect(status().isOk());

        // Validate the CustomerType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCustomerTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCustomerType, customerType),
            getPersistedCustomerType(customerType)
        );
    }

    @Test
    @Transactional
    void fullUpdateCustomerTypeWithPatch() throws Exception {
        // Initialize the database
        insertedCustomerType = customerTypeRepository.saveAndFlush(customerType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the customerType using partial update
        CustomerType partialUpdatedCustomerType = new CustomerType();
        partialUpdatedCustomerType.setId(customerType.getId());

        partialUpdatedCustomerType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);

        restCustomerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCustomerType))
            )
            .andExpect(status().isOk());

        // Validate the CustomerType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCustomerTypeUpdatableFieldsEquals(partialUpdatedCustomerType, getPersistedCustomerType(partialUpdatedCustomerType));
    }

    @Test
    @Transactional
    void patchNonExistingCustomerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customerType.setId(longCount.incrementAndGet());

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerTypeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customerType.setId(longCount.incrementAndGet());

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        customerType.setId(longCount.incrementAndGet());

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(customerTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerType() throws Exception {
        // Initialize the database
        insertedCustomerType = customerTypeRepository.saveAndFlush(customerType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the customerType
        restCustomerTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return customerTypeRepository.count();
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

    protected CustomerType getPersistedCustomerType(CustomerType customerType) {
        return customerTypeRepository.findById(customerType.getId()).orElseThrow();
    }

    protected void assertPersistedCustomerTypeToMatchAllProperties(CustomerType expectedCustomerType) {
        assertCustomerTypeAllPropertiesEquals(expectedCustomerType, getPersistedCustomerType(expectedCustomerType));
    }

    protected void assertPersistedCustomerTypeToMatchUpdatableProperties(CustomerType expectedCustomerType) {
        assertCustomerTypeAllUpdatablePropertiesEquals(expectedCustomerType, getPersistedCustomerType(expectedCustomerType));
    }
}
