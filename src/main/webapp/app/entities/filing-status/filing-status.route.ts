import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FilingStatusComponent } from './filing-status.component';
import { FilingStatusDetailComponent } from './filing-status-detail.component';
import { FilingStatusPopupComponent } from './filing-status-dialog.component';
import { FilingStatusDeletePopupComponent } from './filing-status-delete-dialog.component';

export const filingStatusRoute: Routes = [
    {
        path: 'filing-status',
        component: FilingStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilingStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'filing-status/:id',
        component: FilingStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilingStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filingStatusPopupRoute: Routes = [
    {
        path: 'filing-status-new',
        component: FilingStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilingStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filing-status/:id/edit',
        component: FilingStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilingStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'filing-status/:id/delete',
        component: FilingStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FilingStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
