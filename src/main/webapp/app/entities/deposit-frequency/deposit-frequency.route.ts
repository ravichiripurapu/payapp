import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DepositFrequencyComponent } from './deposit-frequency.component';
import { DepositFrequencyDetailComponent } from './deposit-frequency-detail.component';
import { DepositFrequencyPopupComponent } from './deposit-frequency-dialog.component';
import { DepositFrequencyDeletePopupComponent } from './deposit-frequency-delete-dialog.component';

export const depositFrequencyRoute: Routes = [
    {
        path: 'deposit-frequency',
        component: DepositFrequencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositFrequencies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deposit-frequency/:id',
        component: DepositFrequencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositFrequencies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depositFrequencyPopupRoute: Routes = [
    {
        path: 'deposit-frequency-new',
        component: DepositFrequencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositFrequencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deposit-frequency/:id/edit',
        component: DepositFrequencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositFrequencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deposit-frequency/:id/delete',
        component: DepositFrequencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepositFrequencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
