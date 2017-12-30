package com.pay.app.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tax_payment")
public class TaxPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "amount_with_holding")
    private Float amountWithHolding;

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

    public String getTaxId() {
        return taxId;
    }

    public TaxPayment taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxType() {
        return taxType;
    }

    public TaxPayment taxType(String taxType) {
        this.taxType = taxType;
        return this;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public Float getAmountWithHolding() {
        return amountWithHolding;
    }

    public TaxPayment amountWithHolding(Float amountWithHolding) {
        this.amountWithHolding = amountWithHolding;
        return this;
    }

    public void setAmountWithHolding(Float amountWithHolding) {
        this.amountWithHolding = amountWithHolding;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public TaxPayment employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public TaxPayment createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TaxPayment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public TaxPayment companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
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
            ", taxId='" + getTaxId() + "'" +
            ", taxType='" + getTaxType() + "'" +
            ", amountWithHolding=" + getAmountWithHolding() +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
