import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeTypeComponent } from './employee-type.component';
import { EmployeeTypeDetailComponent } from './employee-type-detail.component';
import { EmployeeTypePopupComponent } from './employee-type-dialog.component';
import { EmployeeTypeDeletePopupComponent } from './employee-type-delete-dialog.component';

export const employeeTypeRoute: Routes = [
    {
        path: 'employee-type',
        component: EmployeeTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-type/:id',
        component: EmployeeTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeTypePopupRoute: Routes = [
    {
        path: 'employee-type-new',
        component: EmployeeTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-type/:id/edit',
        component: EmployeeTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-type/:id/delete',
        component: EmployeeTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
