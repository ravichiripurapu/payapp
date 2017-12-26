package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeeLocalTax.
 */
@Entity
@Table(name = "employee_local_tax")
public class EmployeeLocalTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filing_status")
    private String filingStatus;

    @Column(name = "exempt_from_with_holding")
    private Boolean exemptFromWithHolding;

    @Column(name = "allowances")
    private Integer allowances;

    @Column(name = "extra_with_holding")
    private Float extraWithHolding;

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

    public String getFilingStatus() {
        return filingStatus;
    }

    public EmployeeLocalTax filingStatus(String filingStatus) {
        this.filingStatus = filingStatus;
        return this;
    }

    public void setFilingStatus(String filingStatus) {
        this.filingStatus = filingStatus;
    }

    public Boolean isExemptFromWithHolding() {
        return exemptFromWithHolding;
    }

    public EmployeeLocalTax exemptFromWithHolding(Boolean exemptFromWithHolding) {
        this.exemptFromWithHolding = exemptFromWithHolding;
        return this;
    }

    public void setExemptFromWithHolding(Boolean exemptFromWithHolding) {
        this.exemptFromWithHolding = exemptFromWithHolding;
    }

    public Integer getAllowances() {
        return allowances;
    }

    public EmployeeLocalTax allowances(Integer allowances) {
        this.allowances = allowances;
        return this;
    }

    public void setAllowances(Integer allowances) {
        this.allowances = allowances;
    }

    public Float getExtraWithHolding() {
        return extraWithHolding;
    }

    public EmployeeLocalTax extraWithHolding(Float extraWithHolding) {
        this.extraWithHolding = extraWithHolding;
        return this;
    }

    public void setExtraWithHolding(Float extraWithHolding) {
        this.extraWithHolding = extraWithHolding;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public EmployeeLocalTax employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmployeeLocalTax createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmployeeLocalTax createdBy(String createdBy) {
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
        EmployeeLocalTax employeeLocalTax = (EmployeeLocalTax) o;
        if (employeeLocalTax.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeLocalTax.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeLocalTax{" +
            "id=" + getId() +
            ", filingStatus='" + getFilingStatus() + "'" +
            ", exemptFromWithHolding='" + isExemptFromWithHolding() + "'" +
            ", allowances=" + getAllowances() +
            ", extraWithHolding=" + getExtraWithHolding() +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
