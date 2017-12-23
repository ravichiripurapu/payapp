import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyDepartmentComponent } from './company-department.component';
import { CompanyDepartmentDetailComponent } from './company-department-detail.component';
import { CompanyDepartmentPopupComponent } from './company-department-dialog.component';
import { CompanyDepartmentDeletePopupComponent } from './company-department-delete-dialog.component';

export const companyDepartmentRoute: Routes = [
    {
        path: 'company-department',
        component: CompanyDepartmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDepartments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-department/:id',
        component: CompanyDepartmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDepartments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyDepartmentPopupRoute: Routes = [
    {
        path: 'company-department-new',
        component: CompanyDepartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDepartments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-department/:id/edit',
        component: CompanyDepartmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDepartments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-department/:id/delete',
        component: CompanyDepartmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDepartments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
