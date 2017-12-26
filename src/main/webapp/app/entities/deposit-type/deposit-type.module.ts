import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    DepositTypeService,
    DepositTypePopupService,
    DepositTypeComponent,
    DepositTypeDetailComponent,
    DepositTypeDialogComponent,
    DepositTypePopupComponent,
    DepositTypeDeletePopupComponent,
    DepositTypeDeleteDialogComponent,
    depositTypeRoute,
    depositTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...depositTypeRoute,
    ...depositTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DepositTypeComponent,
        DepositTypeDetailComponent,
        DepositTypeDialogComponent,
        DepositTypeDeleteDialogComponent,
        DepositTypePopupComponent,
        DepositTypeDeletePopupComponent,
    ],
    entryComponents: [
        DepositTypeComponent,
        DepositTypeDialogComponent,
        DepositTypePopupComponent,
        DepositTypeDeleteDialogComponent,
        DepositTypeDeletePopupComponent,
    ],
    providers: [
        DepositTypeService,
        DepositTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappDepositTypeModule {}
