import { BaseEntity } from './../../shared';

export class PayrollSummary implements BaseEntity {
    constructor(
        public id?: number,
        public directDepositAmount?: number,
        public paidByCheckAmount?: number,
        public employeeDeductions?: number,
        public employerDeductions?: number,
        public employeeTaxes?: number,
        public employerTaxes?: number,
        public payrollProcessingFee?: number,
        public totalCashRequirements?: number,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
