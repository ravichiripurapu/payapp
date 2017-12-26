import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollSummary } from './payroll-summary.model';
import { PayrollSummaryService } from './payroll-summary.service';

@Component({
    selector: 'jhi-payroll-summary-detail',
    templateUrl: './payroll-summary-detail.component.html'
})
export class PayrollSummaryDetailComponent implements OnInit, OnDestroy {

    payrollSummary: PayrollSummary;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollSummaryService: PayrollSummaryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollSummaries();
    }

    load(id) {
        this.payrollSummaryService.find(id).subscribe((payrollSummary) => {
            this.payrollSummary = payrollSummary;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollSummaries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollSummaryListModification',
            (response) => this.load(this.payrollSummary.id)
        );
    }
}
