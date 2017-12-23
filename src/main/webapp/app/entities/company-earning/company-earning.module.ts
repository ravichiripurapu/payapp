import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyEarningService,
    CompanyEarningPopupService,
    CompanyEarningComponent,
    CompanyEarningDetailComponent,
    CompanyEarningDialogComponent,
    CompanyEarningPopupComponent,
    CompanyEarningDeletePopupComponent,
    CompanyEarningDeleteDialogComponent,
    companyEarningRoute,
    companyEarningPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyEarningRoute,
    ...companyEarningPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyEarningComponent,
        CompanyEarningDetailComponent,
        CompanyEarningDialogComponent,
        CompanyEarningDeleteDialogComponent,
        CompanyEarningPopupComponent,
        CompanyEarningDeletePopupComponent,
    ],
    entryComponents: [
        CompanyEarningComponent,
        CompanyEarningDialogComponent,
        CompanyEarningPopupComponent,
        CompanyEarningDeleteDialogComponent,
        CompanyEarningDeletePopupComponent,
    ],
    providers: [
        CompanyEarningService,
        CompanyEarningPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyEarningModule {}
