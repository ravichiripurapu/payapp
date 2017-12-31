package com.pay.app.domain;


import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A EmployeePayEarning.
 */
@Entity
@Table(name = "employee_pay")
public class EmployeePay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "payroll_schedule_code")
    private String payrollScheduleCode;

    @Column(name = "time_off")
    private Float timeOff;

    @Transient
    private List<EmployeePayEarning> employeePayEarnings;

    @Transient
    private List<EmployeePayDeduction> employeePayDeductions;

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

    public EmployeePay code(String code) {
        this.code = code;
        return this;
    }

    public String getPayrollScheduleCode() {
        return payrollScheduleCode;
    }

    public void setPayrollScheduleCode(String payrollScheduleCode) {
        this.payrollScheduleCode = payrollScheduleCode;
    }

    public EmployeePay payrollScheduleCode(String payrollScheduleCode) {
        this.payrollScheduleCode = payrollScheduleCode;
        return this;
    }

    public List<EmployeePayEarning> getEmployeePayEarnings() {
        return employeePayEarnings;
    }

    public void setEmployeePayEarnings(List<EmployeePayEarning> employeePayEarnings) {
        this.employeePayEarnings = employeePayEarnings;
    }

    public List<EmployeePayDeduction> getEmployeePayDeductions() {
        return employeePayDeductions;
    }

    public void setEmployeePayDeductions(List<EmployeePayDeduction> employeePayDeductions) {
        this.employeePayDeductions = employeePayDeductions;
    }

    public Float getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(Float timeOff) {
        this.timeOff = timeOff;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public EmployeePay companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public EmployeePay employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmployeePay createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmployeePay createdBy(String createdBy) {
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
        EmployeePay payrollEarning = (EmployeePay) o;
        if (payrollEarning.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payrollEarning.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeePayEarning{" +
            "id=" + getId() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
