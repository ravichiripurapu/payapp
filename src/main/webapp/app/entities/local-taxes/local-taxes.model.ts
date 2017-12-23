import { BaseEntity } from './../../shared';

export class LocalTaxes implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
