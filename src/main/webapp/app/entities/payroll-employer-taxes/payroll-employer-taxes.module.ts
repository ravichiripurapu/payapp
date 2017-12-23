import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    PayrollEmployerTaxesService,
    PayrollEmployerTaxesPopupService,
    PayrollEmployerTaxesComponent,
    PayrollEmployerTaxesDetailComponent,
    PayrollEmployerTaxesDialogComponent,
    PayrollEmployerTaxesPopupComponent,
    PayrollEmployerTaxesDeletePopupComponent,
    PayrollEmployerTaxesDeleteDialogComponent,
    payrollEmployerTaxesRoute,
    payrollEmployerTaxesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...payrollEmployerTaxesRoute,
    ...payrollEmployerTaxesPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PayrollEmployerTaxesComponent,
        PayrollEmployerTaxesDetailComponent,
        PayrollEmployerTaxesDialogComponent,
        PayrollEmployerTaxesDeleteDialogComponent,
        PayrollEmployerTaxesPopupComponent,
        PayrollEmployerTaxesDeletePopupComponent,
    ],
    entryComponents: [
        PayrollEmployerTaxesComponent,
        PayrollEmployerTaxesDialogComponent,
        PayrollEmployerTaxesPopupComponent,
        PayrollEmployerTaxesDeleteDialogComponent,
        PayrollEmployerTaxesDeletePopupComponent,
    ],
    providers: [
        PayrollEmployerTaxesService,
        PayrollEmployerTaxesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappPayrollEmployerTaxesModule {}
