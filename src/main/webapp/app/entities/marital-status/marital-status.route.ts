import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MaritalStatusComponent } from './marital-status.component';
import { MaritalStatusDetailComponent } from './marital-status-detail.component';
import { MaritalStatusPopupComponent } from './marital-status-dialog.component';
import { MaritalStatusDeletePopupComponent } from './marital-status-delete-dialog.component';

export const maritalStatusRoute: Routes = [
    {
        path: 'marital-status',
        component: MaritalStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MaritalStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'marital-status/:id',
        component: MaritalStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MaritalStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const maritalStatusPopupRoute: Routes = [
    {
        path: 'marital-status-new',
        component: MaritalStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MaritalStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marital-status/:id/edit',
        component: MaritalStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MaritalStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marital-status/:id/delete',
        component: MaritalStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MaritalStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];