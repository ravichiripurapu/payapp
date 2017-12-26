import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    FilingStatusService,
    FilingStatusPopupService,
    FilingStatusComponent,
    FilingStatusDetailComponent,
    FilingStatusDialogComponent,
    FilingStatusPopupComponent,
    FilingStatusDeletePopupComponent,
    FilingStatusDeleteDialogComponent,
    filingStatusRoute,
    filingStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...filingStatusRoute,
    ...filingStatusPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FilingStatusComponent,
        FilingStatusDetailComponent,
        FilingStatusDialogComponent,
        FilingStatusDeleteDialogComponent,
        FilingStatusPopupComponent,
        FilingStatusDeletePopupComponent,
    ],
    entryComponents: [
        FilingStatusComponent,
        FilingStatusDialogComponent,
        FilingStatusPopupComponent,
        FilingStatusDeleteDialogComponent,
        FilingStatusDeletePopupComponent,
    ],
    providers: [
        FilingStatusService,
        FilingStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappFilingStatusModule {}
