import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollEmployeeTaxesService,
    PayrollEmployeeTaxesPopupService,
    PayrollEmployeeTaxesComponent,
    PayrollEmployeeTaxesDetailComponent,
    PayrollEmployeeTaxesDialogComponent,
    PayrollEmployeeTaxesPopupComponent,
    PayrollEmployeeTaxesDeletePopupComponent,
    PayrollEmployeeTaxesDeleteDialogComponent,
    payrollEmployeeTaxesRoute,
    payrollEmployeeTaxesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollEmployeeTaxesRoute,
    ...payrollEmployeeTaxesPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollEmployeeTaxesComponent,
        PayrollEmployeeTaxesDetailComponent,
        PayrollEmployeeTaxesDialogComponent,
        PayrollEmployeeTaxesDeleteDialogComponent,
        PayrollEmployeeTaxesPopupComponent,
        PayrollEmployeeTaxesDeletePopupComponent,
    ],
    entryComponents: [
        PayrollEmployeeTaxesComponent,
        PayrollEmployeeTaxesDialogComponent,
        PayrollEmployeeTaxesPopupComponent,
        PayrollEmployeeTaxesDeleteDialogComponent,
        PayrollEmployeeTaxesDeletePopupComponent,
    ],
    providers: [
        PayrollEmployeeTaxesService,
        PayrollEmployeeTaxesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollEmployeeTaxesModule {}
