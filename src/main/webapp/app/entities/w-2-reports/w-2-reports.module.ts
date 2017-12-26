import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    W2ReportsService,
    W2ReportsPopupService,
    W2ReportsComponent,
    W2ReportsDetailComponent,
    W2ReportsDialogComponent,
    W2ReportsPopupComponent,
    W2ReportsDeletePopupComponent,
    W2ReportsDeleteDialogComponent,
    w2ReportsRoute,
    w2ReportsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...w2ReportsRoute,
    ...w2ReportsPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        W2ReportsComponent,
        W2ReportsDetailComponent,
        W2ReportsDialogComponent,
        W2ReportsDeleteDialogComponent,
        W2ReportsPopupComponent,
        W2ReportsDeletePopupComponent,
    ],
    entryComponents: [
        W2ReportsComponent,
        W2ReportsDialogComponent,
        W2ReportsPopupComponent,
        W2ReportsDeleteDialogComponent,
        W2ReportsDeletePopupComponent,
    ],
    providers: [
        W2ReportsService,
        W2ReportsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappW2ReportsModule {}
