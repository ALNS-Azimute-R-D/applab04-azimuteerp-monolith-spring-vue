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
 * A Brand.
 */
@Entity
@Table(name = "tb_brand")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "acronym", length = 20, nullable = false)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @Lob
    @Column(name = "logo_brand")
    private byte[] logoBrand;

    @Column(name = "logo_brand_content_type")
    private String logoBrandContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "brand", "toSuppliers", "productsLists", "stockLevelsLists" }, allowSetters = true)
    private Set<Product> productsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Brand id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Brand acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Brand name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Brand description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getLogoBrand() {
        return this.logoBrand;
    }

    public Brand logoBrand(byte[] logoBrand) {
        this.setLogoBrand(logoBrand);
        return this;
    }

    public void setLogoBrand(byte[] logoBrand) {
        this.logoBrand = logoBrand;
    }

    public String getLogoBrandContentType() {
        return this.logoBrandContentType;
    }

    public Brand logoBrandContentType(String logoBrandContentType) {
        this.logoBrandContentType = logoBrandContentType;
        return this;
    }

    public void setLogoBrandContentType(String logoBrandContentType) {
        this.logoBrandContentType = logoBrandContentType;
    }

    public Set<Product> getProductsLists() {
        return this.productsLists;
    }

    public void setProductsLists(Set<Product> products) {
        if (this.productsLists != null) {
            this.productsLists.forEach(i -> i.setBrand(null));
        }
        if (products != null) {
            products.forEach(i -> i.setBrand(this));
        }
        this.productsLists = products;
    }

    public Brand productsLists(Set<Product> products) {
        this.setProductsLists(products);
        return this;
    }

    public Brand addProductsList(Product product) {
        this.productsLists.add(product);
        product.setBrand(this);
        return this;
    }

    public Brand removeProductsList(Product product) {
        this.productsLists.remove(product);
        product.setBrand(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brand)) {
            return false;
        }
        return getId() != null && getId().equals(((Brand) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Brand{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", logoBrand='" + getLogoBrand() + "'" +
            ", logoBrandContentType='" + getLogoBrandContentType() + "'" +
            "}";
    }
}
