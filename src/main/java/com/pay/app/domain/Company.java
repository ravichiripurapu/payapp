package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "website")
    private String website;

    @Column(name = "fein")
    private String fein;

    @Column(name = "default_full_time_hours")
    private Integer defaultFullTimeHours;

    @Column(name = "default_part_time_hours")
    private Integer defaultPartTimeHours;

    @Column(name = "default_temporary_hours")
    private Integer defaultTemporaryHours;

    @Column(name = "payroll_frequency")
    private String payrollFrequency;

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

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public Company website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFein() {
        return fein;
    }

    public Company fein(String fein) {
        this.fein = fein;
        return this;
    }

    public void setFein(String fein) {
        this.fein = fein;
    }

    public Integer getDefaultFullTimeHours() {
        return defaultFullTimeHours;
    }

    public Company defaultFullTimeHours(Integer defaultFullTimeHours) {
        this.defaultFullTimeHours = defaultFullTimeHours;
        return this;
    }

    public void setDefaultFullTimeHours(Integer defaultFullTimeHours) {
        this.defaultFullTimeHours = defaultFullTimeHours;
    }

    public Integer getDefaultPartTimeHours() {
        return defaultPartTimeHours;
    }

    public Company defaultPartTimeHours(Integer defaultPartTimeHours) {
        this.defaultPartTimeHours = defaultPartTimeHours;
        return this;
    }

    public void setDefaultPartTimeHours(Integer defaultPartTimeHours) {
        this.defaultPartTimeHours = defaultPartTimeHours;
    }

    public Integer getDefaultTemporaryHours() {
        return defaultTemporaryHours;
    }

    public Company defaultTemporaryHours(Integer defaultTemporaryHours) {
        this.defaultTemporaryHours = defaultTemporaryHours;
        return this;
    }

    public void setDefaultTemporaryHours(Integer defaultTemporaryHours) {
        this.defaultTemporaryHours = defaultTemporaryHours;
    }

    public String getPayrollFrequency() {
        return payrollFrequency;
    }

    public Company payrollFrequency(String payrollFrequency) {
        this.payrollFrequency = payrollFrequency;
        return this;
    }

    public void setPayrollFrequency(String payrollFrequency) {
        this.payrollFrequency = payrollFrequency;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Company createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Company createdBy(String createdBy) {
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", fein='" + getFein() + "'" +
            ", defaultFullTimeHours=" + getDefaultFullTimeHours() +
            ", defaultPartTimeHours=" + getDefaultPartTimeHours() +
            ", defaultTemporaryHours=" + getDefaultTemporaryHours() +
            ", payrollFrequency='" + getPayrollFrequency() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
