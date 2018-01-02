package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.pay.app.domain.EmployeePay;
import com.pay.app.domain.EmployeePayEarning;
import com.pay.app.domain.PayrollSummary;
import com.pay.app.dto.RunPayroll;
import com.pay.app.repository.EmployeePayEarningRepository;
import com.pay.app.web.rest.errors.BadRequestAlertException;
import com.pay.app.web.rest.util.HeaderUtil;
import com.symmetry.ste.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing EmployeePayEarning.
 */
@RestController
@RequestMapping("/api")
public class RunPayrollResource {

    private final Logger log = LoggerFactory.getLogger(RunPayrollResource.class);

    private static final String ENTITY_NAME = "payrollSummary";

    private final EmployeePayEarningRepository employeePayEarningRepository;

    public RunPayrollResource(EmployeePayEarningRepository employeePayEarningRepository) {
        this.employeePayEarningRepository = employeePayEarningRepository;
    }

    /**
     * POST  /run-payroll : Create a new payrollEarnings.
     *
     * @param runPayroll the payrollEarnings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollEarnings, or with status 400 (Bad Request) if the payrollEarnings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/run-payroll")
    @Timed
    public ResponseEntity<PayrollSummary> createPayrollEarnings(@RequestBody RunPayroll runPayroll) throws URISyntaxException {
        log.debug("REST request to save EmployeePayEarning : {}", runPayroll);

        String STEhome =  "/opt/ste/ste-root";

        String jniHome = "/opt/ste/ste-interface-files/java";

        String FEDERAL_LOCATION_CODE = "00-000-0000";
        try
        {
            STEPayrollCalculator STE = new STEPayrollCalculator(jniHome, STEhome);
            Date today = new Date();
            STE.setPayrollRunParameters(today, 52, 13);

            List<EmployeePay> employeePayList = runPayroll.getEmployeePayList();

            for (EmployeePay employeePay : employeePayList) {

                // String employee
                String employee = employeePay.getEmployeeCode();



                STE.clearPayrollCalculations();

                // Federal Code with Annualized Calcuations (Regular Pay) -- Pay Check Amount
                // In case of supplemental like (Bonus or Overtime or Commission) --- Supplemental and
                // the second calc method should be flat.
                STE.setCalculationMethod(FEDERAL_LOCATION_CODE, CalcMethod.ANNUALIZED,CalcMethod.NONE);
                Money zero = Money.zero();

                List<EmployeePayEarning> employeePayEarnings = employeePay.getEmployeePayEarnings();

                for (EmployeePayEarning employeePayEarning : employeePayEarnings) {

                    // put employee wages
                    // zeros are the following order : month to date, quarter to date, year to date
                    // In general put the year to date values only.
                    // month to date is not needed
                    // quarter to date is not needed
                    STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(40.00),
                        new Money(5000.00), zero, zero, zero);
                }

                // Get EmployeeFederalTax and set the values appropriately

                // Set W4 Information
                // three zeros : additional with holding, year_to_date, most recent with holding
                STE.setFederalParameters(false, FederalFilingStatus.MARRIED, 2, true, zero,
                    zero, zero);

                // Set FICA Params here
                STE.setFICAParameters(false,false, zero, zero, true);

                // Set Medicare
                STE.setMedicareParameters(false, zero, zero);

                // Verify if the we need to set EIC.
                STE.setEICParameters(EarnedIncomeCredit.SINGLE, zero, false);

                // The code for Indianapolis, Indiana (Marion County) is "18-097-452890"
                // The state GNIS code is 18
                // The county GNIS code is 097
                // The City GNIS code is 452890

                // State Params (State Location code : 18 is state, 097 is county, 452890 is local"
                // state default rounding
                // three zeros : additional with holding, year_to_date, most recent with holding

                // Get Employee Resident Location
                // Get Employee Work Location

                STE.setStateParameters("18-097-452890", true, false,
                    StateRounding.DEFAULTROUNDING, zero, zero, zero, false);

                // Set State Unemployement Insurace for Alaska, New Jersey, and Pennsylvania states only.

                /**
                 *
                 * See Page 18 for example
                 *
                 There are currently three states that contain employee based state unemployment insurance. They
                 are Alaska, New Jersey, and Pennsylvania.

                 Unemployment insurance is withheld from employee wages as a percentage of the employee’s taxable earnings until a wage base has been reached.
                 While the Commonwealth of Pennsylvania based their SUI Calculations on gross Regular and Supple-
                 mental wages, the States of New Jersey and Alaska both base the SUI calculation on SUTA wages.
                 Thus, the WageType in setWages that is being considered in these two states is
                 SUTA_GROSS_WAGES.

                 */

                /**
                State Disability Insurance (SDI)
                There are currently five states and one territory that contain employee based state disability insur-
                    ance. They are California, Hawaii (Temporary Disability Insurance), New Jersey, New York, Rhode Is-
                    land, Puerto Rico.
                 */

                /**
                 Family Leave Insurance (FLI) The New Jersey family leave insurance tax is withheld from employee wages as a percentage of the em-
                 ployee’s taxable earnings until a wage base has been reached.

                 This tax is calculated on SUTA wages,
                 so the WageType that will need to be used is SUTA_GROSS_WAGES

                 */

                /**

                 The New York Family Leave Insurance tax is withheld from employee wages as a percentage of the
                 employee’s taxable earnings until a weekly maximum withholding has been reache
                 d.  This tax is calcu-
                 lated on Regular, Supplemental and Tip wages, so the WageType that will need to be used are REGU-
                 LAR, SUPPLEMENTAL and TIPS.


                 https://www.apspayroll.com/resources/payroll-taxes-rates-and-changes/suta-wage-bases/

                 */

                /**

                 New Jersey Health Care Subsidy Fund (HCSF)
                 The New Jersey health care subsidy fund is a Tax which is withheld from employee wages as a per-
                 centage of the employee’s taxable earnings until a wage base has been reached. The tax is currently
                 at a rate of 0%.

                 */

                /**

                 Workers Compensation (WC)
                 There are two states with employee based workers compensation. They are New M
                 exico and Oregon.
                 The New Mexico tax is based per quarter and is prorated, while the Oregon tax is on a per hour basis.
                 An example for New Mexico has been provided.

                 */

                // State calculation method.
                STE.setCalculationMethod("18-097-452890", CalcMethod.ANNUALIZED, CalcMethod.NONE);

                // State Wages
                // put employee wages
                // zeros are the following order : month to date, quarter to date, year to date
                // In general put the year to date values only.
                // month to date is not needed
                // quarter to date is not needed
                STE.setWages("18-097-452890", WageType.REGULAR, new Hours(40.00), new Money(5000.00),
                    zero, zero, zero);

                // Set County Parameters
                STE.setCountyParameters("18-097-452890", false, true);

                // The miscellaneous parameters vary by state
                // Indiana requires the following two parameters to withhold state tax

                // Each State might require misc params.
                STE.setStateMiscellaneousParameters("18-097-452890", "PERSONALEXEMPTIONS", "1");
                STE.setStateMiscellaneousParameters("18-097-452890", "DEPENDENTEXEMPTIONS", "2");

                // Call the calc Taxes
                STE.calculateTaxes();


                String version = STE.getSTEVersion();
                System.out.println("STE Version: " + version);
                System.out.println("FICA Withholding: " + STE.getFICACalc());
                System.out.println("Medicare Withholding: " + STE.getMedicareCalc());
                System.out.println("Federal Withholding: " + STE.getFederalCalc());
                System.out.println("Earned Inc. Credit: " + STE.getEICCalc());
                System.out.println("State Withholding: " + STE.getStateCalc("18-097-452890"));
                System.out.println("County Withholding: " + STE.getCountyCalc("18-097-452890"));
            }




            PayrollSummary payrollSummary = new PayrollSummary()
                .companyCode("test")
                .createdDate (LocalDate.now())
                .createdBy("System")
                .employeeDeductions(new Double(STE.getFederalCalc().doubleValue()))
                .payrollProcessingFee(new Double(35))
                .directDepositAmount(new Double(2000));

            return  ResponseEntity.created(new URI("/api/run-payroll/"))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "test"))
                .body(payrollSummary);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        throw new BadRequestAlertException("Your payroll cannot be processed", "userManagement", "idexists");



    }


}
