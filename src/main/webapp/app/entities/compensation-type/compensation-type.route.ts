import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompensationTypeComponent } from './compensation-type.component';
import { CompensationTypeDetailComponent } from './compensation-type-detail.component';
import { CompensationTypePopupComponent } from './compensation-type-dialog.component';
import { CompensationTypeDeletePopupComponent } from './compensation-type-delete-dialog.component';

export const compensationTypeRoute: Routes = [
    {
        path: 'compensation-type',
        component: CompensationTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompensationTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'compensation-type/:id',
        component: CompensationTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompensationTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compensationTypePopupRoute: Routes = [
    {
        path: 'compensation-type-new',
        component: CompensationTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompensationTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compensation-type/:id/edit',
        component: CompensationTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompensationTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compensation-type/:id/delete',
        component: CompensationTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompensationTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
