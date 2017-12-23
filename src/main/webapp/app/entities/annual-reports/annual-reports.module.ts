import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    AnnualReportsService,
    AnnualReportsPopupService,
    AnnualReportsComponent,
    AnnualReportsDetailComponent,
    AnnualReportsDialogComponent,
    AnnualReportsPopupComponent,
    AnnualReportsDeletePopupComponent,
    AnnualReportsDeleteDialogComponent,
    annualReportsRoute,
    annualReportsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...annualReportsRoute,
    ...annualReportsPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AnnualReportsComponent,
        AnnualReportsDetailComponent,
        AnnualReportsDialogComponent,
        AnnualReportsDeleteDialogComponent,
        AnnualReportsPopupComponent,
        AnnualReportsDeletePopupComponent,
    ],
    entryComponents: [
        AnnualReportsComponent,
        AnnualReportsDialogComponent,
        AnnualReportsPopupComponent,
        AnnualReportsDeleteDialogComponent,
        AnnualReportsDeletePopupComponent,
    ],
    providers: [
        AnnualReportsService,
        AnnualReportsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappAnnualReportsModule {}
