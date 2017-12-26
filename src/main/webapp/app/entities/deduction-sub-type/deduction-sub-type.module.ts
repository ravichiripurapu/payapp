import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    DeductionSubTypeService,
    DeductionSubTypePopupService,
    DeductionSubTypeComponent,
    DeductionSubTypeDetailComponent,
    DeductionSubTypeDialogComponent,
    DeductionSubTypePopupComponent,
    DeductionSubTypeDeletePopupComponent,
    DeductionSubTypeDeleteDialogComponent,
    deductionSubTypeRoute,
    deductionSubTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...deductionSubTypeRoute,
    ...deductionSubTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DeductionSubTypeComponent,
        DeductionSubTypeDetailComponent,
        DeductionSubTypeDialogComponent,
        DeductionSubTypeDeleteDialogComponent,
        DeductionSubTypePopupComponent,
        DeductionSubTypeDeletePopupComponent,
    ],
    entryComponents: [
        DeductionSubTypeComponent,
        DeductionSubTypeDialogComponent,
        DeductionSubTypePopupComponent,
        DeductionSubTypeDeleteDialogComponent,
        DeductionSubTypeDeletePopupComponent,
    ],
    providers: [
        DeductionSubTypeService,
        DeductionSubTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappDeductionSubTypeModule {}
