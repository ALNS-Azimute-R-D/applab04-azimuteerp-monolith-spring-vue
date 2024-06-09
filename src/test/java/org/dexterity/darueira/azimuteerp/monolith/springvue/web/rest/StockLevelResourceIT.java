package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevelAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.StockLevelRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.StockLevelService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.StockLevelDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.StockLevelMapper;
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
 * Integration tests for the {@link StockLevelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StockLevelResourceIT {

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_REMAINING_QUANTITY = 1;
    private static final Integer UPDATED_REMAINING_QUANTITY = 2;

    private static final String DEFAULT_COMMON_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_COMMON_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stock-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StockLevelRepository stockLevelRepository;

    @Mock
    private StockLevelRepository stockLevelRepositoryMock;

    @Autowired
    private StockLevelMapper stockLevelMapper;

    @Mock
    private StockLevelService stockLevelServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockLevelMockMvc;

    private StockLevel stockLevel;

    private StockLevel insertedStockLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockLevel createEntity(EntityManager em) {
        StockLevel stockLevel = new StockLevel()
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .remainingQuantity(DEFAULT_REMAINING_QUANTITY)
            .commonAttributesDetailsJSON(DEFAULT_COMMON_ATTRIBUTES_DETAILS_JSON);
        // Add required entity
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseResourceIT.createEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        stockLevel.setWarehouse(warehouse);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        stockLevel.setProduct(product);
        return stockLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockLevel createUpdatedEntity(EntityManager em) {
        StockLevel stockLevel = new StockLevel()
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .remainingQuantity(UPDATED_REMAINING_QUANTITY)
            .commonAttributesDetailsJSON(UPDATED_COMMON_ATTRIBUTES_DETAILS_JSON);
        // Add required entity
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseResourceIT.createUpdatedEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        stockLevel.setWarehouse(warehouse);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        stockLevel.setProduct(product);
        return stockLevel;
    }

    @BeforeEach
    public void initTest() {
        stockLevel = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedStockLevel != null) {
            stockLevelRepository.delete(insertedStockLevel);
            insertedStockLevel = null;
        }
    }

    @Test
    @Transactional
    void createStockLevel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);
        var returnedStockLevelDTO = om.readValue(
            restStockLevelMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stockLevelDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StockLevelDTO.class
        );

        // Validate the StockLevel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStockLevel = stockLevelMapper.toEntity(returnedStockLevelDTO);
        assertStockLevelUpdatableFieldsEquals(returnedStockLevel, getPersistedStockLevel(returnedStockLevel));

        insertedStockLevel = returnedStockLevel;
    }

    @Test
    @Transactional
    void createStockLevelWithExistingId() throws Exception {
        // Create the StockLevel with an existing ID
        stockLevel.setId(1L);
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockLevelMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stockLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        stockLevel.setLastModifiedDate(null);

        // Create the StockLevel, which fails.
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        restStockLevelMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stockLevelDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRemainingQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        stockLevel.setRemainingQuantity(null);

        // Create the StockLevel, which fails.
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        restStockLevelMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stockLevelDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStockLevels() throws Exception {
        // Initialize the database
        insertedStockLevel = stockLevelRepository.saveAndFlush(stockLevel);

        // Get all the stockLevelList
        restStockLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].remainingQuantity").value(hasItem(DEFAULT_REMAINING_QUANTITY)))
            .andExpect(jsonPath("$.[*].commonAttributesDetailsJSON").value(hasItem(DEFAULT_COMMON_ATTRIBUTES_DETAILS_JSON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStockLevelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(stockLevelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStockLevelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(stockLevelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStockLevelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(stockLevelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStockLevelMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(stockLevelRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStockLevel() throws Exception {
        // Initialize the database
        insertedStockLevel = stockLevelRepository.saveAndFlush(stockLevel);

        // Get the stockLevel
        restStockLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, stockLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockLevel.getId().intValue()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.remainingQuantity").value(DEFAULT_REMAINING_QUANTITY))
            .andExpect(jsonPath("$.commonAttributesDetailsJSON").value(DEFAULT_COMMON_ATTRIBUTES_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingStockLevel() throws Exception {
        // Get the stockLevel
        restStockLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockLevel() throws Exception {
        // Initialize the database
        insertedStockLevel = stockLevelRepository.saveAndFlush(stockLevel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stockLevel
        StockLevel updatedStockLevel = stockLevelRepository.findById(stockLevel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStockLevel are not directly saved in db
        em.detach(updatedStockLevel);
        updatedStockLevel
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .remainingQuantity(UPDATED_REMAINING_QUANTITY)
            .commonAttributesDetailsJSON(UPDATED_COMMON_ATTRIBUTES_DETAILS_JSON);
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(updatedStockLevel);

        restStockLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockLevelDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stockLevelDTO))
            )
            .andExpect(status().isOk());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStockLevelToMatchAllProperties(updatedStockLevel);
    }

    @Test
    @Transactional
    void putNonExistingStockLevel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stockLevel.setId(longCount.incrementAndGet());

        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockLevelDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stockLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockLevel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stockLevel.setId(longCount.incrementAndGet());

        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(stockLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockLevel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stockLevel.setId(longCount.incrementAndGet());

        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockLevelMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(stockLevelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockLevelWithPatch() throws Exception {
        // Initialize the database
        insertedStockLevel = stockLevelRepository.saveAndFlush(stockLevel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stockLevel using partial update
        StockLevel partialUpdatedStockLevel = new StockLevel();
        partialUpdatedStockLevel.setId(stockLevel.getId());

        restStockLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStockLevel))
            )
            .andExpect(status().isOk());

        // Validate the StockLevel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStockLevelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStockLevel, stockLevel),
            getPersistedStockLevel(stockLevel)
        );
    }

    @Test
    @Transactional
    void fullUpdateStockLevelWithPatch() throws Exception {
        // Initialize the database
        insertedStockLevel = stockLevelRepository.saveAndFlush(stockLevel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the stockLevel using partial update
        StockLevel partialUpdatedStockLevel = new StockLevel();
        partialUpdatedStockLevel.setId(stockLevel.getId());

        partialUpdatedStockLevel
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .remainingQuantity(UPDATED_REMAINING_QUANTITY)
            .commonAttributesDetailsJSON(UPDATED_COMMON_ATTRIBUTES_DETAILS_JSON);

        restStockLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockLevel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStockLevel))
            )
            .andExpect(status().isOk());

        // Validate the StockLevel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStockLevelUpdatableFieldsEquals(partialUpdatedStockLevel, getPersistedStockLevel(partialUpdatedStockLevel));
    }

    @Test
    @Transactional
    void patchNonExistingStockLevel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stockLevel.setId(longCount.incrementAndGet());

        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockLevelDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stockLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockLevel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stockLevel.setId(longCount.incrementAndGet());

        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(stockLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockLevel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        stockLevel.setId(longCount.incrementAndGet());

        // Create the StockLevel
        StockLevelDTO stockLevelDTO = stockLevelMapper.toDto(stockLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockLevelMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(stockLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockLevel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockLevel() throws Exception {
        // Initialize the database
        insertedStockLevel = stockLevelRepository.saveAndFlush(stockLevel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the stockLevel
        restStockLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockLevel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return stockLevelRepository.count();
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

    protected StockLevel getPersistedStockLevel(StockLevel stockLevel) {
        return stockLevelRepository.findById(stockLevel.getId()).orElseThrow();
    }

    protected void assertPersistedStockLevelToMatchAllProperties(StockLevel expectedStockLevel) {
        assertStockLevelAllPropertiesEquals(expectedStockLevel, getPersistedStockLevel(expectedStockLevel));
    }

    protected void assertPersistedStockLevelToMatchUpdatableProperties(StockLevel expectedStockLevel) {
        assertStockLevelAllUpdatablePropertiesEquals(expectedStockLevel, getPersistedStockLevel(expectedStockLevel));
    }
}
