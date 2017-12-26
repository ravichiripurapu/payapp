import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    EmployeeWorkLocationService,
    EmployeeWorkLocationPopupService,
    EmployeeWorkLocationComponent,
    EmployeeWorkLocationDetailComponent,
    EmployeeWorkLocationDialogComponent,
    EmployeeWorkLocationPopupComponent,
    EmployeeWorkLocationDeletePopupComponent,
    EmployeeWorkLocationDeleteDialogComponent,
    employeeWorkLocationRoute,
    employeeWorkLocationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeWorkLocationRoute,
    ...employeeWorkLocationPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeWorkLocationComponent,
        EmployeeWorkLocationDetailComponent,
        EmployeeWorkLocationDialogComponent,
        EmployeeWorkLocationDeleteDialogComponent,
        EmployeeWorkLocationPopupComponent,
        EmployeeWorkLocationDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeWorkLocationComponent,
        EmployeeWorkLocationDialogComponent,
        EmployeeWorkLocationPopupComponent,
        EmployeeWorkLocationDeleteDialogComponent,
        EmployeeWorkLocationDeletePopupComponent,
    ],
    providers: [
        EmployeeWorkLocationService,
        EmployeeWorkLocationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappEmployeeWorkLocationModule {}
