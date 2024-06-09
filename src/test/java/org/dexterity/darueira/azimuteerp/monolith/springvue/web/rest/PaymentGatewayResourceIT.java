package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGatewayAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.PaymentGatewayRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PaymentGatewayDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.PaymentGatewayMapper;
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
 * Integration tests for the {@link PaymentGatewayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentGatewayResourceIT {

    private static final String DEFAULT_ALIAS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_HANDLER_CLAZZ = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_HANDLER_CLAZZ = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/payment-gateways";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaymentGatewayRepository paymentGatewayRepository;

    @Autowired
    private PaymentGatewayMapper paymentGatewayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentGatewayMockMvc;

    private PaymentGateway paymentGateway;

    private PaymentGateway insertedPaymentGateway;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGateway createEntity(EntityManager em) {
        PaymentGateway paymentGateway = new PaymentGateway()
            .aliasCode(DEFAULT_ALIAS_CODE)
            .description(DEFAULT_DESCRIPTION)
            .businessHandlerClazz(DEFAULT_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return paymentGateway;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGateway createUpdatedEntity(EntityManager em) {
        PaymentGateway paymentGateway = new PaymentGateway()
            .aliasCode(UPDATED_ALIAS_CODE)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return paymentGateway;
    }

    @BeforeEach
    public void initTest() {
        paymentGateway = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPaymentGateway != null) {
            paymentGatewayRepository.delete(insertedPaymentGateway);
            insertedPaymentGateway = null;
        }
    }

    @Test
    @Transactional
    void createPaymentGateway() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);
        var returnedPaymentGatewayDTO = om.readValue(
            restPaymentGatewayMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(paymentGatewayDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaymentGatewayDTO.class
        );

        // Validate the PaymentGateway in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPaymentGateway = paymentGatewayMapper.toEntity(returnedPaymentGatewayDTO);
        assertPaymentGatewayUpdatableFieldsEquals(returnedPaymentGateway, getPersistedPaymentGateway(returnedPaymentGateway));

        insertedPaymentGateway = returnedPaymentGateway;
    }

    @Test
    @Transactional
    void createPaymentGatewayWithExistingId() throws Exception {
        // Create the PaymentGateway with an existing ID
        paymentGateway.setId(1L);
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentGatewayMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAliasCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentGateway.setAliasCode(null);

        // Create the PaymentGateway, which fails.
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        restPaymentGatewayMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentGateway.setDescription(null);

        // Create the PaymentGateway, which fails.
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        restPaymentGatewayMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paymentGateway.setActivationStatus(null);

        // Create the PaymentGateway, which fails.
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        restPaymentGatewayMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentGateways() throws Exception {
        // Initialize the database
        insertedPaymentGateway = paymentGatewayRepository.saveAndFlush(paymentGateway);

        // Get all the paymentGatewayList
        restPaymentGatewayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentGateway.getId().intValue())))
            .andExpect(jsonPath("$.[*].aliasCode").value(hasItem(DEFAULT_ALIAS_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].businessHandlerClazz").value(hasItem(DEFAULT_BUSINESS_HANDLER_CLAZZ)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPaymentGateway() throws Exception {
        // Initialize the database
        insertedPaymentGateway = paymentGatewayRepository.saveAndFlush(paymentGateway);

        // Get the paymentGateway
        restPaymentGatewayMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentGateway.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentGateway.getId().intValue()))
            .andExpect(jsonPath("$.aliasCode").value(DEFAULT_ALIAS_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.businessHandlerClazz").value(DEFAULT_BUSINESS_HANDLER_CLAZZ))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentGateway() throws Exception {
        // Get the paymentGateway
        restPaymentGatewayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentGateway() throws Exception {
        // Initialize the database
        insertedPaymentGateway = paymentGatewayRepository.saveAndFlush(paymentGateway);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentGateway
        PaymentGateway updatedPaymentGateway = paymentGatewayRepository.findById(paymentGateway.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaymentGateway are not directly saved in db
        em.detach(updatedPaymentGateway);
        updatedPaymentGateway
            .aliasCode(UPDATED_ALIAS_CODE)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(updatedPaymentGateway);

        restPaymentGatewayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentGatewayDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaymentGatewayToMatchAllProperties(updatedPaymentGateway);
    }

    @Test
    @Transactional
    void putNonExistingPaymentGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentGateway.setId(longCount.incrementAndGet());

        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGatewayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentGatewayDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentGateway.setId(longCount.incrementAndGet());

        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGatewayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentGateway.setId(longCount.incrementAndGet());

        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGatewayMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentGatewayWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentGateway = paymentGatewayRepository.saveAndFlush(paymentGateway);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentGateway using partial update
        PaymentGateway partialUpdatedPaymentGateway = new PaymentGateway();
        partialUpdatedPaymentGateway.setId(paymentGateway.getId());

        partialUpdatedPaymentGateway
            .aliasCode(UPDATED_ALIAS_CODE)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restPaymentGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentGateway.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentGateway))
            )
            .andExpect(status().isOk());

        // Validate the PaymentGateway in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentGatewayUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPaymentGateway, paymentGateway),
            getPersistedPaymentGateway(paymentGateway)
        );
    }

    @Test
    @Transactional
    void fullUpdatePaymentGatewayWithPatch() throws Exception {
        // Initialize the database
        insertedPaymentGateway = paymentGatewayRepository.saveAndFlush(paymentGateway);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paymentGateway using partial update
        PaymentGateway partialUpdatedPaymentGateway = new PaymentGateway();
        partialUpdatedPaymentGateway.setId(paymentGateway.getId());

        partialUpdatedPaymentGateway
            .aliasCode(UPDATED_ALIAS_CODE)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restPaymentGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentGateway.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaymentGateway))
            )
            .andExpect(status().isOk());

        // Validate the PaymentGateway in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaymentGatewayUpdatableFieldsEquals(partialUpdatedPaymentGateway, getPersistedPaymentGateway(partialUpdatedPaymentGateway));
    }

    @Test
    @Transactional
    void patchNonExistingPaymentGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentGateway.setId(longCount.incrementAndGet());

        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentGatewayDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentGateway.setId(longCount.incrementAndGet());

        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentGateway() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paymentGateway.setId(longCount.incrementAndGet());

        // Create the PaymentGateway
        PaymentGatewayDTO paymentGatewayDTO = paymentGatewayMapper.toDto(paymentGateway);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGatewayMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paymentGatewayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentGateway in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentGateway() throws Exception {
        // Initialize the database
        insertedPaymentGateway = paymentGatewayRepository.saveAndFlush(paymentGateway);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paymentGateway
        restPaymentGatewayMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentGateway.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paymentGatewayRepository.count();
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

    protected PaymentGateway getPersistedPaymentGateway(PaymentGateway paymentGateway) {
        return paymentGatewayRepository.findById(paymentGateway.getId()).orElseThrow();
    }

    protected void assertPersistedPaymentGatewayToMatchAllProperties(PaymentGateway expectedPaymentGateway) {
        assertPaymentGatewayAllPropertiesEquals(expectedPaymentGateway, getPersistedPaymentGateway(expectedPaymentGateway));
    }

    protected void assertPersistedPaymentGatewayToMatchUpdatableProperties(PaymentGateway expectedPaymentGateway) {
        assertPaymentGatewayAllUpdatablePropertiesEquals(expectedPaymentGateway, getPersistedPaymentGateway(expectedPaymentGateway));
    }
}
