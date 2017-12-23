import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyLocalTaxComponent } from './company-local-tax.component';
import { CompanyLocalTaxDetailComponent } from './company-local-tax-detail.component';
import { CompanyLocalTaxPopupComponent } from './company-local-tax-dialog.component';
import { CompanyLocalTaxDeletePopupComponent } from './company-local-tax-delete-dialog.component';

export const companyLocalTaxRoute: Routes = [
    {
        path: 'company-local-tax',
        component: CompanyLocalTaxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-local-tax/:id',
        component: CompanyLocalTaxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyLocalTaxPopupRoute: Routes = [
    {
        path: 'company-local-tax-new',
        component: CompanyLocalTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-local-tax/:id/edit',
        component: CompanyLocalTaxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-local-tax/:id/delete',
        component: CompanyLocalTaxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
