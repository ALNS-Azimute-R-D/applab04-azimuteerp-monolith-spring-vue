package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TenantAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TenantRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TenantDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TenantMapper;
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
 * Integration tests for the {@link TenantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TenantResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_BUSINESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_BUSINESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_HANDLER_CLAZZ = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_HANDLER_CLAZZ = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_DETAILS_JSON = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_IMG_CONTENT_TYPE = "image/png";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/tenants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenantMockMvc;

    private Tenant tenant;

    private Tenant insertedTenant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenant createEntity(EntityManager em) {
        Tenant tenant = new Tenant()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .customerBusinessCode(DEFAULT_CUSTOMER_BUSINESS_CODE)
            .businessHandlerClazz(DEFAULT_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .billingDetailsJSON(DEFAULT_BILLING_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .logoImg(DEFAULT_LOGO_IMG)
            .logoImgContentType(DEFAULT_LOGO_IMG_CONTENT_TYPE)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return tenant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenant createUpdatedEntity(EntityManager em) {
        Tenant tenant = new Tenant()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .customerBusinessCode(UPDATED_CUSTOMER_BUSINESS_CODE)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .billingDetailsJSON(UPDATED_BILLING_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return tenant;
    }

    @BeforeEach
    public void initTest() {
        tenant = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTenant != null) {
            tenantRepository.delete(insertedTenant);
            insertedTenant = null;
        }
    }

    @Test
    @Transactional
    void createTenant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);
        var returnedTenantDTO = om.readValue(
            restTenantMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TenantDTO.class
        );

        // Validate the Tenant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTenant = tenantMapper.toEntity(returnedTenantDTO);
        assertTenantUpdatableFieldsEquals(returnedTenant, getPersistedTenant(returnedTenant));

        insertedTenant = returnedTenant;
    }

    @Test
    @Transactional
    void createTenantWithExistingId() throws Exception {
        // Create the Tenant with an existing ID
        tenant.setId(1L);
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setAcronym(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setName(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setDescription(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerBusinessCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setCustomerBusinessCode(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setActivationStatus(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTenants() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList
        restTenantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].customerBusinessCode").value(hasItem(DEFAULT_CUSTOMER_BUSINESS_CODE)))
            .andExpect(jsonPath("$.[*].businessHandlerClazz").value(hasItem(DEFAULT_BUSINESS_HANDLER_CLAZZ)))
            .andExpect(jsonPath("$.[*].mainContactPersonDetailsJSON").value(hasItem(DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].billingDetailsJSON").value(hasItem(DEFAULT_BILLING_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].technicalEnvironmentsDetailsJSON").value(hasItem(DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].logoImgContentType").value(hasItem(DEFAULT_LOGO_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoImg").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO_IMG))))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @Test
    @Transactional
    void getTenant() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        // Get the tenant
        restTenantMockMvc
            .perform(get(ENTITY_API_URL_ID, tenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenant.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.customerBusinessCode").value(DEFAULT_CUSTOMER_BUSINESS_CODE))
            .andExpect(jsonPath("$.businessHandlerClazz").value(DEFAULT_BUSINESS_HANDLER_CLAZZ))
            .andExpect(jsonPath("$.mainContactPersonDetailsJSON").value(DEFAULT_MAIN_CONTACT_PERSON_DETAILS_JSON))
            .andExpect(jsonPath("$.billingDetailsJSON").value(DEFAULT_BILLING_DETAILS_JSON))
            .andExpect(jsonPath("$.technicalEnvironmentsDetailsJSON").value(DEFAULT_TECHNICAL_ENVIRONMENTS_DETAILS_JSON))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON))
            .andExpect(jsonPath("$.logoImgContentType").value(DEFAULT_LOGO_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoImg").value(Base64.getEncoder().encodeToString(DEFAULT_LOGO_IMG)))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTenant() throws Exception {
        // Get the tenant
        restTenantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTenant() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenant
        Tenant updatedTenant = tenantRepository.findById(tenant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTenant are not directly saved in db
        em.detach(updatedTenant);
        updatedTenant
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .customerBusinessCode(UPDATED_CUSTOMER_BUSINESS_CODE)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .billingDetailsJSON(UPDATED_BILLING_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        TenantDTO tenantDTO = tenantMapper.toDto(updatedTenant);

        restTenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTenantToMatchAllProperties(updatedTenant);
    }

    @Test
    @Transactional
    void putNonExistingTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTenantWithPatch() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenant using partial update
        Tenant partialUpdatedTenant = new Tenant();
        partialUpdatedTenant.setId(tenant.getId());

        partialUpdatedTenant
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenant))
            )
            .andExpect(status().isOk());

        // Validate the Tenant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenantUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTenant, tenant), getPersistedTenant(tenant));
    }

    @Test
    @Transactional
    void fullUpdateTenantWithPatch() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenant using partial update
        Tenant partialUpdatedTenant = new Tenant();
        partialUpdatedTenant.setId(tenant.getId());

        partialUpdatedTenant
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .customerBusinessCode(UPDATED_CUSTOMER_BUSINESS_CODE)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ)
            .mainContactPersonDetailsJSON(UPDATED_MAIN_CONTACT_PERSON_DETAILS_JSON)
            .billingDetailsJSON(UPDATED_BILLING_DETAILS_JSON)
            .technicalEnvironmentsDetailsJSON(UPDATED_TECHNICAL_ENVIRONMENTS_DETAILS_JSON)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON)
            .logoImg(UPDATED_LOGO_IMG)
            .logoImgContentType(UPDATED_LOGO_IMG_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenant))
            )
            .andExpect(status().isOk());

        // Validate the Tenant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenantUpdatableFieldsEquals(partialUpdatedTenant, getPersistedTenant(partialUpdatedTenant));
    }

    @Test
    @Transactional
    void patchNonExistingTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tenantDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTenant() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tenant
        restTenantMockMvc
            .perform(delete(ENTITY_API_URL_ID, tenant.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tenantRepository.count();
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

    protected Tenant getPersistedTenant(Tenant tenant) {
        return tenantRepository.findById(tenant.getId()).orElseThrow();
    }

    protected void assertPersistedTenantToMatchAllProperties(Tenant expectedTenant) {
        assertTenantAllPropertiesEquals(expectedTenant, getPersistedTenant(expectedTenant));
    }

    protected void assertPersistedTenantToMatchUpdatableProperties(Tenant expectedTenant) {
        assertTenantAllUpdatablePropertiesEquals(expectedTenant, getPersistedTenant(expectedTenant));
    }
}
