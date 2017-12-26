import { BaseEntity } from './../../shared';

export class AnnualReports implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public annualReportType?: string,
        public annualReport?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
