import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollEmployee } from './payroll-employee.model';
import { PayrollEmployeeService } from './payroll-employee.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-employee',
    templateUrl: './payroll-employee.component.html'
})
export class PayrollEmployeeComponent implements OnInit, OnDestroy {
payrollEmployees: PayrollEmployee[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollEmployeeService: PayrollEmployeeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollEmployeeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollEmployees = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollEmployees();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollEmployee) {
        return item.id;
    }
    registerChangeInPayrollEmployees() {
        this.eventSubscriber = this.eventManager.subscribe('payrollEmployeeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
