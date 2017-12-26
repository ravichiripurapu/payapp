import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayappSharedModule } from '../../shared';
import {
    BankAccountTypeService,
    BankAccountTypePopupService,
    BankAccountTypeComponent,
    BankAccountTypeDetailComponent,
    BankAccountTypeDialogComponent,
    BankAccountTypePopupComponent,
    BankAccountTypeDeletePopupComponent,
    BankAccountTypeDeleteDialogComponent,
    bankAccountTypeRoute,
    bankAccountTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...bankAccountTypeRoute,
    ...bankAccountTypePopupRoute,
];

@NgModule({
    imports: [
        PayappSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BankAccountTypeComponent,
        BankAccountTypeDetailComponent,
        BankAccountTypeDialogComponent,
        BankAccountTypeDeleteDialogComponent,
        BankAccountTypePopupComponent,
        BankAccountTypeDeletePopupComponent,
    ],
    entryComponents: [
        BankAccountTypeComponent,
        BankAccountTypeDialogComponent,
        BankAccountTypePopupComponent,
        BankAccountTypeDeleteDialogComponent,
        BankAccountTypeDeletePopupComponent,
    ],
    providers: [
        BankAccountTypeService,
        BankAccountTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayappBankAccountTypeModule {}
