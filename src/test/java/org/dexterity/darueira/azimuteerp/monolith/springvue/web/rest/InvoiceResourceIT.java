package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InvoiceAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.sameNumber;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Invoice;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.InvoiceStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.InvoiceRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.InvoiceService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.InvoiceDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.InvoiceMapper;
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
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InvoiceResourceIT {

    private static final String DEFAULT_BUSINESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAX_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SHIPPING_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SHIPPING_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_DUE_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_DUE_VALUE = new BigDecimal(2);

    private static final Integer DEFAULT_NUMBER_OF_INSTALLMENTS_ORIGINAL = 1;
    private static final Integer UPDATED_NUMBER_OF_INSTALLMENTS_ORIGINAL = 2;

    private static final Integer DEFAULT_NUMBER_OF_INSTALLMENTS_PAID = 1;
    private static final Integer UPDATED_NUMBER_OF_INSTALLMENTS_PAID = 2;

    private static final BigDecimal DEFAULT_AMOUNT_PAID_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_PAID_VALUE = new BigDecimal(2);

    private static final InvoiceStatusEnum DEFAULT_STATUS = InvoiceStatusEnum.PAID_ONCE;
    private static final InvoiceStatusEnum UPDATED_STATUS = InvoiceStatusEnum.PAYING_IN_INSTALLMENTS;

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/invoices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceRepository invoiceRepositoryMock;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Mock
    private InvoiceService invoiceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    private Invoice insertedInvoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .businessCode(DEFAULT_BUSINESS_CODE)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .description(DEFAULT_DESCRIPTION)
            .taxValue(DEFAULT_TAX_VALUE)
            .shippingValue(DEFAULT_SHIPPING_VALUE)
            .amountDueValue(DEFAULT_AMOUNT_DUE_VALUE)
            .numberOfInstallmentsOriginal(DEFAULT_NUMBER_OF_INSTALLMENTS_ORIGINAL)
            .numberOfInstallmentsPaid(DEFAULT_NUMBER_OF_INSTALLMENTS_PAID)
            .amountPaidValue(DEFAULT_AMOUNT_PAID_VALUE)
            .status(DEFAULT_STATUS)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return invoice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .businessCode(UPDATED_BUSINESS_CODE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .description(UPDATED_DESCRIPTION)
            .taxValue(UPDATED_TAX_VALUE)
            .shippingValue(UPDATED_SHIPPING_VALUE)
            .amountDueValue(UPDATED_AMOUNT_DUE_VALUE)
            .numberOfInstallmentsOriginal(UPDATED_NUMBER_OF_INSTALLMENTS_ORIGINAL)
            .numberOfInstallmentsPaid(UPDATED_NUMBER_OF_INSTALLMENTS_PAID)
            .amountPaidValue(UPDATED_AMOUNT_PAID_VALUE)
            .status(UPDATED_STATUS)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedInvoice != null) {
            invoiceRepository.delete(insertedInvoice);
            insertedInvoice = null;
        }
    }

    @Test
    @Transactional
    void createInvoice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);
        var returnedInvoiceDTO = om.readValue(
            restInvoiceMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InvoiceDTO.class
        );

        // Validate the Invoice in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInvoice = invoiceMapper.toEntity(returnedInvoiceDTO);
        assertInvoiceUpdatableFieldsEquals(returnedInvoice, getPersistedInvoice(returnedInvoice));

        insertedInvoice = returnedInvoice;
    }

    @Test
    @Transactional
    void createInvoiceWithExistingId() throws Exception {
        // Create the Invoice with an existing ID
        invoice.setId(1L);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBusinessCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        invoice.setBusinessCode(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        invoice.setDescription(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberOfInstallmentsOriginalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        invoice.setNumberOfInstallmentsOriginal(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        invoice.setStatus(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        invoice.setActivationStatus(null);

        // Create the Invoice, which fails.
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        restInvoiceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInvoices() throws Exception {
        // Initialize the database
        insertedInvoice = invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessCode").value(hasItem(DEFAULT_BUSINESS_CODE)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxValue").value(hasItem(sameNumber(DEFAULT_TAX_VALUE))))
            .andExpect(jsonPath("$.[*].shippingValue").value(hasItem(sameNumber(DEFAULT_SHIPPING_VALUE))))
            .andExpect(jsonPath("$.[*].amountDueValue").value(hasItem(sameNumber(DEFAULT_AMOUNT_DUE_VALUE))))
            .andExpect(jsonPath("$.[*].numberOfInstallmentsOriginal").value(hasItem(DEFAULT_NUMBER_OF_INSTALLMENTS_ORIGINAL)))
            .andExpect(jsonPath("$.[*].numberOfInstallmentsPaid").value(hasItem(DEFAULT_NUMBER_OF_INSTALLMENTS_PAID)))
            .andExpect(jsonPath("$.[*].amountPaidValue").value(hasItem(sameNumber(DEFAULT_AMOUNT_PAID_VALUE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(invoiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(invoiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(invoiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(invoiceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInvoice() throws Exception {
        // Initialize the database
        insertedInvoice = invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.businessCode").value(DEFAULT_BUSINESS_CODE))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.taxValue").value(sameNumber(DEFAULT_TAX_VALUE)))
            .andExpect(jsonPath("$.shippingValue").value(sameNumber(DEFAULT_SHIPPING_VALUE)))
            .andExpect(jsonPath("$.amountDueValue").value(sameNumber(DEFAULT_AMOUNT_DUE_VALUE)))
            .andExpect(jsonPath("$.numberOfInstallmentsOriginal").value(DEFAULT_NUMBER_OF_INSTALLMENTS_ORIGINAL))
            .andExpect(jsonPath("$.numberOfInstallmentsPaid").value(DEFAULT_NUMBER_OF_INSTALLMENTS_PAID))
            .andExpect(jsonPath("$.amountPaidValue").value(sameNumber(DEFAULT_AMOUNT_PAID_VALUE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoice() throws Exception {
        // Initialize the database
        insertedInvoice = invoiceRepository.saveAndFlush(invoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .businessCode(UPDATED_BUSINESS_CODE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .description(UPDATED_DESCRIPTION)
            .taxValue(UPDATED_TAX_VALUE)
            .shippingValue(UPDATED_SHIPPING_VALUE)
            .amountDueValue(UPDATED_AMOUNT_DUE_VALUE)
            .numberOfInstallmentsOriginal(UPDATED_NUMBER_OF_INSTALLMENTS_ORIGINAL)
            .numberOfInstallmentsPaid(UPDATED_NUMBER_OF_INSTALLMENTS_PAID)
            .amountPaidValue(UPDATED_AMOUNT_PAID_VALUE)
            .status(UPDATED_STATUS)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(updatedInvoice);

        restInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInvoiceToMatchAllProperties(updatedInvoice);
    }

    @Test
    @Transactional
    void putNonExistingInvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoice.setId(longCount.incrementAndGet());

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoice.setId(longCount.incrementAndGet());

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoice.setId(longCount.incrementAndGet());

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceWithPatch() throws Exception {
        // Initialize the database
        insertedInvoice = invoiceRepository.saveAndFlush(invoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoice using partial update
        Invoice partialUpdatedInvoice = new Invoice();
        partialUpdatedInvoice.setId(invoice.getId());

        partialUpdatedInvoice
            .businessCode(UPDATED_BUSINESS_CODE)
            .description(UPDATED_DESCRIPTION)
            .shippingValue(UPDATED_SHIPPING_VALUE)
            .numberOfInstallmentsPaid(UPDATED_NUMBER_OF_INSTALLMENTS_PAID)
            .amountPaidValue(UPDATED_AMOUNT_PAID_VALUE)
            .status(UPDATED_STATUS)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);

        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInvoice))
            )
            .andExpect(status().isOk());

        // Validate the Invoice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInvoiceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedInvoice, invoice), getPersistedInvoice(invoice));
    }

    @Test
    @Transactional
    void fullUpdateInvoiceWithPatch() throws Exception {
        // Initialize the database
        insertedInvoice = invoiceRepository.saveAndFlush(invoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoice using partial update
        Invoice partialUpdatedInvoice = new Invoice();
        partialUpdatedInvoice.setId(invoice.getId());

        partialUpdatedInvoice
            .businessCode(UPDATED_BUSINESS_CODE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .description(UPDATED_DESCRIPTION)
            .taxValue(UPDATED_TAX_VALUE)
            .shippingValue(UPDATED_SHIPPING_VALUE)
            .amountDueValue(UPDATED_AMOUNT_DUE_VALUE)
            .numberOfInstallmentsOriginal(UPDATED_NUMBER_OF_INSTALLMENTS_ORIGINAL)
            .numberOfInstallmentsPaid(UPDATED_NUMBER_OF_INSTALLMENTS_PAID)
            .amountPaidValue(UPDATED_AMOUNT_PAID_VALUE)
            .status(UPDATED_STATUS)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoice.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInvoice))
            )
            .andExpect(status().isOk());

        // Validate the Invoice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInvoiceUpdatableFieldsEquals(partialUpdatedInvoice, getPersistedInvoice(partialUpdatedInvoice));
    }

    @Test
    @Transactional
    void patchNonExistingInvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoice.setId(longCount.incrementAndGet());

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(invoiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoice.setId(longCount.incrementAndGet());

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(invoiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoice.setId(longCount.incrementAndGet());

        // Create the Invoice
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(invoiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Invoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoice() throws Exception {
        // Initialize the database
        insertedInvoice = invoiceRepository.saveAndFlush(invoice);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the invoice
        restInvoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoice.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return invoiceRepository.count();
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

    protected Invoice getPersistedInvoice(Invoice invoice) {
        return invoiceRepository.findById(invoice.getId()).orElseThrow();
    }

    protected void assertPersistedInvoiceToMatchAllProperties(Invoice expectedInvoice) {
        assertInvoiceAllPropertiesEquals(expectedInvoice, getPersistedInvoice(expectedInvoice));
    }

    protected void assertPersistedInvoiceToMatchUpdatableProperties(Invoice expectedInvoice) {
        assertInvoiceAllUpdatablePropertiesEquals(expectedInvoice, getPersistedInvoice(expectedInvoice));
    }
}
