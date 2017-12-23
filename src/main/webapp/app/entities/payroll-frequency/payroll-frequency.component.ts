import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollFrequency } from './payroll-frequency.model';
import { PayrollFrequencyService } from './payroll-frequency.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-frequency',
    templateUrl: './payroll-frequency.component.html'
})
export class PayrollFrequencyComponent implements OnInit, OnDestroy {
payrollFrequencies: PayrollFrequency[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollFrequencyService: PayrollFrequencyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollFrequencyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollFrequencies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollFrequencies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollFrequency) {
        return item.id;
    }
    registerChangeInPayrollFrequencies() {
        this.eventSubscriber = this.eventManager.subscribe('payrollFrequencyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
