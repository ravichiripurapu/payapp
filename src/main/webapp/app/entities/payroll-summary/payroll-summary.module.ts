import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollSummaryService,
    PayrollSummaryPopupService,
    PayrollSummaryComponent,
    PayrollSummaryDetailComponent,
    PayrollSummaryDialogComponent,
    PayrollSummaryPopupComponent,
    PayrollSummaryDeletePopupComponent,
    PayrollSummaryDeleteDialogComponent,
    payrollSummaryRoute,
    payrollSummaryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollSummaryRoute,
    ...payrollSummaryPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollSummaryComponent,
        PayrollSummaryDetailComponent,
        PayrollSummaryDialogComponent,
        PayrollSummaryDeleteDialogComponent,
        PayrollSummaryPopupComponent,
        PayrollSummaryDeletePopupComponent,
    ],
    entryComponents: [
        PayrollSummaryComponent,
        PayrollSummaryDialogComponent,
        PayrollSummaryPopupComponent,
        PayrollSummaryDeleteDialogComponent,
        PayrollSummaryDeletePopupComponent,
    ],
    providers: [
        PayrollSummaryService,
        PayrollSummaryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollSummaryModule {}
