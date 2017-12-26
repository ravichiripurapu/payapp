package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CompensationType.
 */
@Entity
@Table(name = "compensation_type")
public class CompensationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CompensationType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompensationType compensationType = (CompensationType) o;
        if (compensationType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compensationType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompensationType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
