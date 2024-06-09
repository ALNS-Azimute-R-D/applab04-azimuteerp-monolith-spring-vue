package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.WarehouseAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.WarehouseRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.WarehouseDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.WarehouseMapper;
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
 * Integration tests for the {@link WarehouseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WarehouseResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBB";

    private static final byte[] DEFAULT_POINT_LOCATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POINT_LOCATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POINT_LOCATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POINT_LOCATION_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MAIN_EMAIL = "r=9'/Q@\"\\,J2\".*/";
    private static final String UPDATED_MAIN_EMAIL = "cI'@Y`1Y|-.vHBh";

    private static final String DEFAULT_LAND_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LAND_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/warehouses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarehouseMockMvc;

    private Warehouse warehouse;

    private Warehouse insertedWarehouse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warehouse createEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .locationName(DEFAULT_LOCATION_NAME)
            .postalCode(DEFAULT_POSTAL_CODE)
            .pointLocation(DEFAULT_POINT_LOCATION)
            .pointLocationContentType(DEFAULT_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(DEFAULT_MAIN_EMAIL)
            .landPhoneNumber(DEFAULT_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return warehouse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warehouse createUpdatedEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return warehouse;
    }

    @BeforeEach
    public void initTest() {
        warehouse = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedWarehouse != null) {
            warehouseRepository.delete(insertedWarehouse);
            insertedWarehouse = null;
        }
    }

    @Test
    @Transactional
    void createWarehouse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);
        var returnedWarehouseDTO = om.readValue(
            restWarehouseMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WarehouseDTO.class
        );

        // Validate the Warehouse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWarehouse = warehouseMapper.toEntity(returnedWarehouseDTO);
        assertWarehouseUpdatableFieldsEquals(returnedWarehouse, getPersistedWarehouse(returnedWarehouse));

        insertedWarehouse = returnedWarehouse;
    }

    @Test
    @Transactional
    void createWarehouseWithExistingId() throws Exception {
        // Create the Warehouse with an existing ID
        warehouse.setId(1L);
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warehouse.setAcronym(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warehouse.setName(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warehouse.setStreetAddress(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warehouse.setPostalCode(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warehouse.setActivationStatus(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarehouses() throws Exception {
        // Initialize the database
        insertedWarehouse = warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].pointLocationContentType").value(hasItem(DEFAULT_POINT_LOCATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pointLocation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_POINT_LOCATION))))
            .andExpect(jsonPath("$.[*].mainEmail").value(hasItem(DEFAULT_MAIN_EMAIL)))
            .andExpect(jsonPath("$.[*].landPhoneNumber").value(hasItem(DEFAULT_LAND_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @Test
    @Transactional
    void getWarehouse() throws Exception {
        // Initialize the database
        insertedWarehouse = warehouseRepository.saveAndFlush(warehouse);

        // Get the warehouse
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL_ID, warehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warehouse.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.pointLocationContentType").value(DEFAULT_POINT_LOCATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.pointLocation").value(Base64.getEncoder().encodeToString(DEFAULT_POINT_LOCATION)))
            .andExpect(jsonPath("$.mainEmail").value(DEFAULT_MAIN_EMAIL))
            .andExpect(jsonPath("$.landPhoneNumber").value(DEFAULT_LAND_PHONE_NUMBER))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWarehouse() throws Exception {
        // Get the warehouse
        restWarehouseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWarehouse() throws Exception {
        // Initialize the database
        insertedWarehouse = warehouseRepository.saveAndFlush(warehouse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warehouse
        Warehouse updatedWarehouse = warehouseRepository.findById(warehouse.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWarehouse are not directly saved in db
        em.detach(updatedWarehouse);
        updatedWarehouse
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(updatedWarehouse);

        restWarehouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warehouseDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warehouseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWarehouseToMatchAllProperties(updatedWarehouse);
    }

    @Test
    @Transactional
    void putNonExistingWarehouse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warehouse.setId(longCount.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warehouseDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarehouse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warehouse.setId(longCount.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarehouse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warehouse.setId(longCount.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warehouseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarehouseWithPatch() throws Exception {
        // Initialize the database
        insertedWarehouse = warehouseRepository.saveAndFlush(warehouse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warehouse using partial update
        Warehouse partialUpdatedWarehouse = new Warehouse();
        partialUpdatedWarehouse.setId(warehouse.getId());

        partialUpdatedWarehouse
            .description(UPDATED_DESCRIPTION)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarehouse.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarehouse))
            )
            .andExpect(status().isOk());

        // Validate the Warehouse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarehouseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWarehouse, warehouse),
            getPersistedWarehouse(warehouse)
        );
    }

    @Test
    @Transactional
    void fullUpdateWarehouseWithPatch() throws Exception {
        // Initialize the database
        insertedWarehouse = warehouseRepository.saveAndFlush(warehouse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warehouse using partial update
        Warehouse partialUpdatedWarehouse = new Warehouse();
        partialUpdatedWarehouse.setId(warehouse.getId());

        partialUpdatedWarehouse
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .pointLocation(UPDATED_POINT_LOCATION)
            .pointLocationContentType(UPDATED_POINT_LOCATION_CONTENT_TYPE)
            .mainEmail(UPDATED_MAIN_EMAIL)
            .landPhoneNumber(UPDATED_LAND_PHONE_NUMBER)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .faxNumber(UPDATED_FAX_NUMBER)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarehouse.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarehouse))
            )
            .andExpect(status().isOk());

        // Validate the Warehouse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarehouseUpdatableFieldsEquals(partialUpdatedWarehouse, getPersistedWarehouse(partialUpdatedWarehouse));
    }

    @Test
    @Transactional
    void patchNonExistingWarehouse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warehouse.setId(longCount.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warehouseDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarehouse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warehouse.setId(longCount.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarehouse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warehouse.setId(longCount.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(warehouseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Warehouse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarehouse() throws Exception {
        // Initialize the database
        insertedWarehouse = warehouseRepository.saveAndFlush(warehouse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the warehouse
        restWarehouseMockMvc
            .perform(delete(ENTITY_API_URL_ID, warehouse.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return warehouseRepository.count();
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

    protected Warehouse getPersistedWarehouse(Warehouse warehouse) {
        return warehouseRepository.findById(warehouse.getId()).orElseThrow();
    }

    protected void assertPersistedWarehouseToMatchAllProperties(Warehouse expectedWarehouse) {
        assertWarehouseAllPropertiesEquals(expectedWarehouse, getPersistedWarehouse(expectedWarehouse));
    }

    protected void assertPersistedWarehouseToMatchUpdatableProperties(Warehouse expectedWarehouse) {
        assertWarehouseAllUpdatablePropertiesEquals(expectedWarehouse, getPersistedWarehouse(expectedWarehouse));
    }
}
