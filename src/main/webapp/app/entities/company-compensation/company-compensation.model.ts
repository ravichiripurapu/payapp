import { BaseEntity } from './../../shared';

export class CompanyCompensation implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public desc?: string,
        public compensationType?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
