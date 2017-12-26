import { BaseEntity } from './../../shared';

export class CompanyEarning implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public desc?: string,
        public abbreviation?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
