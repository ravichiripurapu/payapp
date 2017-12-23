import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    CompanyLocationService,
    CompanyLocationPopupService,
    CompanyLocationComponent,
    CompanyLocationDetailComponent,
    CompanyLocationDialogComponent,
    CompanyLocationPopupComponent,
    CompanyLocationDeletePopupComponent,
    CompanyLocationDeleteDialogComponent,
    companyLocationRoute,
    companyLocationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyLocationRoute,
    ...companyLocationPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyLocationComponent,
        CompanyLocationDetailComponent,
        CompanyLocationDialogComponent,
        CompanyLocationDeleteDialogComponent,
        CompanyLocationPopupComponent,
        CompanyLocationDeletePopupComponent,
    ],
    entryComponents: [
        CompanyLocationComponent,
        CompanyLocationDialogComponent,
        CompanyLocationPopupComponent,
        CompanyLocationDeleteDialogComponent,
        CompanyLocationDeletePopupComponent,
    ],
    providers: [
        CompanyLocationService,
        CompanyLocationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappCompanyLocationModule {}
