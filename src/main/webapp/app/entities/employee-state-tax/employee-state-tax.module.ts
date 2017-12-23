import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeStateTaxService,
    EmployeeStateTaxPopupService,
    EmployeeStateTaxComponent,
    EmployeeStateTaxDetailComponent,
    EmployeeStateTaxDialogComponent,
    EmployeeStateTaxPopupComponent,
    EmployeeStateTaxDeletePopupComponent,
    EmployeeStateTaxDeleteDialogComponent,
    employeeStateTaxRoute,
    employeeStateTaxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeStateTaxRoute,
    ...employeeStateTaxPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeStateTaxComponent,
        EmployeeStateTaxDetailComponent,
        EmployeeStateTaxDialogComponent,
        EmployeeStateTaxDeleteDialogComponent,
        EmployeeStateTaxPopupComponent,
        EmployeeStateTaxDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeStateTaxComponent,
        EmployeeStateTaxDialogComponent,
        EmployeeStateTaxPopupComponent,
        EmployeeStateTaxDeleteDialogComponent,
        EmployeeStateTaxDeletePopupComponent,
    ],
    providers: [
        EmployeeStateTaxService,
        EmployeeStateTaxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeStateTaxModule {}
