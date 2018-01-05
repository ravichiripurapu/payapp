package com.pay.app.domain;


import com.pay.app.enumerations.TaxCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CompanyEarningType.
 */
@Entity
@Table(name = "company_tax_info")
public class CompanyTaxInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_code")
    private TaxCode taxCode;

    @Column(name = "name")
    private String name;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "deposit_frequency")
    private String depositFrequency;

    @Column(name = "status")
    private String status;

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

    public CompanyTaxInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public CompanyTaxInfo taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public CompanyTaxInfo stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public CompanyTaxInfo rate(Float rate) {
        this.rate = rate;
        return this;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public CompanyTaxInfo effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public String getDepositFrequency() {
        return depositFrequency;
    }

    public void setDepositFrequency(String depositFrequency) {
        this.depositFrequency = depositFrequency;
    }

    public CompanyTaxInfo depositFrequency(String depositFrequency) {
        this.depositFrequency = depositFrequency;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CompanyTaxInfo status(String status) {
        this.status = status;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public CompanyTaxInfo companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public CompanyTaxInfo createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CompanyTaxInfo createdBy(String createdBy) {
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
        CompanyTaxInfo companyEarning = (CompanyTaxInfo) o;
        if (companyEarning.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyEarning.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyEarningType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
