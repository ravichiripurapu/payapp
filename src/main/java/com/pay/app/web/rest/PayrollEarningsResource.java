package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollEarnings;

import com.pay.app.domain.PayrollSummary;
import com.pay.app.repository.PayrollEarningsRepository;
import com.pay.app.web.rest.errors.BadRequestAlertException;
import com.pay.app.web.rest.util.HeaderUtil;
import com.symmetry.ste.*;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PayrollEarnings.
 */
@RestController
@RequestMapping("/api")
public class PayrollEarningsResource {

    private final Logger log = LoggerFactory.getLogger(PayrollEarningsResource.class);

    private static final String ENTITY_NAME = "payrollSummary";

    private final PayrollEarningsRepository payrollEarningsRepository;

    public PayrollEarningsResource(PayrollEarningsRepository payrollEarningsRepository) {
        this.payrollEarningsRepository = payrollEarningsRepository;
    }

    /**
     * POST  /payroll-earnings : Create a new payrollEarnings.
     *
     * @param payrollEarnings the payrollEarnings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollEarnings, or with status 400 (Bad Request) if the payrollEarnings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/run-payroll")
    @Timed
    public ResponseEntity<PayrollSummary> createPayrollEarnings(@RequestBody List<PayrollEarnings> payrollEarnings) throws URISyntaxException {
        log.debug("REST request to save PayrollEarnings : {}", payrollEarnings);

        String STEhome =  "/opt/ste/ste-root";

        String jniHome = "/opt/ste/ste-interface-files/java";

        String FEDERAL_LOCATION_CODE = "00-000-0000";
        try
        {
            STEPayrollCalculator STE = new STEPayrollCalculator(jniHome, STEhome);
            Date today = new Date();
            STE.setPayrollRunParameters(today, 52, 13);
            STE.clearPayrollCalculations();

            // Federal Code with Annualized Calcuations (Regular Pay) -- Pay Check Amount
            // In case of supplemental like (Bonus or Overtime or Commission) --- Supplemental and
            // the second calc method should be flat.
            STE.setCalculationMethod(FEDERAL_LOCATION_CODE, CalcMethod.ANNUALIZED,CalcMethod.NONE);
            Money zero = Money.zero();

            // put employee wages
            // zeros are the following order : month to date, quarter to date, year to date
            // In general put the year to date values only.
            // month to date is not needed
            // quarter to date is not needed
            STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(40.00),
                new Money(5000.00), zero, zero, zero);

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
            STE.setStateParameters("18-097-452890", true, false,
                StateRounding.DEFAULTROUNDING, zero, zero, zero, false);

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

            PayrollSummary payrollSummary = new PayrollSummary()
                .companyCode("test")
                .createdDate (LocalDate.now())
                .createdBy("System")
                .employeeDeductions(new Double(STE.getFederalCalc().doubleValue()))
                .payrollProcessingFee(new Double(35))
                .directDepositAmount(new Double(2000));

            return ResponseEntity.created(new URI("/api/payroll-earnings/" + payrollSummary.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, payrollSummary.getId().toString()))
                .body(payrollSummary);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        throw new BadRequestAlertException("Your payroll cannot be processed", "userManagement", "idexists");



    }


}
