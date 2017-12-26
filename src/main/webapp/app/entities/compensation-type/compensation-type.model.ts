import { BaseEntity } from './../../shared';

export class CompensationType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
