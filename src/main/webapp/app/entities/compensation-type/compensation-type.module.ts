import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompensationTypeService,
    CompensationTypePopupService,
    CompensationTypeComponent,
    CompensationTypeDetailComponent,
    CompensationTypeDialogComponent,
    CompensationTypePopupComponent,
    CompensationTypeDeletePopupComponent,
    CompensationTypeDeleteDialogComponent,
    compensationTypeRoute,
    compensationTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...compensationTypeRoute,
    ...compensationTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompensationTypeComponent,
        CompensationTypeDetailComponent,
        CompensationTypeDialogComponent,
        CompensationTypeDeleteDialogComponent,
        CompensationTypePopupComponent,
        CompensationTypeDeletePopupComponent,
    ],
    entryComponents: [
        CompensationTypeComponent,
        CompensationTypeDialogComponent,
        CompensationTypePopupComponent,
        CompensationTypeDeleteDialogComponent,
        CompensationTypeDeletePopupComponent,
    ],
    providers: [
        CompensationTypeService,
        CompensationTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompensationTypeModule {}
