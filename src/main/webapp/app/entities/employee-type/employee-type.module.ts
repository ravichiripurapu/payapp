import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeTypeService,
    EmployeeTypePopupService,
    EmployeeTypeComponent,
    EmployeeTypeDetailComponent,
    EmployeeTypeDialogComponent,
    EmployeeTypePopupComponent,
    EmployeeTypeDeletePopupComponent,
    EmployeeTypeDeleteDialogComponent,
    employeeTypeRoute,
    employeeTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeTypeRoute,
    ...employeeTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeTypeComponent,
        EmployeeTypeDetailComponent,
        EmployeeTypeDialogComponent,
        EmployeeTypeDeleteDialogComponent,
        EmployeeTypePopupComponent,
        EmployeeTypeDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeTypeComponent,
        EmployeeTypeDialogComponent,
        EmployeeTypePopupComponent,
        EmployeeTypeDeleteDialogComponent,
        EmployeeTypeDeletePopupComponent,
    ],
    providers: [
        EmployeeTypeService,
        EmployeeTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeTypeModule {}
