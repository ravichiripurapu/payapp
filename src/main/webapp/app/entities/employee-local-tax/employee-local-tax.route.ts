import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeLocalTaxComponent } from './employee-local-tax.component';
import { EmployeeLocalTaxDetailComponent } from './employee-local-tax-detail.component';
import { EmployeeLocalTaxPopupComponent } from './employee-local-tax-dialog.component';
import { EmployeeLocalTaxDeletePopupComponent } from './employee-local-tax-delete-dialog.component';

export const employeeLocalTaxRoute: Routes = [
    {
        path: 'employee-local-tax',
        component: EmployeeLocalTaxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeLocalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-local-tax/:id',
        component: EmployeeLocalTaxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeLocalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeLocalTaxPopupRoute: Routes = [
    {
        path: 'employee-local-tax-new',
        component: EmployeeLocalTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeLocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-local-tax/:id/edit',
        component: EmployeeLocalTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeLocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-local-tax/:id/delete',
        component: EmployeeLocalTaxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeLocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
