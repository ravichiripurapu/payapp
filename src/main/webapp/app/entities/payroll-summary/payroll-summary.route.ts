import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollSummaryComponent } from './payroll-summary.component';
import { PayrollSummaryDetailComponent } from './payroll-summary-detail.component';
import { PayrollSummaryPopupComponent } from './payroll-summary-dialog.component';
import { PayrollSummaryDeletePopupComponent } from './payroll-summary-delete-dialog.component';

export const payrollSummaryRoute: Routes = [
    {
        path: 'payroll-summary',
        component: PayrollSummaryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSummaries'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-summary/:id',
        component: PayrollSummaryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSummaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollSummaryPopupRoute: Routes = [
    {
        path: 'payroll-summary-new',
        component: PayrollSummaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-summary/:id/edit',
        component: PayrollSummaryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-summary/:id/delete',
        component: PayrollSummaryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
