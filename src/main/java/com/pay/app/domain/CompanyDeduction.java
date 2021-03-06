package com.pay.app.domain;


import com.pay.app.enumerations.DeductionCode;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CompanyDeduction.
 */
@Entity
@Table(name = "company_deduction")
public class CompanyDeduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deduction_code")
    private DeductionCode deductionCode;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_by")
    private String createdBy;

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

    public CompanyDeduction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeductionCode getDeductionCode() {
        return deductionCode;
    }

    public void setDeductionCode(DeductionCode deductionCode) {
        this.deductionCode = deductionCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public CompanyDeduction companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public CompanyDeduction createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CompanyDeduction createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        CompanyDeduction companyDeduction = (CompanyDeduction) o;
        if (companyDeduction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDeduction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDeduction{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
