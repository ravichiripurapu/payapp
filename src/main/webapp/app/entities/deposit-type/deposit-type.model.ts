import { BaseEntity } from './../../shared';

export class DepositType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
