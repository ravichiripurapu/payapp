import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    GenderService,
    GenderPopupService,
    GenderComponent,
    GenderDetailComponent,
    GenderDialogComponent,
    GenderPopupComponent,
    GenderDeletePopupComponent,
    GenderDeleteDialogComponent,
    genderRoute,
    genderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...genderRoute,
    ...genderPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GenderComponent,
        GenderDetailComponent,
        GenderDialogComponent,
        GenderDeleteDialogComponent,
        GenderPopupComponent,
        GenderDeletePopupComponent,
    ],
    entryComponents: [
        GenderComponent,
        GenderDialogComponent,
        GenderPopupComponent,
        GenderDeleteDialogComponent,
        GenderDeletePopupComponent,
    ],
    providers: [
        GenderService,
        GenderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappGenderModule {}