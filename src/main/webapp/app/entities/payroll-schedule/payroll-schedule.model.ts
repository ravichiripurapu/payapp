import { BaseEntity } from './../../shared';

export class PayrollSchedule implements BaseEntity {
    constructor(
        public id?: number,
        public checkDate?: any,
        public periodEnd?: any,
        public periodStart?: any,
        public approveDate?: any,
        public paymentStatus?: boolean,
        public year?: number,
        public companyCode?: string,
        public createdDate?: any,
        public createdBy?: string,
    ) {
        this.paymentStatus = false;
    }
}
