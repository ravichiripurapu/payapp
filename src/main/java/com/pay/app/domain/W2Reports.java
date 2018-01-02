package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A W2Reports.
 */
@Entity
@Table(name = "w_2_reports")
public class W2Reports implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "w2_report_location")
    private String w2ReportLocation;

    @Column(name = "employee_code")
    private String employeeCode;

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

    public Integer getYear() {
        return year;
    }

    public W2Reports year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getw2ReportLocation() {
        return w2ReportLocation;
    }

    public W2Reports w2ReportLocation(String w2ReportLocation) {
        this.w2ReportLocation = w2ReportLocation;
        return this;
    }

    public void setw2ReportLocation(String w2ReportLocation) {
        this.w2ReportLocation = w2ReportLocation;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public W2Reports employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public W2Reports companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public W2Reports createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public W2Reports createdBy(String createdBy) {
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
        W2Reports w2Reports = (W2Reports) o;
        if (w2Reports.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), w2Reports.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "W2Reports{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", w2Report='" + getw2ReportLocation() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
