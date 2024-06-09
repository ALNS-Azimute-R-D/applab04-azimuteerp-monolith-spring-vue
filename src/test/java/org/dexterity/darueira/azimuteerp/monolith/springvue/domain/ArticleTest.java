package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArticleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CategoryTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrderItemTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = getArticleSample1();
        Article article2 = new Article();
        assertThat(article1).isNotEqualTo(article2);

        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);

        article2 = getArticleSample2();
        assertThat(article1).isNotEqualTo(article2);
    }

    @Test
    void assetCollectionTest() {
        Article article = getArticleRandomSampleGenerator();
        AssetCollection assetCollectionBack = getAssetCollectionRandomSampleGenerator();

        article.addAssetCollection(assetCollectionBack);
        assertThat(article.getAssetCollections()).containsOnly(assetCollectionBack);

        article.removeAssetCollection(assetCollectionBack);
        assertThat(article.getAssetCollections()).doesNotContain(assetCollectionBack);

        article.assetCollections(new HashSet<>(Set.of(assetCollectionBack)));
        assertThat(article.getAssetCollections()).containsOnly(assetCollectionBack);

        article.setAssetCollections(new HashSet<>());
        assertThat(article.getAssetCollections()).doesNotContain(assetCollectionBack);
    }

    @Test
    void mainCategoryTest() {
        Article article = getArticleRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        article.setMainCategory(categoryBack);
        assertThat(article.getMainCategory()).isEqualTo(categoryBack);

        article.mainCategory(null);
        assertThat(article.getMainCategory()).isNull();
    }

    @Test
    void ordersItemsListTest() {
        Article article = getArticleRandomSampleGenerator();
        OrderItem orderItemBack = getOrderItemRandomSampleGenerator();

        article.addOrdersItemsList(orderItemBack);
        assertThat(article.getOrdersItemsLists()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getArticle()).isEqualTo(article);

        article.removeOrdersItemsList(orderItemBack);
        assertThat(article.getOrdersItemsLists()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getArticle()).isNull();

        article.ordersItemsLists(new HashSet<>(Set.of(orderItemBack)));
        assertThat(article.getOrdersItemsLists()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getArticle()).isEqualTo(article);

        article.setOrdersItemsLists(new HashSet<>());
        assertThat(article.getOrdersItemsLists()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getArticle()).isNull();
    }
}
