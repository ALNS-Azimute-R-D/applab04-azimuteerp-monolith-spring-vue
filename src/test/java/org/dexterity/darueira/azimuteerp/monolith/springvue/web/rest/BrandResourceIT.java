package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BrandAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Brand;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.BrandRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.BrandDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.BrandMapper;
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
 * Integration tests for the {@link BrandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BrandResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO_BRAND = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_BRAND = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_BRAND_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_BRAND_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/brands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrandMockMvc;

    private Brand brand;

    private Brand insertedBrand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createEntity(EntityManager em) {
        Brand brand = new Brand()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .logoBrand(DEFAULT_LOGO_BRAND)
            .logoBrandContentType(DEFAULT_LOGO_BRAND_CONTENT_TYPE);
        return brand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createUpdatedEntity(EntityManager em) {
        Brand brand = new Brand()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .logoBrand(UPDATED_LOGO_BRAND)
            .logoBrandContentType(UPDATED_LOGO_BRAND_CONTENT_TYPE);
        return brand;
    }

    @BeforeEach
    public void initTest() {
        brand = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBrand != null) {
            brandRepository.delete(insertedBrand);
            insertedBrand = null;
        }
    }

    @Test
    @Transactional
    void createBrand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);
        var returnedBrandDTO = om.readValue(
            restBrandMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BrandDTO.class
        );

        // Validate the Brand in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBrand = brandMapper.toEntity(returnedBrandDTO);
        assertBrandUpdatableFieldsEquals(returnedBrand, getPersistedBrand(returnedBrand));

        insertedBrand = returnedBrand;
    }

    @Test
    @Transactional
    void createBrandWithExistingId() throws Exception {
        // Create the Brand with an existing ID
        brand.setId(1L);
        BrandDTO brandDTO = brandMapper.toDto(brand);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setAcronym(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setName(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBrands() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].logoBrandContentType").value(hasItem(DEFAULT_LOGO_BRAND_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoBrand").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO_BRAND))));
    }

    @Test
    @Transactional
    void getBrand() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc
            .perform(get(ENTITY_API_URL_ID, brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.logoBrandContentType").value(DEFAULT_LOGO_BRAND_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoBrand").value(Base64.getEncoder().encodeToString(DEFAULT_LOGO_BRAND)));
    }

    @Test
    @Transactional
    void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBrand() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brand
        Brand updatedBrand = brandRepository.findById(brand.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBrand are not directly saved in db
        em.detach(updatedBrand);
        updatedBrand
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .logoBrand(UPDATED_LOGO_BRAND)
            .logoBrandContentType(UPDATED_LOGO_BRAND_CONTENT_TYPE);
        BrandDTO brandDTO = brandMapper.toDto(updatedBrand);

        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brandDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBrandToMatchAllProperties(updatedBrand);
    }

    @Test
    @Transactional
    void putNonExistingBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brandDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBrandWithPatch() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brand using partial update
        Brand partialUpdatedBrand = new Brand();
        partialUpdatedBrand.setId(brand.getId());

        partialUpdatedBrand.logoBrand(UPDATED_LOGO_BRAND).logoBrandContentType(UPDATED_LOGO_BRAND_CONTENT_TYPE);

        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrand.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrandUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBrand, brand), getPersistedBrand(brand));
    }

    @Test
    @Transactional
    void fullUpdateBrandWithPatch() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brand using partial update
        Brand partialUpdatedBrand = new Brand();
        partialUpdatedBrand.setId(brand.getId());

        partialUpdatedBrand
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .logoBrand(UPDATED_LOGO_BRAND)
            .logoBrandContentType(UPDATED_LOGO_BRAND_CONTENT_TYPE);

        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrand.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrandUpdatableFieldsEquals(partialUpdatedBrand, getPersistedBrand(partialUpdatedBrand));
    }

    @Test
    @Transactional
    void patchNonExistingBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, brandDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBrand() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the brand
        restBrandMockMvc
            .perform(delete(ENTITY_API_URL_ID, brand.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return brandRepository.count();
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

    protected Brand getPersistedBrand(Brand brand) {
        return brandRepository.findById(brand.getId()).orElseThrow();
    }

    protected void assertPersistedBrandToMatchAllProperties(Brand expectedBrand) {
        assertBrandAllPropertiesEquals(expectedBrand, getPersistedBrand(expectedBrand));
    }

    protected void assertPersistedBrandToMatchUpdatableProperties(Brand expectedBrand) {
        assertBrandAllUpdatablePropertiesEquals(expectedBrand, getPersistedBrand(expectedBrand));
    }
}
