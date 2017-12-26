import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    QuarterlyReportsService,
    QuarterlyReportsPopupService,
    QuarterlyReportsComponent,
    QuarterlyReportsDetailComponent,
    QuarterlyReportsDialogComponent,
    QuarterlyReportsPopupComponent,
    QuarterlyReportsDeletePopupComponent,
    QuarterlyReportsDeleteDialogComponent,
    quarterlyReportsRoute,
    quarterlyReportsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...quarterlyReportsRoute,
    ...quarterlyReportsPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        QuarterlyReportsComponent,
        QuarterlyReportsDetailComponent,
        QuarterlyReportsDialogComponent,
        QuarterlyReportsDeleteDialogComponent,
        QuarterlyReportsPopupComponent,
        QuarterlyReportsDeletePopupComponent,
    ],
    entryComponents: [
        QuarterlyReportsComponent,
        QuarterlyReportsDialogComponent,
        QuarterlyReportsPopupComponent,
        QuarterlyReportsDeleteDialogComponent,
        QuarterlyReportsDeletePopupComponent,
    ],
    providers: [
        QuarterlyReportsService,
        QuarterlyReportsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappQuarterlyReportsModule {}
