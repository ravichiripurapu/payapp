import { BaseEntity } from './../../shared';

export class CompanyLocation implements BaseEntity {
    constructor(
        public id?: number,
        public addressLine1?: string,
        public addressLine2?: string,
        public zip?: string,
        public city?: string,
        public state?: string,
        public country?: string,
        public headquarters?: boolean,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
        this.headquarters = false;
    }
}
