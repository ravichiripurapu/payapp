import { BaseEntity } from './../../shared';

export class FilingStatus implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
