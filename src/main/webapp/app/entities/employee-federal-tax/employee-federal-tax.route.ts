import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeFederalTaxComponent } from './employee-federal-tax.component';
import { EmployeeFederalTaxDetailComponent } from './employee-federal-tax-detail.component';
import { EmployeeFederalTaxPopupComponent } from './employee-federal-tax-dialog.component';
import { EmployeeFederalTaxDeletePopupComponent } from './employee-federal-tax-delete-dialog.component';

export const employeeFederalTaxRoute: Routes = [
    {
        path: 'employee-federal-tax',
        component: EmployeeFederalTaxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeFederalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-federal-tax/:id',
        component: EmployeeFederalTaxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeFederalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeFederalTaxPopupRoute: Routes = [
    {
        path: 'employee-federal-tax-new',
        component: EmployeeFederalTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeFederalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-federal-tax/:id/edit',
        component: EmployeeFederalTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeFederalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-federal-tax/:id/delete',
        component: EmployeeFederalTaxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeFederalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
