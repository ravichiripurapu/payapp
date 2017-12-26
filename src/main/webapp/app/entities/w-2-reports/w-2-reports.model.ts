import { BaseEntity } from './../../shared';

export class W2Reports implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public w2Report?: string,
        public employeeCode?: string,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
