package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArticleAsserts.*;
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
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Article;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.SizeOptionEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArticleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArticleService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArticleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArticleMapper;
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
 * Integration tests for the {@link ArticleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArticleResourceIT {

    private static final Long DEFAULT_INVENTORY_PRODUCT_ID = 1L;
    private static final Long UPDATED_INVENTORY_PRODUCT_ID = 2L;

    private static final String DEFAULT_SKU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SKU_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_VALUE = new BigDecimal(2);

    private static final SizeOptionEnum DEFAULT_ITEM_SIZE = SizeOptionEnum.S;
    private static final SizeOptionEnum UPDATED_ITEM_SIZE = SizeOptionEnum.M;

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArticleRepository articleRepository;

    @Mock
    private ArticleRepository articleRepositoryMock;

    @Autowired
    private ArticleMapper articleMapper;

    @Mock
    private ArticleService articleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleMockMvc;

    private Article article;

    private Article insertedArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .inventoryProductId(DEFAULT_INVENTORY_PRODUCT_ID)
            .skuCode(DEFAULT_SKU_CODE)
            .customName(DEFAULT_CUSTOM_NAME)
            .customDescription(DEFAULT_CUSTOM_DESCRIPTION)
            .priceValue(DEFAULT_PRICE_VALUE)
            .itemSize(DEFAULT_ITEM_SIZE)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return article;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createUpdatedEntity(EntityManager em) {
        Article article = new Article()
            .inventoryProductId(UPDATED_INVENTORY_PRODUCT_ID)
            .skuCode(UPDATED_SKU_CODE)
            .customName(UPDATED_CUSTOM_NAME)
            .customDescription(UPDATED_CUSTOM_DESCRIPTION)
            .priceValue(UPDATED_PRICE_VALUE)
            .itemSize(UPDATED_ITEM_SIZE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return article;
    }

    @BeforeEach
    public void initTest() {
        article = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedArticle != null) {
            articleRepository.delete(insertedArticle);
            insertedArticle = null;
        }
    }

    @Test
    @Transactional
    void createArticle() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);
        var returnedArticleDTO = om.readValue(
            restArticleMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArticleDTO.class
        );

        // Validate the Article in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArticle = articleMapper.toEntity(returnedArticleDTO);
        assertArticleUpdatableFieldsEquals(returnedArticle, getPersistedArticle(returnedArticle));

        insertedArticle = returnedArticle;
    }

    @Test
    @Transactional
    void createArticleWithExistingId() throws Exception {
        // Create the Article with an existing ID
        article.setId(1L);
        ArticleDTO articleDTO = articleMapper.toDto(article);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInventoryProductIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setInventoryProductId(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkItemSizeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setItemSize(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setActivationStatus(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticles() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryProductId").value(hasItem(DEFAULT_INVENTORY_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].skuCode").value(hasItem(DEFAULT_SKU_CODE)))
            .andExpect(jsonPath("$.[*].customName").value(hasItem(DEFAULT_CUSTOM_NAME)))
            .andExpect(jsonPath("$.[*].customDescription").value(hasItem(DEFAULT_CUSTOM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priceValue").value(hasItem(sameNumber(DEFAULT_PRICE_VALUE))))
            .andExpect(jsonPath("$.[*].itemSize").value(hasItem(DEFAULT_ITEM_SIZE.toString())))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArticlesWithEagerRelationshipsIsEnabled() throws Exception {
        when(articleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArticleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(articleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArticlesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(articleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArticleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(articleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArticle() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.inventoryProductId").value(DEFAULT_INVENTORY_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.skuCode").value(DEFAULT_SKU_CODE))
            .andExpect(jsonPath("$.customName").value(DEFAULT_CUSTOM_NAME))
            .andExpect(jsonPath("$.customDescription").value(DEFAULT_CUSTOM_DESCRIPTION))
            .andExpect(jsonPath("$.priceValue").value(sameNumber(DEFAULT_PRICE_VALUE)))
            .andExpect(jsonPath("$.itemSize").value(DEFAULT_ITEM_SIZE.toString()))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticle() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .inventoryProductId(UPDATED_INVENTORY_PRODUCT_ID)
            .skuCode(UPDATED_SKU_CODE)
            .customName(UPDATED_CUSTOM_NAME)
            .customDescription(UPDATED_CUSTOM_DESCRIPTION)
            .priceValue(UPDATED_PRICE_VALUE)
            .itemSize(UPDATED_ITEM_SIZE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        ArticleDTO articleDTO = articleMapper.toDto(updatedArticle);

        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArticleToMatchAllProperties(updatedArticle);
    }

    @Test
    @Transactional
    void putNonExistingArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle.customDescription(UPDATED_CUSTOM_DESCRIPTION);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedArticle, article), getPersistedArticle(article));
    }

    @Test
    @Transactional
    void fullUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .inventoryProductId(UPDATED_INVENTORY_PRODUCT_ID)
            .skuCode(UPDATED_SKU_CODE)
            .customName(UPDATED_CUSTOM_NAME)
            .customDescription(UPDATED_CUSTOM_DESCRIPTION)
            .priceValue(UPDATED_PRICE_VALUE)
            .itemSize(UPDATED_ITEM_SIZE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleUpdatableFieldsEquals(partialUpdatedArticle, getPersistedArticle(partialUpdatedArticle));
    }

    @Test
    @Transactional
    void patchNonExistingArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticle() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the article
        restArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, article.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return articleRepository.count();
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

    protected Article getPersistedArticle(Article article) {
        return articleRepository.findById(article.getId()).orElseThrow();
    }

    protected void assertPersistedArticleToMatchAllProperties(Article expectedArticle) {
        assertArticleAllPropertiesEquals(expectedArticle, getPersistedArticle(expectedArticle));
    }

    protected void assertPersistedArticleToMatchUpdatableProperties(Article expectedArticle) {
        assertArticleAllUpdatablePropertiesEquals(expectedArticle, getPersistedArticle(expectedArticle));
    }
}
