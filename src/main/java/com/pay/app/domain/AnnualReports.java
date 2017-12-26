package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AnnualReports.
 */
@Entity
@Table(name = "annual_reports")
public class AnnualReports implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "annual_report_type")
    private String annualReportType;

    @Column(name = "annual_report")
    private String annualReport;

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

    public AnnualReports year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAnnualReportType() {
        return annualReportType;
    }

    public AnnualReports annualReportType(String annualReportType) {
        this.annualReportType = annualReportType;
        return this;
    }

    public void setAnnualReportType(String annualReportType) {
        this.annualReportType = annualReportType;
    }

    public String getAnnualReport() {
        return annualReport;
    }

    public AnnualReports annualReport(String annualReport) {
        this.annualReport = annualReport;
        return this;
    }

    public void setAnnualReport(String annualReport) {
        this.annualReport = annualReport;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public AnnualReports companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public AnnualReports createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AnnualReports createdBy(String createdBy) {
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
        AnnualReports annualReports = (AnnualReports) o;
        if (annualReports.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annualReports.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnnualReports{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", annualReportType='" + getAnnualReportType() + "'" +
            ", annualReport='" + getAnnualReport() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
