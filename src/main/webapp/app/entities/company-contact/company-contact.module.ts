import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyContactService,
    CompanyContactPopupService,
    CompanyContactComponent,
    CompanyContactDetailComponent,
    CompanyContactDialogComponent,
    CompanyContactPopupComponent,
    CompanyContactDeletePopupComponent,
    CompanyContactDeleteDialogComponent,
    companyContactRoute,
    companyContactPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyContactRoute,
    ...companyContactPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyContactComponent,
        CompanyContactDetailComponent,
        CompanyContactDialogComponent,
        CompanyContactDeleteDialogComponent,
        CompanyContactPopupComponent,
        CompanyContactDeletePopupComponent,
    ],
    entryComponents: [
        CompanyContactComponent,
        CompanyContactDialogComponent,
        CompanyContactPopupComponent,
        CompanyContactDeleteDialogComponent,
        CompanyContactDeletePopupComponent,
    ],
    providers: [
        CompanyContactService,
        CompanyContactPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyContactModule {}
