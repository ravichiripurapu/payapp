import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyBankComponent } from './company-bank.component';
import { CompanyBankDetailComponent } from './company-bank-detail.component';
import { CompanyBankPopupComponent } from './company-bank-dialog.component';
import { CompanyBankDeletePopupComponent } from './company-bank-delete-dialog.component';

export const companyBankRoute: Routes = [
    {
        path: 'company-bank',
        component: CompanyBankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyBanks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-bank/:id',
        component: CompanyBankDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyBanks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyBankPopupRoute: Routes = [
    {
        path: 'company-bank-new',
        component: CompanyBankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyBanks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-bank/:id/edit',
        component: CompanyBankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyBanks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-bank/:id/delete',
        component: CompanyBankDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyBanks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
