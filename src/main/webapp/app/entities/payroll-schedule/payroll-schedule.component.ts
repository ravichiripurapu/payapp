import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PayrollSchedule } from './payroll-schedule.model';
import { PayrollScheduleService } from './payroll-schedule.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payroll-schedule',
    templateUrl: './payroll-schedule.component.html'
})
export class PayrollScheduleComponent implements OnInit, OnDestroy {
payrollSchedules: PayrollSchedule[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private payrollScheduleService: PayrollScheduleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.payrollScheduleService.query().subscribe(
            (res: ResponseWrapper) => {
                this.payrollSchedules = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollSchedules();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PayrollSchedule) {
        return item.id;
    }
    registerChangeInPayrollSchedules() {
        this.eventSubscriber = this.eventManager.subscribe('payrollScheduleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
