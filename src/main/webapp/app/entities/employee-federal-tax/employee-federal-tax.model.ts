import { BaseEntity } from './../../shared';

export class EmployeeFederalTax implements BaseEntity {
    constructor(
        public id?: number,
        public filingStatusCode?: string,
        public exemptFromWithHolding?: boolean,
        public allowances?: number,
        public extraWithHolding?: number,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
        this.exemptFromWithHolding = false;
    }
}
