import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    LocalTaxesService,
    LocalTaxesPopupService,
    LocalTaxesComponent,
    LocalTaxesDetailComponent,
    LocalTaxesDialogComponent,
    LocalTaxesPopupComponent,
    LocalTaxesDeletePopupComponent,
    LocalTaxesDeleteDialogComponent,
    localTaxesRoute,
    localTaxesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...localTaxesRoute,
    ...localTaxesPopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LocalTaxesComponent,
        LocalTaxesDetailComponent,
        LocalTaxesDialogComponent,
        LocalTaxesDeleteDialogComponent,
        LocalTaxesPopupComponent,
        LocalTaxesDeletePopupComponent,
    ],
    entryComponents: [
        LocalTaxesComponent,
        LocalTaxesDialogComponent,
        LocalTaxesPopupComponent,
        LocalTaxesDeleteDialogComponent,
        LocalTaxesDeletePopupComponent,
    ],
    providers: [
        LocalTaxesService,
        LocalTaxesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappLocalTaxesModule {}
