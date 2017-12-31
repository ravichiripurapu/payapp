import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEarnings } from './payroll-earnings.model';
import { PayrollEarningsService } from './payroll-earnings.service';

@Component({
    selector: 'jhi-payroll-earnings-detail',
    templateUrl: './payroll-earnings-detail.component.html'
})
export class PayrollEarningsDetailComponent implements OnInit, OnDestroy {

    employeePayEarning: PayrollEarnings;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollEarningsService: PayrollEarningsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollEarnings();
    }

    load(id) {
        this.payrollEarningsService.find(id).subscribe((employeePayEarning) => {
            this.employeePayEarning = employeePayEarning;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollEarnings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollEarningsListModification',
            (response) => this.load(this.employeePayEarning.id)
        );
    }
}
