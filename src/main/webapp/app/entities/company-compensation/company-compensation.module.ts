import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyCompensationService,
    CompanyCompensationPopupService,
    CompanyCompensationComponent,
    CompanyCompensationDetailComponent,
    CompanyCompensationDialogComponent,
    CompanyCompensationPopupComponent,
    CompanyCompensationDeletePopupComponent,
    CompanyCompensationDeleteDialogComponent,
    companyCompensationRoute,
    companyCompensationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyCompensationRoute,
    ...companyCompensationPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyCompensationComponent,
        CompanyCompensationDetailComponent,
        CompanyCompensationDialogComponent,
        CompanyCompensationDeleteDialogComponent,
        CompanyCompensationPopupComponent,
        CompanyCompensationDeletePopupComponent,
    ],
    entryComponents: [
        CompanyCompensationComponent,
        CompanyCompensationDialogComponent,
        CompanyCompensationPopupComponent,
        CompanyCompensationDeleteDialogComponent,
        CompanyCompensationDeletePopupComponent,
    ],
    providers: [
        CompanyCompensationService,
        CompanyCompensationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyCompensationModule {}
