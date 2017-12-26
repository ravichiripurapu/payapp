import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    FutaExemptReasonCodeService,
    FutaExemptReasonCodePopupService,
    FutaExemptReasonCodeComponent,
    FutaExemptReasonCodeDetailComponent,
    FutaExemptReasonCodeDialogComponent,
    FutaExemptReasonCodePopupComponent,
    FutaExemptReasonCodeDeletePopupComponent,
    FutaExemptReasonCodeDeleteDialogComponent,
    futaExemptReasonCodeRoute,
    futaExemptReasonCodePopupRoute,
} from './';

const ENTITY_STATES = [
    ...futaExemptReasonCodeRoute,
    ...futaExemptReasonCodePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FutaExemptReasonCodeComponent,
        FutaExemptReasonCodeDetailComponent,
        FutaExemptReasonCodeDialogComponent,
        FutaExemptReasonCodeDeleteDialogComponent,
        FutaExemptReasonCodePopupComponent,
        FutaExemptReasonCodeDeletePopupComponent,
    ],
    entryComponents: [
        FutaExemptReasonCodeComponent,
        FutaExemptReasonCodeDialogComponent,
        FutaExemptReasonCodePopupComponent,
        FutaExemptReasonCodeDeleteDialogComponent,
        FutaExemptReasonCodeDeletePopupComponent,
    ],
    providers: [
        FutaExemptReasonCodeService,
        FutaExemptReasonCodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappFutaExemptReasonCodeModule {}
