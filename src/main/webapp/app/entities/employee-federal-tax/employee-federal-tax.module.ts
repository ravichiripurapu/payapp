import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeFederalTaxService,
    EmployeeFederalTaxPopupService,
    EmployeeFederalTaxComponent,
    EmployeeFederalTaxDetailComponent,
    EmployeeFederalTaxDialogComponent,
    EmployeeFederalTaxPopupComponent,
    EmployeeFederalTaxDeletePopupComponent,
    EmployeeFederalTaxDeleteDialogComponent,
    employeeFederalTaxRoute,
    employeeFederalTaxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeFederalTaxRoute,
    ...employeeFederalTaxPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeFederalTaxComponent,
        EmployeeFederalTaxDetailComponent,
        EmployeeFederalTaxDialogComponent,
        EmployeeFederalTaxDeleteDialogComponent,
        EmployeeFederalTaxPopupComponent,
        EmployeeFederalTaxDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeFederalTaxComponent,
        EmployeeFederalTaxDialogComponent,
        EmployeeFederalTaxPopupComponent,
        EmployeeFederalTaxDeleteDialogComponent,
        EmployeeFederalTaxDeletePopupComponent,
    ],
    providers: [
        EmployeeFederalTaxService,
        EmployeeFederalTaxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeFederalTaxModule {}
