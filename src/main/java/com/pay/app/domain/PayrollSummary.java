package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PayrollSummary.
 */
@Entity
@Table(name = "payroll_summary")
public class PayrollSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direct_deposit_amount")
    private Double directDepositAmount;

    @Column(name = "paid_by_check_amount")
    private Double paidByCheckAmount;

    @Column(name = "employee_deductions")
    private Double employeeDeductions;

    @Column(name = "employer_deductions")
    private Double employerDeductions;

    @Column(name = "employee_taxes")
    private Double employeeTaxes;

    @Column(name = "employer_taxes")
    private Double employerTaxes;

    @Column(name = "payroll_processing_fee")
    private Double payrollProcessingFee;

    @Column(name = "total_cash_requirements")
    private Double totalCashRequirements;

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

    public Double getDirectDepositAmount() {
        return directDepositAmount;
    }

    public PayrollSummary directDepositAmount(Double directDepositAmount) {
        this.directDepositAmount = directDepositAmount;
        return this;
    }

    public void setDirectDepositAmount(Double directDepositAmount) {
        this.directDepositAmount = directDepositAmount;
    }

    public Double getPaidByCheckAmount() {
        return paidByCheckAmount;
    }

    public PayrollSummary paidByCheckAmount(Double paidByCheckAmount) {
        this.paidByCheckAmount = paidByCheckAmount;
        return this;
    }

    public void setPaidByCheckAmount(Double paidByCheckAmount) {
        this.paidByCheckAmount = paidByCheckAmount;
    }

    public Double getEmployeeDeductions() {
        return employeeDeductions;
    }

    public PayrollSummary employeeDeductions(Double employeeDeductions) {
        this.employeeDeductions = employeeDeductions;
        return this;
    }

    public void setEmployeeDeductions(Double employeeDeductions) {
        this.employeeDeductions = employeeDeductions;
    }

    public Double getEmployerDeductions() {
        return employerDeductions;
    }

    public PayrollSummary employerDeductions(Double employerDeductions) {
        this.employerDeductions = employerDeductions;
        return this;
    }

    public void setEmployerDeductions(Double employerDeductions) {
        this.employerDeductions = employerDeductions;
    }

    public Double getEmployeeTaxes() {
        return employeeTaxes;
    }

    public PayrollSummary employeeTaxes(Double employeeTaxes) {
        this.employeeTaxes = employeeTaxes;
        return this;
    }

    public void setEmployeeTaxes(Double employeeTaxes) {
        this.employeeTaxes = employeeTaxes;
    }

    public Double getEmployerTaxes() {
        return employerTaxes;
    }

    public PayrollSummary employerTaxes(Double employerTaxes) {
        this.employerTaxes = employerTaxes;
        return this;
    }

    public void setEmployerTaxes(Double employerTaxes) {
        this.employerTaxes = employerTaxes;
    }

    public Double getPayrollProcessingFee() {
        return payrollProcessingFee;
    }

    public PayrollSummary payrollProcessingFee(Double payrollProcessingFee) {
        this.payrollProcessingFee = payrollProcessingFee;
        return this;
    }

    public void setPayrollProcessingFee(Double payrollProcessingFee) {
        this.payrollProcessingFee = payrollProcessingFee;
    }

    public Double getTotalCashRequirements() {
        return totalCashRequirements;
    }

    public PayrollSummary totalCashRequirements(Double totalCashRequirements) {
        this.totalCashRequirements = totalCashRequirements;
        return this;
    }

    public void setTotalCashRequirements(Double totalCashRequirements) {
        this.totalCashRequirements = totalCashRequirements;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public PayrollSummary companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PayrollSummary createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PayrollSummary createdBy(String createdBy) {
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
        PayrollSummary payrollSummary = (PayrollSummary) o;
        if (payrollSummary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payrollSummary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayrollSummary{" +
            "id=" + getId() +
            ", directDepositAmount=" + getDirectDepositAmount() +
            ", paidByCheckAmount=" + getPaidByCheckAmount() +
            ", employeeDeductions=" + getEmployeeDeductions() +
            ", employerDeductions=" + getEmployerDeductions() +
            ", employeeTaxes=" + getEmployeeTaxes() +
            ", employerTaxes=" + getEmployerTaxes() +
            ", payrollProcessingFee=" + getPayrollProcessingFee() +
            ", totalCashRequirements=" + getTotalCashRequirements() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
