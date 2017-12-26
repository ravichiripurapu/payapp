import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyStateTaxComponent } from './company-state-tax.component';
import { CompanyStateTaxDetailComponent } from './company-state-tax-detail.component';
import { CompanyStateTaxPopupComponent } from './company-state-tax-dialog.component';
import { CompanyStateTaxDeletePopupComponent } from './company-state-tax-delete-dialog.component';

export const companyStateTaxRoute: Routes = [
    {
        path: 'company-state-tax',
        component: CompanyStateTaxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyStateTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-state-tax/:id',
        component: CompanyStateTaxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyStateTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyStateTaxPopupRoute: Routes = [
    {
        path: 'company-state-tax-new',
        component: CompanyStateTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyStateTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-state-tax/:id/edit',
        component: CompanyStateTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyStateTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-state-tax/:id/delete',
        component: CompanyStateTaxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyStateTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
