import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollEarnings } from './payroll-earnings.model';
import { PayrollEarningsService } from './payroll-earnings.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-earnings',
    templateUrl: './payroll-earnings.component.html'
})
export class PayrollEarningsComponent implements OnInit, OnDestroy {
payrollEarnings: PayrollEarnings[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollEarningsService: PayrollEarningsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollEarningsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollEarnings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollEarnings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollEarnings) {
        return item.id;
    }
    registerChangeInPayrollEarnings() {
        this.eventSubscriber = this.eventManager.subscribe('payrollEarningsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
