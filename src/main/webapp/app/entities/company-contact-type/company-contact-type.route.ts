import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyContactTypeComponent } from './company-contact-type.component';
import { CompanyContactTypeDetailComponent } from './company-contact-type-detail.component';
import { CompanyContactTypePopupComponent } from './company-contact-type-dialog.component';
import { CompanyContactTypeDeletePopupComponent } from './company-contact-type-delete-dialog.component';

export const companyContactTypeRoute: Routes = [
    {
        path: 'company-contact-type',
        component: CompanyContactTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContactTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-contact-type/:id',
        component: CompanyContactTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContactTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyContactTypePopupRoute: Routes = [
    {
        path: 'company-contact-type-new',
        component: CompanyContactTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContactTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-contact-type/:id/edit',
        component: CompanyContactTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContactTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-contact-type/:id/delete',
        component: CompanyContactTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContactTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
