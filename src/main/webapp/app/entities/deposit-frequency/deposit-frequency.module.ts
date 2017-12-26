import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    DepositFrequencyService,
    DepositFrequencyPopupService,
    DepositFrequencyComponent,
    DepositFrequencyDetailComponent,
    DepositFrequencyDialogComponent,
    DepositFrequencyPopupComponent,
    DepositFrequencyDeletePopupComponent,
    DepositFrequencyDeleteDialogComponent,
    depositFrequencyRoute,
    depositFrequencyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...depositFrequencyRoute,
    ...depositFrequencyPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DepositFrequencyComponent,
        DepositFrequencyDetailComponent,
        DepositFrequencyDialogComponent,
        DepositFrequencyDeleteDialogComponent,
        DepositFrequencyPopupComponent,
        DepositFrequencyDeletePopupComponent,
    ],
    entryComponents: [
        DepositFrequencyComponent,
        DepositFrequencyDialogComponent,
        DepositFrequencyPopupComponent,
        DepositFrequencyDeleteDialogComponent,
        DepositFrequencyDeletePopupComponent,
    ],
    providers: [
        DepositFrequencyService,
        DepositFrequencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappDepositFrequencyModule {}
