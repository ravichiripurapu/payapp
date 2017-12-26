package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PayrollEmployeeTaxes.
 */
@Entity
@Table(name = "payroll_employee_taxes")
public class PayrollEmployeeTaxes implements Serializable {

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

    public PayrollEmployeeTaxes taxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Float getPeriodAmount() {
        return periodAmount;
    }

    public PayrollEmployeeTaxes periodAmount(Float periodAmount) {
        this.periodAmount = periodAmount;
        return this;
    }

    public void setPeriodAmount(Float periodAmount) {
        this.periodAmount = periodAmount;
    }

    public Float getYtdAmount() {
        return ytdAmount;
    }

    public PayrollEmployeeTaxes ytdAmount(Float ytdAmount) {
        this.ytdAmount = ytdAmount;
        return this;
    }

    public void setYtdAmount(Float ytdAmount) {
        this.ytdAmount = ytdAmount;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public PayrollEmployeeTaxes companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public PayrollEmployeeTaxes employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PayrollEmployeeTaxes createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PayrollEmployeeTaxes createdBy(String createdBy) {
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
        PayrollEmployeeTaxes payrollEmployeeTaxes = (PayrollEmployeeTaxes) o;
        if (payrollEmployeeTaxes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payrollEmployeeTaxes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayrollEmployeeTaxes{" +
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
