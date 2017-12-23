import { BaseEntity } from './../../shared';

export class PayrollEmployee implements BaseEntity {
    constructor(
        public id?: number,
        public companySchedule?: string,
        public incomeTaxState?: string,
        public unemploymentState?: string,
        public netPay?: number,
        public payStub?: string,
        public companyCode?: string,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
