import { BaseEntity } from './../../shared';

export class CompanyContact implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public mobilePhone?: string,
        public homePhone?: string,
        public dob?: any,
        public ssn?: string,
        public companyContactType?: string,
        public verificationCode?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
