import { BaseEntity } from './../../shared';

export class PayrollEmployerTaxes implements BaseEntity {
    constructor(
        public id?: number,
        public taxCode?: string,
        public periodAmount?: number,
        public ytdAmount?: number,
        public companyCode?: string,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
