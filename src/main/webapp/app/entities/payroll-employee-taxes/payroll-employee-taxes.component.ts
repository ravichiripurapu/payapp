import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollEmployeeTaxes } from './payroll-employee-taxes.model';
import { PayrollEmployeeTaxesService } from './payroll-employee-taxes.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-employee-taxes',
    templateUrl: './payroll-employee-taxes.component.html'
})
export class PayrollEmployeeTaxesComponent implements OnInit, OnDestroy {
payrollEmployeeTaxes: PayrollEmployeeTaxes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollEmployeeTaxesService: PayrollEmployeeTaxesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollEmployeeTaxesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollEmployeeTaxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollEmployeeTaxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollEmployeeTaxes) {
        return item.id;
    }
    registerChangeInPayrollEmployeeTaxes() {
        this.eventSubscriber = this.eventManager.subscribe('payrollEmployeeTaxesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
