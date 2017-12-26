import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollFrequency } from './payroll-frequency.model';
import { PayrollFrequencyService } from './payroll-frequency.service';

@Component({
    selector: 'jhi-payroll-frequency-detail',
    templateUrl: './payroll-frequency-detail.component.html'
})
export class PayrollFrequencyDetailComponent implements OnInit, OnDestroy {

    payrollFrequency: PayrollFrequency;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollFrequencyService: PayrollFrequencyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollFrequencies();
    }

    load(id) {
        this.payrollFrequencyService.find(id).subscribe((payrollFrequency) => {
            this.payrollFrequency = payrollFrequency;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollFrequencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollFrequencyListModification',
            (response) => this.load(this.payrollFrequency.id)
        );
    }
}
