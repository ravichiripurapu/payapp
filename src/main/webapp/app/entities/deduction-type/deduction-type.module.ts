import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    DeductionTypeService,
    DeductionTypePopupService,
    DeductionTypeComponent,
    DeductionTypeDetailComponent,
    DeductionTypeDialogComponent,
    DeductionTypePopupComponent,
    DeductionTypeDeletePopupComponent,
    DeductionTypeDeleteDialogComponent,
    deductionTypeRoute,
    deductionTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...deductionTypeRoute,
    ...deductionTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DeductionTypeComponent,
        DeductionTypeDetailComponent,
        DeductionTypeDialogComponent,
        DeductionTypeDeleteDialogComponent,
        DeductionTypePopupComponent,
        DeductionTypeDeletePopupComponent,
    ],
    entryComponents: [
        DeductionTypeComponent,
        DeductionTypeDialogComponent,
        DeductionTypePopupComponent,
        DeductionTypeDeleteDialogComponent,
        DeductionTypeDeletePopupComponent,
    ],
    providers: [
        DeductionTypeService,
        DeductionTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappDeductionTypeModule {}
