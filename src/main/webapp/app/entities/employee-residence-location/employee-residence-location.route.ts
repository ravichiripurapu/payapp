import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeResidenceLocationComponent } from './employee-residence-location.component';
import { EmployeeResidenceLocationDetailComponent } from './employee-residence-location-detail.component';
import { EmployeeResidenceLocationPopupComponent } from './employee-residence-location-dialog.component';
import { EmployeeResidenceLocationDeletePopupComponent } from './employee-residence-location-delete-dialog.component';

export const employeeResidenceLocationRoute: Routes = [
    {
        path: 'employee-residence-location',
        component: EmployeeResidenceLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeResidenceLocations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-residence-location/:id',
        component: EmployeeResidenceLocationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeResidenceLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeResidenceLocationPopupRoute: Routes = [
    {
        path: 'employee-residence-location-new',
        component: EmployeeResidenceLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeResidenceLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-residence-location/:id/edit',
        component: EmployeeResidenceLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeResidenceLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-residence-location/:id/delete',
        component: EmployeeResidenceLocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeResidenceLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
