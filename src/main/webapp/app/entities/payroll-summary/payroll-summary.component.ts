import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollSummary } from './payroll-summary.model';
import { PayrollSummaryService } from './payroll-summary.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-summary',
    templateUrl: './payroll-summary.component.html'
})
export class PayrollSummaryComponent implements OnInit, OnDestroy {
payrollSummaries: PayrollSummary[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollSummaryService: PayrollSummaryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollSummaryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollSummaries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollSummaries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollSummary) {
        return item.id;
    }
    registerChangeInPayrollSummaries() {
        this.eventSubscriber = this.eventManager.subscribe('payrollSummaryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
