import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PayrollScheduleComponent } from './payroll-schedule.component';
import { PayrollScheduleDetailComponent } from './payroll-schedule-detail.component';
import { PayrollSchedulePopupComponent } from './payroll-schedule-dialog.component';
import { PayrollScheduleDeletePopupComponent } from './payroll-schedule-delete-dialog.component';

export const payrollScheduleRoute: Routes = [
    {
        path: 'payroll-schedule',
        component: PayrollScheduleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSchedules'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payroll-schedule/:id',
        component: PayrollScheduleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSchedules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const payrollSchedulePopupRoute: Routes = [
    {
        path: 'payroll-schedule-new',
        component: PayrollSchedulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSchedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-schedule/:id/edit',
        component: PayrollSchedulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSchedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payroll-schedule/:id/delete',
        component: PayrollScheduleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PayrollSchedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
