import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyLocationComponent } from './company-location.component';
import { CompanyLocationDetailComponent } from './company-location-detail.component';
import { CompanyLocationPopupComponent } from './company-location-dialog.component';
import { CompanyLocationDeletePopupComponent } from './company-location-delete-dialog.component';

export const companyLocationRoute: Routes = [
    {
        path: 'company-location',
        component: CompanyLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-location/:id',
        component: CompanyLocationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyLocationPopupRoute: Routes = [
    {
        path: 'company-location-new',
        component: CompanyLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-location/:id/edit',
        component: CompanyLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-location/:id/delete',
        component: CompanyLocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
