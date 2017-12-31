package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeePayEarning.
 */
@Entity
@Table(name = "employee_pay_earning")
public class EmployeePayEarning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "payroll_schedule_code")
    private String payrollScheduleCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "earning_code")
    private EarningCode earningCode;

    @Column(name = "earning_name")
    private String earningName;

    @Column(name = "hours")
    private Float hours;

    @Column(name = "period_amount")
    private Double periodAmount;

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public EmployeePayEarning code(String code) {
        this.code = code;
        return this;
    }

    public String getPayrollScheduleCode() {
        return payrollScheduleCode;
    }

    public void setPayrollScheduleCode(String payrollScheduleCode) {
        this.payrollScheduleCode = payrollScheduleCode;
    }

    public EmployeePayEarning payrollScheduleCode(String payrollScheduleCode) {
        this.payrollScheduleCode = payrollScheduleCode;
        return this;
    }

    public EarningCode getEarningCode() {
        return earningCode;
    }

    public EmployeePayEarning earningCode(EarningCode earningCode) {
        this.earningCode = earningCode;
        return this;
    }

    public void setEarningCode(EarningCode earningCode) {
        this.earningCode = earningCode;
    }

    public String getEarningName() {
        return earningName;
    }

    public EmployeePayEarning earningName(String earningName) {
        this.earningName = earningName;
        return this;
    }

    public void setEarningName(String earningName) {
        this.earningName = earningName;
    }

    public Float getHours() {
        return hours;
    }

    public EmployeePayEarning hours(Float hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public Double getPeriodAmount() {
        return periodAmount;
    }

    public EmployeePayEarning periodAmount(Double periodAmount) {
        this.periodAmount = periodAmount;
        return this;
    }

    public void setPeriodAmount(Double periodAmount) {
        this.periodAmount = periodAmount;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public EmployeePayEarning companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public EmployeePayEarning employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmployeePayEarning createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmployeePayEarning createdBy(String createdBy) {
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
        EmployeePayEarning employeePayEarning = (EmployeePayEarning) o;
        if (employeePayEarning.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeePayEarning.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeePayEarning{" +
            "id=" + getId() +
            ", earningsCode='" + getEarningCode() + "'" +
            ", hours=" + getHours() +
            ", periodAmount=" + getPeriodAmount() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
