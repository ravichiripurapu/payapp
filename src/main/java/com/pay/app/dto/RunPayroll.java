package com.pay.app.dto;

import com.pay.app.domain.EmployeePay;

import java.util.List;

public class RunPayroll {

    Integer currentYear;

    Integer currentPayPeriod;

    Integer totalPayPeriods;

    Integer totalEmployees;

    List<EmployeePay> employeePayList;

    public List<EmployeePay> getEmployeePayList() {
        return employeePayList;
    }

    public void setEmployeePayList(List<EmployeePay> employeePayList) {
        this.employeePayList = employeePayList;
    }

    public RunPayroll employeePayList(List<EmployeePay> employeePayList) {
        this.employeePayList = employeePayList;
        return this;
    }
}
