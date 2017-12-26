import { BaseEntity } from './../../shared';

export class PayrollEarnings implements BaseEntity {
    constructor(
        public id?: number,
        public earningsCode?: string,
        public hours?: number,
        public periodAmount?: number,
        public ytdAmount?: number,
        public companyCode?: string,
        public employeeCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
