import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PayappMaritalStatusModule } from './marital-status/marital-status.module';
import { PayappGenderModule } from './gender/gender.module';
import { PayappFilingStatusModule } from './filing-status/filing-status.module';
import { PayappLocalTaxesModule } from './local-taxes/local-taxes.module';
import { PayappDepositTypeModule } from './deposit-type/deposit-type.module';
import { PayappBankAccountTypeModule } from './bank-account-type/bank-account-type.module';
import { PayappEmployeeTypeModule } from './employee-type/employee-type.module';
import { PayappFutaExemptReasonCodeModule } from './futa-exempt-reason-code/futa-exempt-reason-code.module';
import { PayappDepositFrequencyModule } from './deposit-frequency/deposit-frequency.module';
import { PayappCompensationTypeModule } from './compensation-type/compensation-type.module';
import { PayappDeductionTypeModule } from './deduction-type/deduction-type.module';
import { PayappDeductionSubTypeModule } from './deduction-sub-type/deduction-sub-type.module';
import { PayappStateModule } from './state/state.module';
import { PayappCompanyContactTypeModule } from './company-contact-type/company-contact-type.module';
import { PayappPayrollFrequencyModule } from './payroll-frequency/payroll-frequency.module';
import { PayappCompanyModule } from './company/company.module';
import { PayappCompanyContactModule } from './company-contact/company-contact.module';
import { PayappCompanyBankModule } from './company-bank/company-bank.module';
import { PayappCompanyStateTaxModule } from './company-state-tax/company-state-tax.module';
import { PayappCompanyLocalTaxModule } from './company-local-tax/company-local-tax.module';
import { PayappCompanyDepartmentModule } from './company-department/company-department.module';
import { PayappCompanyLocationModule } from './company-location/company-location.module';
import { PayappCompanyEarningModule } from './company-earning/company-earning.module';
import { PayappCompanyCompensationModule } from './company-compensation/company-compensation.module';
import { PayappCompanyDeductionModule } from './company-deduction/company-deduction.module';
import { PayappEmployeeModule } from './employee/employee.module';
import { PayappEmployeeBankModule } from './employee-bank/employee-bank.module';
import { PayappEmployeeFederalTaxModule } from './employee-federal-tax/employee-federal-tax.module';
import { PayappEmployeeStateTaxModule } from './employee-state-tax/employee-state-tax.module';
import { PayappEmployeeLocalTaxModule } from './employee-local-tax/employee-local-tax.module';
import { PayappEmployeeResidenceLocationModule } from './employee-residence-location/employee-residence-location.module';
import { PayappEmployeeWorkLocationModule } from './employee-work-location/employee-work-location.module';
import { PayappW2ReportsModule } from './w-2-reports/w-2-reports.module';
import { PayappAnnualReportsModule } from './annual-reports/annual-reports.module';
import { PayappQuarterlyReportsModule } from './quarterly-reports/quarterly-reports.module';
import { PayappPayrollScheduleModule } from './payroll-schedule/payroll-schedule.module';
import { PayappPayrollSummaryModule } from './payroll-summary/payroll-summary.module';
import { PayappPayrollEmployeeModule } from './payroll-employee/payroll-employee.module';
import { PayappPayrollEarningsModule } from './payroll-earnings/payroll-earnings.module';
import { PayappPayrollEmployeeTaxesModule } from './payroll-employee-taxes/payroll-employee-taxes.module';
import { PayappPayrollEmployerTaxesModule } from './payroll-employer-taxes/payroll-employer-taxes.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PayappMaritalStatusModule,
        PayappGenderModule,
        PayappFilingStatusModule,
        PayappLocalTaxesModule,
        PayappDepositTypeModule,
        PayappBankAccountTypeModule,
        PayappEmployeeTypeModule,
        PayappFutaExemptReasonCodeModule,
        PayappDepositFrequencyModule,
        PayappCompensationTypeModule,
        PayappDeductionTypeModule,
        PayappDeductionSubTypeModule,
        PayappStateModule,
        PayappCompanyContactTypeModule,
        PayappPayrollFrequencyModule,
        PayappCompanyModule,
        PayappCompanyContactModule,
        PayappCompanyBankModule,
        PayappCompanyStateTaxModule,
        PayappCompanyLocalTaxModule,
        PayappCompanyDepartmentModule,
        PayappCompanyLocationModule,
        PayappCompanyEarningModule,
        PayappCompanyCompensationModule,
        PayappCompanyDeductionModule,
        PayappEmployeeModule,
        PayappEmployeeBankModule,
        PayappEmployeeFederalTaxModule,
        PayappEmployeeStateTaxModule,
        PayappEmployeeLocalTaxModule,
        PayappEmployeeResidenceLocationModule,
        PayappEmployeeWorkLocationModule,
        PayappW2ReportsModule,
        PayappAnnualReportsModule,
        PayappQuarterlyReportsModule,
        PayappPayrollScheduleModule,
        PayappPayrollSummaryModule,
        PayappPayrollEmployeeModule,
        PayappPayrollEarningsModule,
        PayappPayrollEmployeeTaxesModule,
        PayappPayrollEmployerTaxesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEntityModule {}
