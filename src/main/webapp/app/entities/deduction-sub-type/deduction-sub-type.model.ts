import { BaseEntity } from './../../shared';

export class DeductionSubType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
