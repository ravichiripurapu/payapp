import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyDeductionComponent } from './company-deduction.component';
import { CompanyDeductionDetailComponent } from './company-deduction-detail.component';
import { CompanyDeductionPopupComponent } from './company-deduction-dialog.component';
import { CompanyDeductionDeletePopupComponent } from './company-deduction-delete-dialog.component';

export const companyDeductionRoute: Routes = [
    {
        path: 'company-deduction',
        component: CompanyDeductionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDeductions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-deduction/:id',
        component: CompanyDeductionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDeductions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyDeductionPopupRoute: Routes = [
    {
        path: 'company-deduction-new',
        component: CompanyDeductionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDeductions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-deduction/:id/edit',
        component: CompanyDeductionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDeductions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-deduction/:id/delete',
        component: CompanyDeductionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyDeductions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
