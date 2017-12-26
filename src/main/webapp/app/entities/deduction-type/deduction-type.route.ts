import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DeductionTypeComponent } from './deduction-type.component';
import { DeductionTypeDetailComponent } from './deduction-type-detail.component';
import { DeductionTypePopupComponent } from './deduction-type-dialog.component';
import { DeductionTypeDeletePopupComponent } from './deduction-type-delete-dialog.component';

export const deductionTypeRoute: Routes = [
    {
        path: 'deduction-type',
        component: DeductionTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deduction-type/:id',
        component: DeductionTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deductionTypePopupRoute: Routes = [
    {
        path: 'deduction-type-new',
        component: DeductionTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deduction-type/:id/edit',
        component: DeductionTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deduction-type/:id/delete',
        component: DeductionTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeductionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
