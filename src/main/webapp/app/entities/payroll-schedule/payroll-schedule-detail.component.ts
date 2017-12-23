import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollSchedule } from './payroll-schedule.model';
import { PayrollScheduleService } from './payroll-schedule.service';

@Component({
    selector: 'jhi-payroll-schedule-detail',
    templateUrl: './payroll-schedule-detail.component.html'
})
export class PayrollScheduleDetailComponent implements OnInit, OnDestroy {

    payrollSchedule: PayrollSchedule;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private payrollScheduleService: PayrollScheduleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPayrollSchedules();
    }

    load(id) {
        this.payrollScheduleService.find(id).subscribe((payrollSchedule) => {
            this.payrollSchedule = payrollSchedule;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPayrollSchedules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'payrollScheduleListModification',
            (response) => this.load(this.payrollSchedule.id)
        );
    }
}
