package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeeStateTax.
 */
@Entity
@Table(name = "employee_state_tax")
public class EmployeeStateTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "filing_status")
    private String filingStatus;

    @Column(name = "exempt_from_with_holding")
    private Boolean exemptFromWithHolding;

    @Column(name = "exempt_from_sui")
    private Boolean exemptFromSUI;

    @Column(name = "exempt_from_futa")
    private Boolean exemptFromFUTA;

    @Column(name = "futa_exempt_reason")
    private String futaExemptReason;

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

    public String getState() {
        return state;
    }

    public EmployeeStateTax state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFilingStatus() {
        return filingStatus;
    }

    public EmployeeStateTax filingStatus(String filingStatus) {
        this.filingStatus = filingStatus;
        return this;
    }

    public void setFilingStatus(String filingStatus) {
        this.filingStatus = filingStatus;
    }

    public Boolean isExemptFromWithHolding() {
        return exemptFromWithHolding;
    }

    public EmployeeStateTax exemptFromWithHolding(Boolean exemptFromWithHolding) {
        this.exemptFromWithHolding = exemptFromWithHolding;
        return this;
    }

    public void setExemptFromWithHolding(Boolean exemptFromWithHolding) {
        this.exemptFromWithHolding = exemptFromWithHolding;
    }

    public Boolean isExemptFromSUI() {
        return exemptFromSUI;
    }

    public EmployeeStateTax exemptFromSUI(Boolean exemptFromSUI) {
        this.exemptFromSUI = exemptFromSUI;
        return this;
    }

    public void setExemptFromSUI(Boolean exemptFromSUI) {
        this.exemptFromSUI = exemptFromSUI;
    }

    public Boolean isExemptFromFUTA() {
        return exemptFromFUTA;
    }

    public EmployeeStateTax exemptFromFUTA(Boolean exemptFromFUTA) {
        this.exemptFromFUTA = exemptFromFUTA;
        return this;
    }

    public void setExemptFromFUTA(Boolean exemptFromFUTA) {
        this.exemptFromFUTA = exemptFromFUTA;
    }

    public String getFutaExemptReason() {
        return futaExemptReason;
    }

    public EmployeeStateTax futaExemptReason(String futaExemptReason) {
        this.futaExemptReason = futaExemptReason;
        return this;
    }

    public void setFutaExemptReason(String futaExemptReason) {
        this.futaExemptReason = futaExemptReason;
    }

    public Integer getAllowances() {
        return allowances;
    }

    public EmployeeStateTax allowances(Integer allowances) {
        this.allowances = allowances;
        return this;
    }

    public void setAllowances(Integer allowances) {
        this.allowances = allowances;
    }

    public Float getExtraWithHolding() {
        return extraWithHolding;
    }

    public EmployeeStateTax extraWithHolding(Float extraWithHolding) {
        this.extraWithHolding = extraWithHolding;
        return this;
    }

    public void setExtraWithHolding(Float extraWithHolding) {
        this.extraWithHolding = extraWithHolding;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public EmployeeStateTax employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmployeeStateTax createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmployeeStateTax createdBy(String createdBy) {
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
        EmployeeStateTax employeeStateTax = (EmployeeStateTax) o;
        if (employeeStateTax.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeStateTax.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeStateTax{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", filingStatus='" + getFilingStatus() + "'" +
            ", exemptFromWithHolding='" + isExemptFromWithHolding() + "'" +
            ", exemptFromSUI='" + isExemptFromSUI() + "'" +
            ", exemptFromFUTA='" + isExemptFromFUTA() + "'" +
            ", futaExemptReason='" + getFutaExemptReason() + "'" +
            ", allowances=" + getAllowances() +
            ", extraWithHolding=" + getExtraWithHolding() +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
