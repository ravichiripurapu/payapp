import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FutaExemptReasonCodeComponent } from './futa-exempt-reason-code.component';
import { FutaExemptReasonCodeDetailComponent } from './futa-exempt-reason-code-detail.component';
import { FutaExemptReasonCodePopupComponent } from './futa-exempt-reason-code-dialog.component';
import { FutaExemptReasonCodeDeletePopupComponent } from './futa-exempt-reason-code-delete-dialog.component';

export const futaExemptReasonCodeRoute: Routes = [
    {
        path: 'futa-exempt-reason-code',
        component: FutaExemptReasonCodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FutaExemptReasonCodes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'futa-exempt-reason-code/:id',
        component: FutaExemptReasonCodeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FutaExemptReasonCodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const futaExemptReasonCodePopupRoute: Routes = [
    {
        path: 'futa-exempt-reason-code-new',
        component: FutaExemptReasonCodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FutaExemptReasonCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'futa-exempt-reason-code/:id/edit',
        component: FutaExemptReasonCodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FutaExemptReasonCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'futa-exempt-reason-code/:id/delete',
        component: FutaExemptReasonCodeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FutaExemptReasonCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
