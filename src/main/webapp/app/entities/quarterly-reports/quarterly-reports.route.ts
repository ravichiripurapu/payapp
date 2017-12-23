import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { QuarterlyReportsComponent } from './quarterly-reports.component';
import { QuarterlyReportsDetailComponent } from './quarterly-reports-detail.component';
import { QuarterlyReportsPopupComponent } from './quarterly-reports-dialog.component';
import { QuarterlyReportsDeletePopupComponent } from './quarterly-reports-delete-dialog.component';

export const quarterlyReportsRoute: Routes = [
    {
        path: 'quarterly-reports',
        component: QuarterlyReportsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuarterlyReports'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'quarterly-reports/:id',
        component: QuarterlyReportsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuarterlyReports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quarterlyReportsPopupRoute: Routes = [
    {
        path: 'quarterly-reports-new',
        component: QuarterlyReportsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuarterlyReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quarterly-reports/:id/edit',
        component: QuarterlyReportsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuarterlyReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quarterly-reports/:id/delete',
        component: QuarterlyReportsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuarterlyReports'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
