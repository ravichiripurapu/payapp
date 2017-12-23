import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LocalTaxesComponent } from './local-taxes.component';
import { LocalTaxesDetailComponent } from './local-taxes-detail.component';
import { LocalTaxesPopupComponent } from './local-taxes-dialog.component';
import { LocalTaxesDeletePopupComponent } from './local-taxes-delete-dialog.component';

export const localTaxesRoute: Routes = [
    {
        path: 'local-taxes',
        component: LocalTaxesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'local-taxes/:id',
        component: LocalTaxesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocalTaxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const localTaxesPopupRoute: Routes = [
    {
        path: 'local-taxes-new',
        component: LocalTaxesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'local-taxes/:id/edit',
        component: LocalTaxesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'local-taxes/:id/delete',
        component: LocalTaxesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocalTaxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
