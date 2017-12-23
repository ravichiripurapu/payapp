import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollScheduleService,
    PayrollSchedulePopupService,
    PayrollScheduleComponent,
    PayrollScheduleDetailComponent,
    PayrollScheduleDialogComponent,
    PayrollSchedulePopupComponent,
    PayrollScheduleDeletePopupComponent,
    PayrollScheduleDeleteDialogComponent,
    payrollScheduleRoute,
    payrollSchedulePopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollScheduleRoute,
    ...payrollSchedulePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollScheduleComponent,
        PayrollScheduleDetailComponent,
        PayrollScheduleDialogComponent,
        PayrollScheduleDeleteDialogComponent,
        PayrollSchedulePopupComponent,
        PayrollScheduleDeletePopupComponent,
    ],
    entryComponents: [
        PayrollScheduleComponent,
        PayrollScheduleDialogComponent,
        PayrollSchedulePopupComponent,
        PayrollScheduleDeleteDialogComponent,
        PayrollScheduleDeletePopupComponent,
    ],
    providers: [
        PayrollScheduleService,
        PayrollSchedulePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollScheduleModule {}
