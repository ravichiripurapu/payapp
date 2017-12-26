package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PayrollEmployee.
 */
@Entity
@Table(name = "payroll_employee")
public class PayrollEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_schedule")
    private String companySchedule;

    @Column(name = "income_tax_state")
    private String incomeTaxState;

    @Column(name = "unemployment_state")
    private String unemploymentState;

    @Column(name = "net_pay")
    private Float netPay;

    @Column(name = "pay_stub")
    private String payStub;

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

    public String getCompanySchedule() {
        return companySchedule;
    }

    public PayrollEmployee companySchedule(String companySchedule) {
        this.companySchedule = companySchedule;
        return this;
    }

    public void setCompanySchedule(String companySchedule) {
        this.companySchedule = companySchedule;
    }

    public String getIncomeTaxState() {
        return incomeTaxState;
    }

    public PayrollEmployee incomeTaxState(String incomeTaxState) {
        this.incomeTaxState = incomeTaxState;
        return this;
    }

    public void setIncomeTaxState(String incomeTaxState) {
        this.incomeTaxState = incomeTaxState;
    }

    public String getUnemploymentState() {
        return unemploymentState;
    }

    public PayrollEmployee unemploymentState(String unemploymentState) {
        this.unemploymentState = unemploymentState;
        return this;
    }

    public void setUnemploymentState(String unemploymentState) {
        this.unemploymentState = unemploymentState;
    }

    public Float getNetPay() {
        return netPay;
    }

    public PayrollEmployee netPay(Float netPay) {
        this.netPay = netPay;
        return this;
    }

    public void setNetPay(Float netPay) {
        this.netPay = netPay;
    }

    public String getPayStub() {
        return payStub;
    }

    public PayrollEmployee payStub(String payStub) {
        this.payStub = payStub;
        return this;
    }

    public void setPayStub(String payStub) {
        this.payStub = payStub;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public PayrollEmployee companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public PayrollEmployee employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PayrollEmployee createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PayrollEmployee createdBy(String createdBy) {
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
        PayrollEmployee payrollEmployee = (PayrollEmployee) o;
        if (payrollEmployee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payrollEmployee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayrollEmployee{" +
            "id=" + getId() +
            ", companySchedule='" + getCompanySchedule() + "'" +
            ", incomeTaxState='" + getIncomeTaxState() + "'" +
            ", unemploymentState='" + getUnemploymentState() + "'" +
            ", netPay=" + getNetPay() +
            ", payStub='" + getPayStub() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
