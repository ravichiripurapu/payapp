import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyEarningComponent } from './company-earning.component';
import { CompanyEarningDetailComponent } from './company-earning-detail.component';
import { CompanyEarningPopupComponent } from './company-earning-dialog.component';
import { CompanyEarningDeletePopupComponent } from './company-earning-delete-dialog.component';

export const companyEarningRoute: Routes = [
    {
        path: 'company-earning',
        component: CompanyEarningComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEarnings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-earning/:id',
        component: CompanyEarningDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEarnings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyEarningPopupRoute: Routes = [
    {
        path: 'company-earning-new',
        component: CompanyEarningPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEarnings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-earning/:id/edit',
        component: CompanyEarningPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEarnings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-earning/:id/delete',
        component: CompanyEarningDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEarnings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
