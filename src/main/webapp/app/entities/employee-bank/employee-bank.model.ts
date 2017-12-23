import { BaseEntity } from './../../shared';

export class EmployeeBank implements BaseEntity {
    constructor(
        public id?: number,
        public accountType?: string,
        public routingNumber?: string,
        public accountNumber?: string,
        public bankName?: string,
        public percentDeposit?: number,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
