package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A QuarterlyReports.
 */
@Entity
@Table(name = "quarterly_reports")
public class QuarterlyReports implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "quarter_number")
    private Integer quarterNumber;

    @Column(name = "quaterly_report")
    private String quaterlyReport;

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

    public QuarterlyReports year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuarterNumber() {
        return quarterNumber;
    }

    public QuarterlyReports quarterNumber(Integer quarterNumber) {
        this.quarterNumber = quarterNumber;
        return this;
    }

    public void setQuarterNumber(Integer quarterNumber) {
        this.quarterNumber = quarterNumber;
    }

    public String getQuaterlyReport() {
        return quaterlyReport;
    }

    public QuarterlyReports quaterlyReport(String quaterlyReport) {
        this.quaterlyReport = quaterlyReport;
        return this;
    }

    public void setQuaterlyReport(String quaterlyReport) {
        this.quaterlyReport = quaterlyReport;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public QuarterlyReports companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public QuarterlyReports createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public QuarterlyReports createdBy(String createdBy) {
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
        QuarterlyReports quarterlyReports = (QuarterlyReports) o;
        if (quarterlyReports.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quarterlyReports.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuarterlyReports{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", quarterNumber=" + getQuarterNumber() +
            ", quaterlyReport='" + getQuaterlyReport() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
