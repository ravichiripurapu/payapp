import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyContactComponent } from './company-contact.component';
import { CompanyContactDetailComponent } from './company-contact-detail.component';
import { CompanyContactPopupComponent } from './company-contact-dialog.component';
import { CompanyContactDeletePopupComponent } from './company-contact-delete-dialog.component';

export const companyContactRoute: Routes = [
    {
        path: 'company-contact',
        component: CompanyContactComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContacts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-contact/:id',
        component: CompanyContactDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContacts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyContactPopupRoute: Routes = [
    {
        path: 'company-contact-new',
        component: CompanyContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-contact/:id/edit',
        component: CompanyContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-contact/:id/delete',
        component: CompanyContactDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyContacts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
