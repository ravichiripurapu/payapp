import { BaseEntity } from './../../shared';

export class CompanyDeduction implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public desc?: string,
        public deductionType?: string,
        public deductionSubType?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
