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
 * A TypeOfActivity.
 */
@Entity
@Table(name = "tb_type_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfActivity implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfActivity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfActivity", "createdByUser", "assetCollections", "scheduledActivitiesLists" },
        allowSetters = true
    )
    private Set<Activity> activitiesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeOfActivity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TypeOfActivity acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public TypeOfActivity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeOfActivity description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandlerClazzName() {
        return this.handlerClazzName;
    }

    public TypeOfActivity handlerClazzName(String handlerClazzName) {
        this.setHandlerClazzName(handlerClazzName);
        return this;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public Set<Activity> getActivitiesLists() {
        return this.activitiesLists;
    }

    public void setActivitiesLists(Set<Activity> activities) {
        if (this.activitiesLists != null) {
            this.activitiesLists.forEach(i -> i.setTypeOfActivity(null));
        }
        if (activities != null) {
            activities.forEach(i -> i.setTypeOfActivity(this));
        }
        this.activitiesLists = activities;
    }

    public TypeOfActivity activitiesLists(Set<Activity> activities) {
        this.setActivitiesLists(activities);
        return this;
    }

    public TypeOfActivity addActivitiesList(Activity activity) {
        this.activitiesLists.add(activity);
        activity.setTypeOfActivity(this);
        return this;
    }

    public TypeOfActivity removeActivitiesList(Activity activity) {
        this.activitiesLists.remove(activity);
        activity.setTypeOfActivity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfActivity)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeOfActivity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfActivity{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            "}";
    }
}
