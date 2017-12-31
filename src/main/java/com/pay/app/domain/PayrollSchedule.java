package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PayrollSchedule.
 */
@Entity
@Table(name = "payroll_schedule")
public class PayrollSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "check_date")
    private LocalDate checkDate;

    @Column(name = "period_end")
    private LocalDate periodEnd;

    @Column(name = "period_start")
    private LocalDate periodStart;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "payment_status")
    private Boolean paymentStatus;

    @Column(name = "jhi_year")
    private Integer year;

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

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public PayrollSchedule checkDate(LocalDate checkDate) {
        this.checkDate = checkDate;
        return this;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public PayrollSchedule periodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public PayrollSchedule code(String code) {
        this.code = code;
        return this;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public PayrollSchedule periodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
        return this;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public PayrollSchedule approveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
        return this;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public Boolean isPaymentStatus() {
        return paymentStatus;
    }

    public PayrollSchedule paymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getYear() {
        return year;
    }

    public PayrollSchedule year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public PayrollSchedule companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PayrollSchedule createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PayrollSchedule createdBy(String createdBy) {
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
        PayrollSchedule payrollSchedule = (PayrollSchedule) o;
        if (payrollSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payrollSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayrollSchedule{" +
            "id=" + getId() +
            ", checkDate='" + getCheckDate() + "'" +
            ", periodEnd='" + getPeriodEnd() + "'" +
            ", periodStart='" + getPeriodStart() + "'" +
            ", approveDate='" + getApproveDate() + "'" +
            ", paymentStatus='" + isPaymentStatus() + "'" +
            ", year=" + getYear() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
