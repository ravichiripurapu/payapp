import { BaseEntity } from './../../shared';

export class EmployeeLocalTax implements BaseEntity {
    constructor(
        public id?: number,
        public filingStatus?: string,
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
