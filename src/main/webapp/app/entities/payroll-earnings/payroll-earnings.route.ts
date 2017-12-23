import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollEarningsComponent } from './payroll-earnings.component';
import { PayrollEarningsDetailComponent } from './payroll-earnings-detail.component';
import { PayrollEarningsPopupComponent } from './payroll-earnings-dialog.component';
import { PayrollEarningsDeletePopupComponent } from './payroll-earnings-delete-dialog.component';

export const payrollEarningsRoute: Routes = [
    {
        path: 'payroll-earnings',
        component: PayrollEarningsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEarnings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-earnings/:id',
        component: PayrollEarningsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEarnings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollEarningsPopupRoute: Routes = [
    {
        path: 'payroll-earnings-new',
        component: PayrollEarningsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEarnings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-earnings/:id/edit',
        component: PayrollEarningsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEarnings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-earnings/:id/delete',
        component: PayrollEarningsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEarnings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
