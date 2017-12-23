import { BaseEntity } from './../../shared';

export class EmployeeResidenceLocation implements BaseEntity {
    constructor(
        public id?: number,
        public addressLine1?: string,
        public addressLine2?: string,
        public zip?: string,
        public city?: string,
        public state?: string,
        public country?: string,
        public locationCode?: string,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
