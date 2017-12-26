import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public website?: string,
        public fein?: string,
        public defaultFullTimeHours?: number,
        public defaultPartTimeHours?: number,
        public defaultTemporaryHours?: number,
        public payrollFrequency?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
