import { BaseEntity } from './../../shared';

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public mobilePhoneNumber?: string,
        public homePhoneNumber?: string,
        public dob?: any,
        public ssn?: string,
        public jobTitle?: string,
        public gender?: string,
        public maritalStatus?: string,
        public employeeNumber?: string,
        public hireDate?: any,
        public workPhoneNumber?: string,
        public employeeType?: string,
        public department?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
