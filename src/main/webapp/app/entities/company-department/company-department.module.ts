import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyDepartmentService,
    CompanyDepartmentPopupService,
    CompanyDepartmentComponent,
    CompanyDepartmentDetailComponent,
    CompanyDepartmentDialogComponent,
    CompanyDepartmentPopupComponent,
    CompanyDepartmentDeletePopupComponent,
    CompanyDepartmentDeleteDialogComponent,
    companyDepartmentRoute,
    companyDepartmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyDepartmentRoute,
    ...companyDepartmentPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyDepartmentComponent,
        CompanyDepartmentDetailComponent,
        CompanyDepartmentDialogComponent,
        CompanyDepartmentDeleteDialogComponent,
        CompanyDepartmentPopupComponent,
        CompanyDepartmentDeletePopupComponent,
    ],
    entryComponents: [
        CompanyDepartmentComponent,
        CompanyDepartmentDialogComponent,
        CompanyDepartmentPopupComponent,
        CompanyDepartmentDeleteDialogComponent,
        CompanyDepartmentDeletePopupComponent,
    ],
    providers: [
        CompanyDepartmentService,
        CompanyDepartmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyDepartmentModule {}
