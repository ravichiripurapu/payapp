import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeResidenceLocationService,
    EmployeeResidenceLocationPopupService,
    EmployeeResidenceLocationComponent,
    EmployeeResidenceLocationDetailComponent,
    EmployeeResidenceLocationDialogComponent,
    EmployeeResidenceLocationPopupComponent,
    EmployeeResidenceLocationDeletePopupComponent,
    EmployeeResidenceLocationDeleteDialogComponent,
    employeeResidenceLocationRoute,
    employeeResidenceLocationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeResidenceLocationRoute,
    ...employeeResidenceLocationPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeResidenceLocationComponent,
        EmployeeResidenceLocationDetailComponent,
        EmployeeResidenceLocationDialogComponent,
        EmployeeResidenceLocationDeleteDialogComponent,
        EmployeeResidenceLocationPopupComponent,
        EmployeeResidenceLocationDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeResidenceLocationComponent,
        EmployeeResidenceLocationDialogComponent,
        EmployeeResidenceLocationPopupComponent,
        EmployeeResidenceLocationDeleteDialogComponent,
        EmployeeResidenceLocationDeletePopupComponent,
    ],
    providers: [
        EmployeeResidenceLocationService,
        EmployeeResidenceLocationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeResidenceLocationModule {}
