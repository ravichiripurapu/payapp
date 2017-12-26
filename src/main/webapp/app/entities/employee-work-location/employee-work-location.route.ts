import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeWorkLocationComponent } from './employee-work-location.component';
import { EmployeeWorkLocationDetailComponent } from './employee-work-location-detail.component';
import { EmployeeWorkLocationPopupComponent } from './employee-work-location-dialog.component';
import { EmployeeWorkLocationDeletePopupComponent } from './employee-work-location-delete-dialog.component';

export const employeeWorkLocationRoute: Routes = [
    {
        path: 'employee-work-location',
        component: EmployeeWorkLocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeWorkLocations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-work-location/:id',
        component: EmployeeWorkLocationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeWorkLocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeWorkLocationPopupRoute: Routes = [
    {
        path: 'employee-work-location-new',
        component: EmployeeWorkLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeWorkLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-work-location/:id/edit',
        component: EmployeeWorkLocationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeWorkLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-work-location/:id/delete',
        component: EmployeeWorkLocationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeWorkLocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
