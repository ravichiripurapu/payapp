import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEarnings } from './payroll-earnings.model';
import { PayrollEarningsPopupService } from './payroll-earnings-popup.service';
import { PayrollEarningsService } from './payroll-earnings.service';

@Component({
    selector: 'jhi-payroll-earnings-dialog',
    templateUrl: './payroll-earnings-dialog.component.html'
})
export class PayrollEarningsDialogComponent implements OnInit {

    employeePayEarning: PayrollEarnings;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollEarningsService: PayrollEarningsService,
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
        if (this.employeePayEarning.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollEarningsService.update(this.employeePayEarning));
        } else {
            this.subscribeToSaveResponse(
                this.payrollEarningsService.create(this.employeePayEarning));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollEarnings>) {
        result.subscribe((res: PayrollEarnings) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollEarnings) {
        this.eventManager.broadcast({ name: 'payrollEarningsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-earnings-popup',
    template: ''
})
export class PayrollEarningsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEarningsPopupService: PayrollEarningsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollEarningsPopupService
                    .open(PayrollEarningsDialogComponent as Component, params['id']);
            } else {
                this.payrollEarningsPopupService
                    .open(PayrollEarningsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
