import { BaseEntity } from './../../shared';

export class CompanyBank implements BaseEntity {
    constructor(
        public id?: number,
        public accountType?: string,
        public routingNumber?: string,
        public accountNumber?: string,
        public bankName?: string,
        public percentDeposit?: number,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
    }
}
