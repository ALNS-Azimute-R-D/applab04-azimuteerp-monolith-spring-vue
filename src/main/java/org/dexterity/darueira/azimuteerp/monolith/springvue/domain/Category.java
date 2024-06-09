package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Category.
 */
@Entity
@Table(name = "tb_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "acronym", length = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "handler_clazz_name", length = 255)
    private String handlerClazzName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainCategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetCollections", "mainCategory", "ordersItemsLists" }, allowSetters = true)
    private Set<Article> articlesLists = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "articlesLists", "categoryParent", "childrenCategoriesLists" }, allowSetters = true)
    private Category categoryParent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryParent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articlesLists", "categoryParent", "childrenCategoriesLists" }, allowSetters = true)
    private Set<Category> childrenCategoriesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Category acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandlerClazzName() {
        return this.handlerClazzName;
    }

    public Category handlerClazzName(String handlerClazzName) {
        this.setHandlerClazzName(handlerClazzName);
        return this;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public Set<Article> getArticlesLists() {
        return this.articlesLists;
    }

    public void setArticlesLists(Set<Article> articles) {
        if (this.articlesLists != null) {
            this.articlesLists.forEach(i -> i.setMainCategory(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setMainCategory(this));
        }
        this.articlesLists = articles;
    }

    public Category articlesLists(Set<Article> articles) {
        this.setArticlesLists(articles);
        return this;
    }

    public Category addArticlesList(Article article) {
        this.articlesLists.add(article);
        article.setMainCategory(this);
        return this;
    }

    public Category removeArticlesList(Article article) {
        this.articlesLists.remove(article);
        article.setMainCategory(null);
        return this;
    }

    public Category getCategoryParent() {
        return this.categoryParent;
    }

    public void setCategoryParent(Category category) {
        this.categoryParent = category;
    }

    public Category categoryParent(Category category) {
        this.setCategoryParent(category);
        return this;
    }

    public Set<Category> getChildrenCategoriesLists() {
        return this.childrenCategoriesLists;
    }

    public void setChildrenCategoriesLists(Set<Category> categories) {
        if (this.childrenCategoriesLists != null) {
            this.childrenCategoriesLists.forEach(i -> i.setCategoryParent(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setCategoryParent(this));
        }
        this.childrenCategoriesLists = categories;
    }

    public Category childrenCategoriesLists(Set<Category> categories) {
        this.setChildrenCategoriesLists(categories);
        return this;
    }

    public Category addChildrenCategoriesList(Category category) {
        this.childrenCategoriesLists.add(category);
        category.setCategoryParent(this);
        return this;
    }

    public Category removeChildrenCategoriesList(Category category) {
        this.childrenCategoriesLists.remove(category);
        category.setCategoryParent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return getId() != null && getId().equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            "}";
    }
}
