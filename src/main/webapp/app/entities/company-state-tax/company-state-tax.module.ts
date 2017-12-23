import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyStateTaxService,
    CompanyStateTaxPopupService,
    CompanyStateTaxComponent,
    CompanyStateTaxDetailComponent,
    CompanyStateTaxDialogComponent,
    CompanyStateTaxPopupComponent,
    CompanyStateTaxDeletePopupComponent,
    CompanyStateTaxDeleteDialogComponent,
    companyStateTaxRoute,
    companyStateTaxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyStateTaxRoute,
    ...companyStateTaxPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyStateTaxComponent,
        CompanyStateTaxDetailComponent,
        CompanyStateTaxDialogComponent,
        CompanyStateTaxDeleteDialogComponent,
        CompanyStateTaxPopupComponent,
        CompanyStateTaxDeletePopupComponent,
    ],
    entryComponents: [
        CompanyStateTaxComponent,
        CompanyStateTaxDialogComponent,
        CompanyStateTaxPopupComponent,
        CompanyStateTaxDeleteDialogComponent,
        CompanyStateTaxDeletePopupComponent,
    ],
    providers: [
        CompanyStateTaxService,
        CompanyStateTaxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyStateTaxModule {}
