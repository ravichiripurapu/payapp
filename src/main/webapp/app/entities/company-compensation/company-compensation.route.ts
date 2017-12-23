import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyCompensationComponent } from './company-compensation.component';
import { CompanyCompensationDetailComponent } from './company-compensation-detail.component';
import { CompanyCompensationPopupComponent } from './company-compensation-dialog.component';
import { CompanyCompensationDeletePopupComponent } from './company-compensation-delete-dialog.component';

export const companyCompensationRoute: Routes = [
    {
        path: 'company-compensation',
        component: CompanyCompensationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyCompensations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-compensation/:id',
        component: CompanyCompensationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyCompensations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyCompensationPopupRoute: Routes = [
    {
        path: 'company-compensation-new',
        component: CompanyCompensationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyCompensations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-compensation/:id/edit',
        component: CompanyCompensationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyCompensations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-compensation/:id/delete',
        component: CompanyCompensationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyCompensations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
