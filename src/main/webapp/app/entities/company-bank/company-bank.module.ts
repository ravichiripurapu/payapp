import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyBankService,
    CompanyBankPopupService,
    CompanyBankComponent,
    CompanyBankDetailComponent,
    CompanyBankDialogComponent,
    CompanyBankPopupComponent,
    CompanyBankDeletePopupComponent,
    CompanyBankDeleteDialogComponent,
    companyBankRoute,
    companyBankPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyBankRoute,
    ...companyBankPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyBankComponent,
        CompanyBankDetailComponent,
        CompanyBankDialogComponent,
        CompanyBankDeleteDialogComponent,
        CompanyBankPopupComponent,
        CompanyBankDeletePopupComponent,
    ],
    entryComponents: [
        CompanyBankComponent,
        CompanyBankDialogComponent,
        CompanyBankPopupComponent,
        CompanyBankDeleteDialogComponent,
        CompanyBankDeletePopupComponent,
    ],
    providers: [
        CompanyBankService,
        CompanyBankPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyBankModule {}
