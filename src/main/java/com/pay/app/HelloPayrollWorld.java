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
    }**/

    public HelloPayrollWorld(){
    }
    public void hello(){
        //String STEhome = "/opt/symmetry/ste/";
        String STEhome = System.getProperty("user.dir") + "/src/main/resources/ste/ste-root";
        //String jniHome = "/opt/symmetry/ste/java/";
        String jniHome = System.getProperty("user.dir") + "/src/main/resources/ste/ste-interface-files/java";
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
            STE.setCalculationMethod(FEDERAL_LOCATION_CODE, CalcMethod.ANNUALIZED,CalcMethod.NONE);
            Money zero = Money.zero();
            STE.setWages(FEDERAL_LOCATION_CODE, WageType.REGULAR, new Hours(40.00),
                new Money(5000.00), zero, zero, zero);
            STE.setFederalParameters(false, FederalFilingStatus.MARRIED, 2, true, zero,
                zero, zero);
            STE.setFICAParameters(false,false, zero, zero, true);
            STE.setMedicareParameters(false, zero, zero);
            STE.setEICParameters(EarnedIncomeCredit.SINGLE, zero, false);
            // The code for Indianapolis, Indiana (Marion County) is "18-097-452890"
            // The state GNIS code is 18
            // The county GNIS code is 097
            // The City GNIS code is 452890

            STE.setStateParameters("18-097-452890", true, false,
                StateRounding.DEFAULTROUNDING, zero, zero, zero, false);
            STE.setCalculationMethod("18-097-452890", CalcMethod.ANNUALIZED,
                CalcMethod.NONE);
            STE.setWages("18-097-452890", WageType.REGULAR, new Hours(40.00), new Money(5000.00),
                zero, zero, zero);
            STE.setCountyParameters("18-097-452890", false, true);
            // The miscellaneous parameters vary by state
            // Indiana requires the following two parameters to withhold state tax
            STE.setStateMiscellaneousParameters("18-097-452890", "PERSONALEXEMPTIONS", "1");
            STE.setStateMiscellaneousParameters("18-097-452890", "DEPENDENTEXEMPTIONS", "2");
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
