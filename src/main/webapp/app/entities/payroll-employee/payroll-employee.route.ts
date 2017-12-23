import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollEmployeeComponent } from './payroll-employee.component';
import { PayrollEmployeeDetailComponent } from './payroll-employee-detail.component';
import { PayrollEmployeePopupComponent } from './payroll-employee-dialog.component';
import { PayrollEmployeeDeletePopupComponent } from './payroll-employee-delete-dialog.component';

export const payrollEmployeeRoute: Routes = [
    {
        path: 'payroll-employee',
        component: PayrollEmployeeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployees'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-employee/:id',
        component: PayrollEmployeeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollEmployeePopupRoute: Routes = [
    {
        path: 'payroll-employee-new',
        component: PayrollEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-employee/:id/edit',
        component: PayrollEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-employee/:id/delete',
        component: PayrollEmployeeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
