import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeBankComponent } from './employee-bank.component';
import { EmployeeBankDetailComponent } from './employee-bank-detail.component';
import { EmployeeBankPopupComponent } from './employee-bank-dialog.component';
import { EmployeeBankDeletePopupComponent } from './employee-bank-delete-dialog.component';

export const employeeBankRoute: Routes = [
    {
        path: 'employee-bank',
        component: EmployeeBankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeBanks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-bank/:id',
        component: EmployeeBankDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeBanks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeBankPopupRoute: Routes = [
    {
        path: 'employee-bank-new',
        component: EmployeeBankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeBanks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-bank/:id/edit',
        component: EmployeeBankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeBanks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-bank/:id/delete',
        component: EmployeeBankDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeBanks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
