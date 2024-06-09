package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProductAsserts.*;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Brand;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ProductRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ProductService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProductDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ProductMapper;
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
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_PRODUCT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_SKU = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_STANDARD_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_STANDARD_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_REORDER_LEVEL = 1;
    private static final Integer UPDATED_REORDER_LEVEL = 2;

    private static final Integer DEFAULT_TARGET_LEVEL = 1;
    private static final Integer UPDATED_TARGET_LEVEL = 2;

    private static final String DEFAULT_QUANTITY_PER_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY_PER_UNIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISCONTINUED = false;
    private static final Boolean UPDATED_DISCONTINUED = true;

    private static final Integer DEFAULT_MINIMUM_REORDER_QUANTITY = 1;
    private static final Integer UPDATED_MINIMUM_REORDER_QUANTITY = 2;

    private static final String DEFAULT_SUGGESTED_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SUGGESTED_CATEGORY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductRepository productRepository;

    @Mock
    private ProductRepository productRepositoryMock;

    @Autowired
    private ProductMapper productMapper;

    @Mock
    private ProductService productServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    private Product insertedProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productSKU(DEFAULT_PRODUCT_SKU)
            .productName(DEFAULT_PRODUCT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .standardCost(DEFAULT_STANDARD_COST)
            .listPrice(DEFAULT_LIST_PRICE)
            .reorderLevel(DEFAULT_REORDER_LEVEL)
            .targetLevel(DEFAULT_TARGET_LEVEL)
            .quantityPerUnit(DEFAULT_QUANTITY_PER_UNIT)
            .discontinued(DEFAULT_DISCONTINUED)
            .minimumReorderQuantity(DEFAULT_MINIMUM_REORDER_QUANTITY)
            .suggestedCategory(DEFAULT_SUGGESTED_CATEGORY)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        Brand brand;
        if (TestUtil.findAll(em, Brand.class).isEmpty()) {
            brand = BrandResourceIT.createEntity(em);
            em.persist(brand);
            em.flush();
        } else {
            brand = TestUtil.findAll(em, Brand.class).get(0);
        }
        product.setBrand(brand);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .productSKU(UPDATED_PRODUCT_SKU)
            .productName(UPDATED_PRODUCT_NAME)
            .description(UPDATED_DESCRIPTION)
            .standardCost(UPDATED_STANDARD_COST)
            .listPrice(UPDATED_LIST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targetLevel(UPDATED_TARGET_LEVEL)
            .quantityPerUnit(UPDATED_QUANTITY_PER_UNIT)
            .discontinued(UPDATED_DISCONTINUED)
            .minimumReorderQuantity(UPDATED_MINIMUM_REORDER_QUANTITY)
            .suggestedCategory(UPDATED_SUGGESTED_CATEGORY)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        Brand brand;
        if (TestUtil.findAll(em, Brand.class).isEmpty()) {
            brand = BrandResourceIT.createUpdatedEntity(em);
            em.persist(brand);
            em.flush();
        } else {
            brand = TestUtil.findAll(em, Brand.class).get(0);
        }
        product.setBrand(brand);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProduct != null) {
            productRepository.delete(insertedProduct);
            insertedProduct = null;
        }
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        var returnedProductDTO = om.readValue(
            restProductMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProductDTO.class
        );

        // Validate the Product in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProduct = productMapper.toEntity(returnedProductDTO);
        assertProductUpdatableFieldsEquals(returnedProduct, getPersistedProduct(returnedProduct));

        insertedProduct = returnedProduct;
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkListPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setListPrice(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);

        restProductMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiscontinuedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setDiscontinued(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);

        restProductMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        product.setActivationStatus(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.toDto(product);

        restProductMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productSKU").value(hasItem(DEFAULT_PRODUCT_SKU)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].standardCost").value(hasItem(sameNumber(DEFAULT_STANDARD_COST))))
            .andExpect(jsonPath("$.[*].listPrice").value(hasItem(sameNumber(DEFAULT_LIST_PRICE))))
            .andExpect(jsonPath("$.[*].reorderLevel").value(hasItem(DEFAULT_REORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].targetLevel").value(hasItem(DEFAULT_TARGET_LEVEL)))
            .andExpect(jsonPath("$.[*].quantityPerUnit").value(hasItem(DEFAULT_QUANTITY_PER_UNIT)))
            .andExpect(jsonPath("$.[*].discontinued").value(hasItem(DEFAULT_DISCONTINUED.booleanValue())))
            .andExpect(jsonPath("$.[*].minimumReorderQuantity").value(hasItem(DEFAULT_MINIMUM_REORDER_QUANTITY)))
            .andExpect(jsonPath("$.[*].suggestedCategory").value(hasItem(DEFAULT_SUGGESTED_CATEGORY)))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductsWithEagerRelationshipsIsEnabled() throws Exception {
        when(productServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(productRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productSKU").value(DEFAULT_PRODUCT_SKU))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.standardCost").value(sameNumber(DEFAULT_STANDARD_COST)))
            .andExpect(jsonPath("$.listPrice").value(sameNumber(DEFAULT_LIST_PRICE)))
            .andExpect(jsonPath("$.reorderLevel").value(DEFAULT_REORDER_LEVEL))
            .andExpect(jsonPath("$.targetLevel").value(DEFAULT_TARGET_LEVEL))
            .andExpect(jsonPath("$.quantityPerUnit").value(DEFAULT_QUANTITY_PER_UNIT))
            .andExpect(jsonPath("$.discontinued").value(DEFAULT_DISCONTINUED.booleanValue()))
            .andExpect(jsonPath("$.minimumReorderQuantity").value(DEFAULT_MINIMUM_REORDER_QUANTITY))
            .andExpect(jsonPath("$.suggestedCategory").value(DEFAULT_SUGGESTED_CATEGORY))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64.getEncoder().encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduct() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .productSKU(UPDATED_PRODUCT_SKU)
            .productName(UPDATED_PRODUCT_NAME)
            .description(UPDATED_DESCRIPTION)
            .standardCost(UPDATED_STANDARD_COST)
            .listPrice(UPDATED_LIST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targetLevel(UPDATED_TARGET_LEVEL)
            .quantityPerUnit(UPDATED_QUANTITY_PER_UNIT)
            .discontinued(UPDATED_DISCONTINUED)
            .minimumReorderQuantity(UPDATED_MINIMUM_REORDER_QUANTITY)
            .suggestedCategory(UPDATED_SUGGESTED_CATEGORY)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productDTO))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductToMatchAllProperties(updatedProduct);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productSKU(UPDATED_PRODUCT_SKU)
            .description(UPDATED_DESCRIPTION)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targetLevel(UPDATED_TARGET_LEVEL)
            .quantityPerUnit(UPDATED_QUANTITY_PER_UNIT)
            .discontinued(UPDATED_DISCONTINUED)
            .suggestedCategory(UPDATED_SUGGESTED_CATEGORY)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProduct, product), getPersistedProduct(product));
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productSKU(UPDATED_PRODUCT_SKU)
            .productName(UPDATED_PRODUCT_NAME)
            .description(UPDATED_DESCRIPTION)
            .standardCost(UPDATED_STANDARD_COST)
            .listPrice(UPDATED_LIST_PRICE)
            .reorderLevel(UPDATED_REORDER_LEVEL)
            .targetLevel(UPDATED_TARGET_LEVEL)
            .quantityPerUnit(UPDATED_QUANTITY_PER_UNIT)
            .discontinued(UPDATED_DISCONTINUED)
            .minimumReorderQuantity(UPDATED_MINIMUM_REORDER_QUANTITY)
            .suggestedCategory(UPDATED_SUGGESTED_CATEGORY)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductUpdatableFieldsEquals(partialUpdatedProduct, getPersistedProduct(partialUpdatedProduct));
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        product.setId(longCount.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        insertedProduct = productRepository.saveAndFlush(product);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productRepository.count();
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

    protected Product getPersistedProduct(Product product) {
        return productRepository.findById(product.getId()).orElseThrow();
    }

    protected void assertPersistedProductToMatchAllProperties(Product expectedProduct) {
        assertProductAllPropertiesEquals(expectedProduct, getPersistedProduct(expectedProduct));
    }

    protected void assertPersistedProductToMatchUpdatableProperties(Product expectedProduct) {
        assertProductAllUpdatablePropertiesEquals(expectedProduct, getPersistedProduct(expectedProduct));
    }
}
