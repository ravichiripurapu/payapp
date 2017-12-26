import { BaseEntity } from './../../shared';

export class DepositFrequency implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
