import { BaseEntity } from './../../shared';

export class CompanyDepartment implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public departmentCode?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
