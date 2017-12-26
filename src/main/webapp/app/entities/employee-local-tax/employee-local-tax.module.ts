import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeLocalTaxService,
    EmployeeLocalTaxPopupService,
    EmployeeLocalTaxComponent,
    EmployeeLocalTaxDetailComponent,
    EmployeeLocalTaxDialogComponent,
    EmployeeLocalTaxPopupComponent,
    EmployeeLocalTaxDeletePopupComponent,
    EmployeeLocalTaxDeleteDialogComponent,
    employeeLocalTaxRoute,
    employeeLocalTaxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeLocalTaxRoute,
    ...employeeLocalTaxPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeLocalTaxComponent,
        EmployeeLocalTaxDetailComponent,
        EmployeeLocalTaxDialogComponent,
        EmployeeLocalTaxDeleteDialogComponent,
        EmployeeLocalTaxPopupComponent,
        EmployeeLocalTaxDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeLocalTaxComponent,
        EmployeeLocalTaxDialogComponent,
        EmployeeLocalTaxPopupComponent,
        EmployeeLocalTaxDeleteDialogComponent,
        EmployeeLocalTaxDeletePopupComponent,
    ],
    providers: [
        EmployeeLocalTaxService,
        EmployeeLocalTaxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeLocalTaxModule {}
