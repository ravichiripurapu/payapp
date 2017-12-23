package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CompanyStateTax.
 */
@Entity
@Table(name = "company_state_tax")
public class CompanyStateTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "status")
    private String status;

    @Column(name = "deposit_frequency")
    private String depositFrequency;

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

    public String getStateCode() {
        return stateCode;
    }

    public CompanyStateTax stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getTaxId() {
        return taxId;
    }

    public CompanyStateTax taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Float getRate() {
        return rate;
    }

    public CompanyStateTax rate(Float rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public CompanyStateTax effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getStatus() {
        return status;
    }

    public CompanyStateTax status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepositFrequency() {
        return depositFrequency;
    }

    public CompanyStateTax depositFrequency(String depositFrequency) {
        this.depositFrequency = depositFrequency;
        return this;
    }

    public void setDepositFrequency(String depositFrequency) {
        this.depositFrequency = depositFrequency;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public CompanyStateTax companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public CompanyStateTax createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CompanyStateTax createdBy(String createdBy) {
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
        CompanyStateTax companyStateTax = (CompanyStateTax) o;
        if (companyStateTax.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyStateTax.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyStateTax{" +
            "id=" + getId() +
            ", stateCode='" + getStateCode() + "'" +
            ", taxId='" + getTaxId() + "'" +
            ", rate=" + getRate() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", depositFrequency='" + getDepositFrequency() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
