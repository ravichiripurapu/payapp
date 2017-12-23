import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeStateTaxComponent } from './employee-state-tax.component';
import { EmployeeStateTaxDetailComponent } from './employee-state-tax-detail.component';
import { EmployeeStateTaxPopupComponent } from './employee-state-tax-dialog.component';
import { EmployeeStateTaxDeletePopupComponent } from './employee-state-tax-delete-dialog.component';

export const employeeStateTaxRoute: Routes = [
    {
        path: 'employee-state-tax',
        component: EmployeeStateTaxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeStateTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-state-tax/:id',
        component: EmployeeStateTaxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeStateTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeStateTaxPopupRoute: Routes = [
    {
        path: 'employee-state-tax-new',
        component: EmployeeStateTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeStateTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-state-tax/:id/edit',
        component: EmployeeStateTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeStateTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-state-tax/:id/delete',
        component: EmployeeStateTaxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeStateTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
