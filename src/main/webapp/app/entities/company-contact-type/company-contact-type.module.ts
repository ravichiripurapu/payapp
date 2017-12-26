import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyContactTypeService,
    CompanyContactTypePopupService,
    CompanyContactTypeComponent,
    CompanyContactTypeDetailComponent,
    CompanyContactTypeDialogComponent,
    CompanyContactTypePopupComponent,
    CompanyContactTypeDeletePopupComponent,
    CompanyContactTypeDeleteDialogComponent,
    companyContactTypeRoute,
    companyContactTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyContactTypeRoute,
    ...companyContactTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyContactTypeComponent,
        CompanyContactTypeDetailComponent,
        CompanyContactTypeDialogComponent,
        CompanyContactTypeDeleteDialogComponent,
        CompanyContactTypePopupComponent,
        CompanyContactTypeDeletePopupComponent,
    ],
    entryComponents: [
        CompanyContactTypeComponent,
        CompanyContactTypeDialogComponent,
        CompanyContactTypePopupComponent,
        CompanyContactTypeDeleteDialogComponent,
        CompanyContactTypeDeletePopupComponent,
    ],
    providers: [
        CompanyContactTypeService,
        CompanyContactTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyContactTypeModule {}
