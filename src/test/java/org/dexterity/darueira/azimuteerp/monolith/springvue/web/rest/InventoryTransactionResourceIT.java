package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransactionAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransaction;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.InventoryTransactionRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.InventoryTransactionService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.InventoryTransactionDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.InventoryTransactionMapper;
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
 * Integration tests for the {@link InventoryTransactionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InventoryTransactionResourceIT {

    private static final Long DEFAULT_INVOICE_ID = 1L;
    private static final Long UPDATED_INVOICE_ID = 2L;

    private static final Instant DEFAULT_TRANSACTION_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TRANSACTION_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_TRANSACTION_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_COMMENTS = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/inventory-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventoryTransactionRepository inventoryTransactionRepository;

    @Mock
    private InventoryTransactionRepository inventoryTransactionRepositoryMock;

    @Autowired
    private InventoryTransactionMapper inventoryTransactionMapper;

    @Mock
    private InventoryTransactionService inventoryTransactionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryTransactionMockMvc;

    private InventoryTransaction inventoryTransaction;

    private InventoryTransaction insertedInventoryTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryTransaction createEntity(EntityManager em) {
        InventoryTransaction inventoryTransaction = new InventoryTransaction()
            .invoiceId(DEFAULT_INVOICE_ID)
            .transactionCreatedDate(DEFAULT_TRANSACTION_CREATED_DATE)
            .transactionModifiedDate(DEFAULT_TRANSACTION_MODIFIED_DATE)
            .quantity(DEFAULT_QUANTITY)
            .transactionComments(DEFAULT_TRANSACTION_COMMENTS)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplier = SupplierResourceIT.createEntity(em);
            em.persist(supplier);
            em.flush();
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        inventoryTransaction.setSupplier(supplier);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        inventoryTransaction.setProduct(product);
        // Add required entity
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseResourceIT.createEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        inventoryTransaction.setWarehouse(warehouse);
        return inventoryTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryTransaction createUpdatedEntity(EntityManager em) {
        InventoryTransaction inventoryTransaction = new InventoryTransaction()
            .invoiceId(UPDATED_INVOICE_ID)
            .transactionCreatedDate(UPDATED_TRANSACTION_CREATED_DATE)
            .transactionModifiedDate(UPDATED_TRANSACTION_MODIFIED_DATE)
            .quantity(UPDATED_QUANTITY)
            .transactionComments(UPDATED_TRANSACTION_COMMENTS)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplier = SupplierResourceIT.createUpdatedEntity(em);
            em.persist(supplier);
            em.flush();
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        inventoryTransaction.setSupplier(supplier);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        inventoryTransaction.setProduct(product);
        // Add required entity
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseResourceIT.createUpdatedEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        inventoryTransaction.setWarehouse(warehouse);
        return inventoryTransaction;
    }

    @BeforeEach
    public void initTest() {
        inventoryTransaction = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedInventoryTransaction != null) {
            inventoryTransactionRepository.delete(insertedInventoryTransaction);
            insertedInventoryTransaction = null;
        }
    }

    @Test
    @Transactional
    void createInventoryTransaction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);
        var returnedInventoryTransactionDTO = om.readValue(
            restInventoryTransactionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(inventoryTransactionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InventoryTransactionDTO.class
        );

        // Validate the InventoryTransaction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInventoryTransaction = inventoryTransactionMapper.toEntity(returnedInventoryTransactionDTO);
        assertInventoryTransactionUpdatableFieldsEquals(
            returnedInventoryTransaction,
            getPersistedInventoryTransaction(returnedInventoryTransaction)
        );

        insertedInventoryTransaction = returnedInventoryTransaction;
    }

    @Test
    @Transactional
    void createInventoryTransactionWithExistingId() throws Exception {
        // Create the InventoryTransaction with an existing ID
        inventoryTransaction.setId(1L);
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInvoiceIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryTransaction.setInvoiceId(null);

        // Create the InventoryTransaction, which fails.
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        restInventoryTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryTransaction.setQuantity(null);

        // Create the InventoryTransaction, which fails.
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        restInventoryTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryTransaction.setActivationStatus(null);

        // Create the InventoryTransaction, which fails.
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        restInventoryTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventoryTransactions() throws Exception {
        // Initialize the database
        insertedInventoryTransaction = inventoryTransactionRepository.saveAndFlush(inventoryTransaction);

        // Get all the inventoryTransactionList
        restInventoryTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionCreatedDate").value(hasItem(DEFAULT_TRANSACTION_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionModifiedDate").value(hasItem(DEFAULT_TRANSACTION_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].transactionComments").value(hasItem(DEFAULT_TRANSACTION_COMMENTS)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInventoryTransactionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(inventoryTransactionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInventoryTransactionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inventoryTransactionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInventoryTransactionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inventoryTransactionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInventoryTransactionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(inventoryTransactionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInventoryTransaction() throws Exception {
        // Initialize the database
        insertedInventoryTransaction = inventoryTransactionRepository.saveAndFlush(inventoryTransaction);

        // Get the inventoryTransaction
        restInventoryTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, inventoryTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryTransaction.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID.intValue()))
            .andExpect(jsonPath("$.transactionCreatedDate").value(DEFAULT_TRANSACTION_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.transactionModifiedDate").value(DEFAULT_TRANSACTION_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.transactionComments").value(DEFAULT_TRANSACTION_COMMENTS))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInventoryTransaction() throws Exception {
        // Get the inventoryTransaction
        restInventoryTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventoryTransaction() throws Exception {
        // Initialize the database
        insertedInventoryTransaction = inventoryTransactionRepository.saveAndFlush(inventoryTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryTransaction
        InventoryTransaction updatedInventoryTransaction = inventoryTransactionRepository
            .findById(inventoryTransaction.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInventoryTransaction are not directly saved in db
        em.detach(updatedInventoryTransaction);
        updatedInventoryTransaction
            .invoiceId(UPDATED_INVOICE_ID)
            .transactionCreatedDate(UPDATED_TRANSACTION_CREATED_DATE)
            .transactionModifiedDate(UPDATED_TRANSACTION_MODIFIED_DATE)
            .quantity(UPDATED_QUANTITY)
            .transactionComments(UPDATED_TRANSACTION_COMMENTS)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(updatedInventoryTransaction);

        restInventoryTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryTransactionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInventoryTransactionToMatchAllProperties(updatedInventoryTransaction);
    }

    @Test
    @Transactional
    void putNonExistingInventoryTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryTransaction.setId(longCount.incrementAndGet());

        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryTransactionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventoryTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryTransaction.setId(longCount.incrementAndGet());

        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventoryTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryTransaction.setId(longCount.incrementAndGet());

        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedInventoryTransaction = inventoryTransactionRepository.saveAndFlush(inventoryTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryTransaction using partial update
        InventoryTransaction partialUpdatedInventoryTransaction = new InventoryTransaction();
        partialUpdatedInventoryTransaction.setId(inventoryTransaction.getId());

        partialUpdatedInventoryTransaction
            .invoiceId(UPDATED_INVOICE_ID)
            .transactionModifiedDate(UPDATED_TRANSACTION_MODIFIED_DATE)
            .quantity(UPDATED_QUANTITY)
            .transactionComments(UPDATED_TRANSACTION_COMMENTS);

        restInventoryTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventoryTransaction))
            )
            .andExpect(status().isOk());

        // Validate the InventoryTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryTransactionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInventoryTransaction, inventoryTransaction),
            getPersistedInventoryTransaction(inventoryTransaction)
        );
    }

    @Test
    @Transactional
    void fullUpdateInventoryTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedInventoryTransaction = inventoryTransactionRepository.saveAndFlush(inventoryTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryTransaction using partial update
        InventoryTransaction partialUpdatedInventoryTransaction = new InventoryTransaction();
        partialUpdatedInventoryTransaction.setId(inventoryTransaction.getId());

        partialUpdatedInventoryTransaction
            .invoiceId(UPDATED_INVOICE_ID)
            .transactionCreatedDate(UPDATED_TRANSACTION_CREATED_DATE)
            .transactionModifiedDate(UPDATED_TRANSACTION_MODIFIED_DATE)
            .quantity(UPDATED_QUANTITY)
            .transactionComments(UPDATED_TRANSACTION_COMMENTS)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restInventoryTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventoryTransaction))
            )
            .andExpect(status().isOk());

        // Validate the InventoryTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryTransactionUpdatableFieldsEquals(
            partialUpdatedInventoryTransaction,
            getPersistedInventoryTransaction(partialUpdatedInventoryTransaction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInventoryTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryTransaction.setId(longCount.incrementAndGet());

        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryTransactionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventoryTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryTransaction.setId(longCount.incrementAndGet());

        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventoryTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryTransaction.setId(longCount.incrementAndGet());

        // Create the InventoryTransaction
        InventoryTransactionDTO inventoryTransactionDTO = inventoryTransactionMapper.toDto(inventoryTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventoryTransaction() throws Exception {
        // Initialize the database
        insertedInventoryTransaction = inventoryTransactionRepository.saveAndFlush(inventoryTransaction);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventoryTransaction
        restInventoryTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventoryTransaction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventoryTransactionRepository.count();
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

    protected InventoryTransaction getPersistedInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionRepository.findById(inventoryTransaction.getId()).orElseThrow();
    }

    protected void assertPersistedInventoryTransactionToMatchAllProperties(InventoryTransaction expectedInventoryTransaction) {
        assertInventoryTransactionAllPropertiesEquals(
            expectedInventoryTransaction,
            getPersistedInventoryTransaction(expectedInventoryTransaction)
        );
    }

    protected void assertPersistedInventoryTransactionToMatchUpdatableProperties(InventoryTransaction expectedInventoryTransaction) {
        assertInventoryTransactionAllUpdatablePropertiesEquals(
            expectedInventoryTransaction,
            getPersistedInventoryTransaction(expectedInventoryTransaction)
        );
    }
}
