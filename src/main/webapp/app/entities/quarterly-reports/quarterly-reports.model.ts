import { BaseEntity } from './../../shared';

export class QuarterlyReports implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public quarterNumber?: number,
        public quaterlyReport?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
