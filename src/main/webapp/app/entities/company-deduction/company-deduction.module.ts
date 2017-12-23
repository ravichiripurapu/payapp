import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyDeductionService,
    CompanyDeductionPopupService,
    CompanyDeductionComponent,
    CompanyDeductionDetailComponent,
    CompanyDeductionDialogComponent,
    CompanyDeductionPopupComponent,
    CompanyDeductionDeletePopupComponent,
    CompanyDeductionDeleteDialogComponent,
    companyDeductionRoute,
    companyDeductionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyDeductionRoute,
    ...companyDeductionPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyDeductionComponent,
        CompanyDeductionDetailComponent,
        CompanyDeductionDialogComponent,
        CompanyDeductionDeleteDialogComponent,
        CompanyDeductionPopupComponent,
        CompanyDeductionDeletePopupComponent,
    ],
    entryComponents: [
        CompanyDeductionComponent,
        CompanyDeductionDialogComponent,
        CompanyDeductionPopupComponent,
        CompanyDeductionDeleteDialogComponent,
        CompanyDeductionDeletePopupComponent,
    ],
    providers: [
        CompanyDeductionService,
        CompanyDeductionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyDeductionModule {}
