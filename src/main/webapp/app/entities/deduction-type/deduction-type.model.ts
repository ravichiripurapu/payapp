import { BaseEntity } from './../../shared';

export class DeductionType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
