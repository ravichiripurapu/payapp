import { BaseEntity } from './../../shared';

export class CompanyStateTax implements BaseEntity {
    constructor(
        public id?: number,
        public stateCode?: string,
        public taxId?: string,
        public rate?: number,
        public effectiveDate?: any,
        public status?: string,
        public depositFrequency?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
