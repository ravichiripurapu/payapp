package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PayrollEmployerTaxes.
 */
@Entity
@Table(name = "payroll_employer_taxes")
public class PayrollEmployerTaxes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "period_amount")
    private Float periodAmount;

    @Column(name = "ytd_amount")
    private Float ytdAmount;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "employee_code")
    private String employeeCode;

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

    public String getTaxCode() {
        return taxCode;
    }

    public PayrollEmployerTaxes taxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Float getPeriodAmount() {
        return periodAmount;
    }

    public PayrollEmployerTaxes periodAmount(Float periodAmount) {
        this.periodAmount = periodAmount;
        return this;
    }

    public void setPeriodAmount(Float periodAmount) {
        this.periodAmount = periodAmount;
    }

    public Float getYtdAmount() {
        return ytdAmount;
    }

    public PayrollEmployerTaxes ytdAmount(Float ytdAmount) {
        this.ytdAmount = ytdAmount;
        return this;
    }

    public void setYtdAmount(Float ytdAmount) {
        this.ytdAmount = ytdAmount;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public PayrollEmployerTaxes companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public PayrollEmployerTaxes employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PayrollEmployerTaxes createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PayrollEmployerTaxes createdBy(String createdBy) {
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
        PayrollEmployerTaxes payrollEmployerTaxes = (PayrollEmployerTaxes) o;
        if (payrollEmployerTaxes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payrollEmployerTaxes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayrollEmployerTaxes{" +
            "id=" + getId() +
            ", taxCode='" + getTaxCode() + "'" +
            ", periodAmount=" + getPeriodAmount() +
            ", ytdAmount=" + getYtdAmount() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
