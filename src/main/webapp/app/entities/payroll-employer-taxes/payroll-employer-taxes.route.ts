import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollEmployerTaxesComponent } from './payroll-employer-taxes.component';
import { PayrollEmployerTaxesDetailComponent } from './payroll-employer-taxes-detail.component';
import { PayrollEmployerTaxesPopupComponent } from './payroll-employer-taxes-dialog.component';
import { PayrollEmployerTaxesDeletePopupComponent } from './payroll-employer-taxes-delete-dialog.component';

export const payrollEmployerTaxesRoute: Routes = [
    {
        path: 'payroll-employer-taxes',
        component: PayrollEmployerTaxesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployerTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-employer-taxes/:id',
        component: PayrollEmployerTaxesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployerTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollEmployerTaxesPopupRoute: Routes = [
    {
        path: 'payroll-employer-taxes-new',
        component: PayrollEmployerTaxesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployerTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-employer-taxes/:id/edit',
        component: PayrollEmployerTaxesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployerTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-employer-taxes/:id/delete',
        component: PayrollEmployerTaxesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollEmployerTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
