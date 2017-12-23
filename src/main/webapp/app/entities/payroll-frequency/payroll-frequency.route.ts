import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollFrequencyComponent } from './payroll-frequency.component';
import { PayrollFrequencyDetailComponent } from './payroll-frequency-detail.component';
import { PayrollFrequencyPopupComponent } from './payroll-frequency-dialog.component';
import { PayrollFrequencyDeletePopupComponent } from './payroll-frequency-delete-dialog.component';

export const payrollFrequencyRoute: Routes = [
    {
        path: 'payroll-frequency',
        component: PayrollFrequencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollFrequencies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-frequency/:id',
        component: PayrollFrequencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollFrequencies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollFrequencyPopupRoute: Routes = [
    {
        path: 'payroll-frequency-new',
        component: PayrollFrequencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollFrequencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-frequency/:id/edit',
        component: PayrollFrequencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollFrequencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-frequency/:id/delete',
        component: PayrollFrequencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollFrequencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
