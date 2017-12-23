import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollEmployeeTaxesComponent } from './payroll-employee-taxes.component';
import { PayrollEmployeeTaxesDetailComponent } from './payroll-employee-taxes-detail.component';
import { PayrollEmployeeTaxesPopupComponent } from './payroll-employee-taxes-dialog.component';
import { PayrollEmployeeTaxesDeletePopupComponent } from './payroll-employee-taxes-delete-dialog.component';

export const payrollEmployeeTaxesRoute: Routes = [
    {
        path: 'payroll-employee-taxes',
        component: PayrollEmployeeTaxesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployeeTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-employee-taxes/:id',
        component: PayrollEmployeeTaxesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployeeTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollEmployeeTaxesPopupRoute: Routes = [
    {
        path: 'payroll-employee-taxes-new',
        component: PayrollEmployeeTaxesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployeeTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-employee-taxes/:id/edit',
        component: PayrollEmployeeTaxesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployeeTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-employee-taxes/:id/delete',
        component: PayrollEmployeeTaxesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployeeTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
