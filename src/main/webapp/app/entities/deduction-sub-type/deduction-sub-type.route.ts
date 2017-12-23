import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DeductionSubTypeComponent } from './deduction-sub-type.component';
import { DeductionSubTypeDetailComponent } from './deduction-sub-type-detail.component';
import { DeductionSubTypePopupComponent } from './deduction-sub-type-dialog.component';
import { DeductionSubTypeDeletePopupComponent } from './deduction-sub-type-delete-dialog.component';

export const deductionSubTypeRoute: Routes = [
    {
        path: 'deduction-sub-type',
        component: DeductionSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionSubTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deduction-sub-type/:id',
        component: DeductionSubTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionSubTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deductionSubTypePopupRoute: Routes = [
    {
        path: 'deduction-sub-type-new',
        component: DeductionSubTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionSubTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deduction-sub-type/:id/edit',
        component: DeductionSubTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionSubTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deduction-sub-type/:id/delete',
        component: DeductionSubTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionSubTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
