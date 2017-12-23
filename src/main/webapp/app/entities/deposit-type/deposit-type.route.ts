import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DepositTypeComponent } from './deposit-type.component';
import { DepositTypeDetailComponent } from './deposit-type-detail.component';
import { DepositTypePopupComponent } from './deposit-type-dialog.component';
import { DepositTypeDeletePopupComponent } from './deposit-type-delete-dialog.component';

export const depositTypeRoute: Routes = [
    {
        path: 'deposit-type',
        component: DepositTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deposit-type/:id',
        component: DepositTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depositTypePopupRoute: Routes = [
    {
        path: 'deposit-type-new',
        component: DepositTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deposit-type/:id/edit',
        component: DepositTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deposit-type/:id/delete',
        component: DepositTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
