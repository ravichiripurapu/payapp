import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollSchedule } from './payroll-schedule.model';
import { PayrollSchedulePopupService } from './payroll-schedule-popup.service';
import { PayrollScheduleService } from './payroll-schedule.service';

@Component({
    selector: 'jhi-payroll-schedule-dialog',
    templateUrl: './payroll-schedule-dialog.component.html'
})
export class PayrollScheduleDialogComponent implements OnInit {

    payrollSchedule: PayrollSchedule;
    isSaving: boolean;
    checkDateDp: any;
    periodEndDp: any;
    periodStartDp: any;
    approveDateDp: any;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollScheduleService: PayrollScheduleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.payrollSchedule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollScheduleService.update(this.payrollSchedule));
        } else {
            this.subscribeToSaveResponse(
                this.payrollScheduleService.create(this.payrollSchedule));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollSchedule>) {
        result.subscribe((res: PayrollSchedule) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollSchedule) {
        this.eventManager.broadcast({ name: 'payrollScheduleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-schedule-popup',
    template: ''
})
export class PayrollSchedulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollSchedulePopupService: PayrollSchedulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollSchedulePopupService
                    .open(PayrollScheduleDialogComponent as Component, params['id']);
            } else {
                this.payrollSchedulePopupService
                    .open(PayrollScheduleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
