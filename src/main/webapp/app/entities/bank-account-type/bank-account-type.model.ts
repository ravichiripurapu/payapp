import { BaseEntity } from './../../shared';

export class BankAccountType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
