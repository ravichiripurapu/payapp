import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollFrequencyService,
    PayrollFrequencyPopupService,
    PayrollFrequencyComponent,
    PayrollFrequencyDetailComponent,
    PayrollFrequencyDialogComponent,
    PayrollFrequencyPopupComponent,
    PayrollFrequencyDeletePopupComponent,
    PayrollFrequencyDeleteDialogComponent,
    payrollFrequencyRoute,
    payrollFrequencyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollFrequencyRoute,
    ...payrollFrequencyPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollFrequencyComponent,
        PayrollFrequencyDetailComponent,
        PayrollFrequencyDialogComponent,
        PayrollFrequencyDeleteDialogComponent,
        PayrollFrequencyPopupComponent,
        PayrollFrequencyDeletePopupComponent,
    ],
    entryComponents: [
        PayrollFrequencyComponent,
        PayrollFrequencyDialogComponent,
        PayrollFrequencyPopupComponent,
        PayrollFrequencyDeleteDialogComponent,
        PayrollFrequencyDeletePopupComponent,
    ],
    providers: [
        PayrollFrequencyService,
        PayrollFrequencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollFrequencyModule {}
