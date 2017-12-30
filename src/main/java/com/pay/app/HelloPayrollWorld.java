package com.pay.app;

import com.symmetry.ste.*;

import java.util.Date;

/**
 * Created by ravi on 12/25/17.
 */
public class HelloPayrollWorld {
    STEPayrollCalculator STE;


    /**
    public static void main(String[] args) {
        HelloPayrollWorld client = new HelloPayrollWorld();
        client.hello();
    }*/

    public HelloPayrollWorld(){
    }
    public void hello(){
        //String STEhome = "/opt/symmetry/ste/";
        String STEhome =  "/opt/ste/ste-root";
        //String jniHome = "/opt/symmetry/ste/java/";
        String jniHome = "/opt/ste/ste-interface-files/java";
        String arch = System.getProperty("os.name", "");
        if ( arch.toLowerCase().contains("win"))
        {
            STEhome = "c:/STE/ste-root/";
            jniHome = "c:/STE/ste-interface-files/java/";
        }
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
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
