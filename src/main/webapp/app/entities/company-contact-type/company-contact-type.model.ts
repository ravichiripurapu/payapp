import { BaseEntity } from './../../shared';

export class CompanyContactType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
