import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AnnualReportsComponent } from './annual-reports.component';
import { AnnualReportsDetailComponent } from './annual-reports-detail.component';
import { AnnualReportsPopupComponent } from './annual-reports-dialog.component';
import { AnnualReportsDeletePopupComponent } from './annual-reports-delete-dialog.component';

export const annualReportsRoute: Routes = [
    {
        path: 'annual-reports',
        component: AnnualReportsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnnualReports'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'annual-reports/:id',
        component: AnnualReportsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnnualReports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const annualReportsPopupRoute: Routes = [
    {
        path: 'annual-reports-new',
        component: AnnualReportsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnnualReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'annual-reports/:id/edit',
        component: AnnualReportsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnnualReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'annual-reports/:id/delete',
        component: AnnualReportsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AnnualReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
