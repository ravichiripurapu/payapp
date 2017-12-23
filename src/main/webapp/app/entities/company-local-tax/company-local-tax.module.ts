import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyLocalTaxService,
    CompanyLocalTaxPopupService,
    CompanyLocalTaxComponent,
    CompanyLocalTaxDetailComponent,
    CompanyLocalTaxDialogComponent,
    CompanyLocalTaxPopupComponent,
    CompanyLocalTaxDeletePopupComponent,
    CompanyLocalTaxDeleteDialogComponent,
    companyLocalTaxRoute,
    companyLocalTaxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyLocalTaxRoute,
    ...companyLocalTaxPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyLocalTaxComponent,
        CompanyLocalTaxDetailComponent,
        CompanyLocalTaxDialogComponent,
        CompanyLocalTaxDeleteDialogComponent,
        CompanyLocalTaxPopupComponent,
        CompanyLocalTaxDeletePopupComponent,
    ],
    entryComponents: [
        CompanyLocalTaxComponent,
        CompanyLocalTaxDialogComponent,
        CompanyLocalTaxPopupComponent,
        CompanyLocalTaxDeleteDialogComponent,
        CompanyLocalTaxDeletePopupComponent,
    ],
    providers: [
        CompanyLocalTaxService,
        CompanyLocalTaxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyLocalTaxModule {}
