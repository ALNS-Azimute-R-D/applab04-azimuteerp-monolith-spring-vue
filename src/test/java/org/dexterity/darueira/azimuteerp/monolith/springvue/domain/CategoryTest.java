package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArticleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CategoryTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CategoryTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void articlesListTest() {
        Category category = getCategoryRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        category.addArticlesList(articleBack);
        assertThat(category.getArticlesLists()).containsOnly(articleBack);
        assertThat(articleBack.getMainCategory()).isEqualTo(category);

        category.removeArticlesList(articleBack);
        assertThat(category.getArticlesLists()).doesNotContain(articleBack);
        assertThat(articleBack.getMainCategory()).isNull();

        category.articlesLists(new HashSet<>(Set.of(articleBack)));
        assertThat(category.getArticlesLists()).containsOnly(articleBack);
        assertThat(articleBack.getMainCategory()).isEqualTo(category);

        category.setArticlesLists(new HashSet<>());
        assertThat(category.getArticlesLists()).doesNotContain(articleBack);
        assertThat(articleBack.getMainCategory()).isNull();
    }

    @Test
    void categoryParentTest() {
        Category category = getCategoryRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        category.setCategoryParent(categoryBack);
        assertThat(category.getCategoryParent()).isEqualTo(categoryBack);

        category.categoryParent(null);
        assertThat(category.getCategoryParent()).isNull();
    }

    @Test
    void childrenCategoriesListTest() {
        Category category = getCategoryRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        category.addChildrenCategoriesList(categoryBack);
        assertThat(category.getChildrenCategoriesLists()).containsOnly(categoryBack);
        assertThat(categoryBack.getCategoryParent()).isEqualTo(category);

        category.removeChildrenCategoriesList(categoryBack);
        assertThat(category.getChildrenCategoriesLists()).doesNotContain(categoryBack);
        assertThat(categoryBack.getCategoryParent()).isNull();

        category.childrenCategoriesLists(new HashSet<>(Set.of(categoryBack)));
        assertThat(category.getChildrenCategoriesLists()).containsOnly(categoryBack);
        assertThat(categoryBack.getCategoryParent()).isEqualTo(category);

        category.setChildrenCategoriesLists(new HashSet<>());
        assertThat(category.getChildrenCategoriesLists()).doesNotContain(categoryBack);
        assertThat(categoryBack.getCategoryParent()).isNull();
    }
}
