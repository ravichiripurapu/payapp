import { BaseEntity } from './../../shared';

export class PayrollFrequency implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
