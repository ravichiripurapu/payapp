import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollEmployeeService,
    PayrollEmployeePopupService,
    PayrollEmployeeComponent,
    PayrollEmployeeDetailComponent,
    PayrollEmployeeDialogComponent,
    PayrollEmployeePopupComponent,
    PayrollEmployeeDeletePopupComponent,
    PayrollEmployeeDeleteDialogComponent,
    payrollEmployeeRoute,
    payrollEmployeePopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollEmployeeRoute,
    ...payrollEmployeePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollEmployeeComponent,
        PayrollEmployeeDetailComponent,
        PayrollEmployeeDialogComponent,
        PayrollEmployeeDeleteDialogComponent,
        PayrollEmployeePopupComponent,
        PayrollEmployeeDeletePopupComponent,
    ],
    entryComponents: [
        PayrollEmployeeComponent,
        PayrollEmployeeDialogComponent,
        PayrollEmployeePopupComponent,
        PayrollEmployeeDeleteDialogComponent,
        PayrollEmployeeDeletePopupComponent,
    ],
    providers: [
        PayrollEmployeeService,
        PayrollEmployeePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollEmployeeModule {}
