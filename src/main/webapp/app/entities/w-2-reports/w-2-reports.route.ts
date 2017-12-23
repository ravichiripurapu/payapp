import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { W2ReportsComponent } from './w-2-reports.component';
import { W2ReportsDetailComponent } from './w-2-reports-detail.component';
import { W2ReportsPopupComponent } from './w-2-reports-dialog.component';
import { W2ReportsDeletePopupComponent } from './w-2-reports-delete-dialog.component';

export const w2ReportsRoute: Routes = [
    {
        path: 'w-2-reports',
        component: W2ReportsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'W2Reports'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'w-2-reports/:id',
        component: W2ReportsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'W2Reports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const w2ReportsPopupRoute: Routes = [
    {
        path: 'w-2-reports-new',
        component: W2ReportsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'W2Reports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'w-2-reports/:id/edit',
        component: W2ReportsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'W2Reports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'w-2-reports/:id/delete',
        component: W2ReportsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'W2Reports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
