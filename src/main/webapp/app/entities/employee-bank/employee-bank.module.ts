import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeBankService,
    EmployeeBankPopupService,
    EmployeeBankComponent,
    EmployeeBankDetailComponent,
    EmployeeBankDialogComponent,
    EmployeeBankPopupComponent,
    EmployeeBankDeletePopupComponent,
    EmployeeBankDeleteDialogComponent,
    employeeBankRoute,
    employeeBankPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeBankRoute,
    ...employeeBankPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeBankComponent,
        EmployeeBankDetailComponent,
        EmployeeBankDialogComponent,
        EmployeeBankDeleteDialogComponent,
        EmployeeBankPopupComponent,
        EmployeeBankDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeBankComponent,
        EmployeeBankDialogComponent,
        EmployeeBankPopupComponent,
        EmployeeBankDeleteDialogComponent,
        EmployeeBankDeletePopupComponent,
    ],
    providers: [
        EmployeeBankService,
        EmployeeBankPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeBankModule {}
