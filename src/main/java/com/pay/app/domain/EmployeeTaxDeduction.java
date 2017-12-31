package com.pay.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeeTaxDeduction.
 */
@Entity
@Table(name = "employee_tax_deduction")
public class EmployeeTaxDeduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_code")
    private TaxCode taxCode;

    @Column(name = "tax_name")
    private String taxName;

    @Column(name = "with_holding_amount")
    private Double withHoldingAmount;

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

    public TaxCode getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(TaxCode taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Double getWithHoldingAmount() {
        return withHoldingAmount;
    }

    public void setWithHoldingAmount(Double withHoldingAmount) {
        this.withHoldingAmount = withHoldingAmount;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public EmployeeTaxDeduction employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public EmployeeTaxDeduction createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EmployeeTaxDeduction createdBy(String createdBy) {
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
        EmployeeTaxDeduction employeeTaxDeduction = (EmployeeTaxDeduction) o;
        if (employeeTaxDeduction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeTaxDeduction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeTaxDeduction{" +
            "id=" + getId() +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
