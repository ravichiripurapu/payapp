import { BaseEntity } from './../../shared';

export class EmployeeStateTax implements BaseEntity {
    constructor(
        public id?: number,
        public state?: string,
        public filingStatus?: string,
        public exemptFromWithHolding?: boolean,
        public exemptFromSUI?: boolean,
        public exemptFromFUTA?: boolean,
        public futaExemptReason?: string,
        public allowances?: number,
        public extraWithHolding?: number,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
        this.exemptFromWithHolding = false;
        this.exemptFromSUI = false;
        this.exemptFromFUTA = false;
    }
}
