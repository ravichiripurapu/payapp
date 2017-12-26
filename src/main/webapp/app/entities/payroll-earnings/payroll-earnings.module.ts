import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollEarningsService,
    PayrollEarningsPopupService,
    PayrollEarningsComponent,
    PayrollEarningsDetailComponent,
    PayrollEarningsDialogComponent,
    PayrollEarningsPopupComponent,
    PayrollEarningsDeletePopupComponent,
    PayrollEarningsDeleteDialogComponent,
    payrollEarningsRoute,
    payrollEarningsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollEarningsRoute,
    ...payrollEarningsPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollEarningsComponent,
        PayrollEarningsDetailComponent,
        PayrollEarningsDialogComponent,
        PayrollEarningsDeleteDialogComponent,
        PayrollEarningsPopupComponent,
        PayrollEarningsDeletePopupComponent,
    ],
    entryComponents: [
        PayrollEarningsComponent,
        PayrollEarningsDialogComponent,
        PayrollEarningsPopupComponent,
        PayrollEarningsDeleteDialogComponent,
        PayrollEarningsDeletePopupComponent,
    ],
    providers: [
        PayrollEarningsService,
        PayrollEarningsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollEarningsModule {}
