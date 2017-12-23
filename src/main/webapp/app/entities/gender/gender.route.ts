import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GenderComponent } from './gender.component';
import { GenderDetailComponent } from './gender-detail.component';
import { GenderPopupComponent } from './gender-dialog.component';
import { GenderDeletePopupComponent } from './gender-delete-dialog.component';

export const genderRoute: Routes = [
    {
        path: 'gender',
        component: GenderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gender/:id',
        component: GenderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genderPopupRoute: Routes = [
    {
        path: 'gender-new',
        component: GenderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gender/:id/edit',
        component: GenderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gender/:id/delete',
        component: GenderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Genders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];