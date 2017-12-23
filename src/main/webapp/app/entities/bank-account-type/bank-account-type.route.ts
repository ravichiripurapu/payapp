import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BankAccountTypeComponent } from './bank-account-type.component';
import { BankAccountTypeDetailComponent } from './bank-account-type-detail.component';
import { BankAccountTypePopupComponent } from './bank-account-type-dialog.component';
import { BankAccountTypeDeletePopupComponent } from './bank-account-type-delete-dialog.component';

export const bankAccountTypeRoute: Routes = [
    {
        path: 'bank-account-type',
        component: BankAccountTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bank-account-type/:id',
        component: BankAccountTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bankAccountTypePopupRoute: Routes = [
    {
        path: 'bank-account-type-new',
        component: BankAccountTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-account-type/:id/edit',
        component: BankAccountTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-account-type/:id/delete',
        component: BankAccountTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
