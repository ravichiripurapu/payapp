import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollSummary } from './payroll-summary.model';
import { PayrollSummaryPopupService } from './payroll-summary-popup.service';
import { PayrollSummaryService } from './payroll-summary.service';

@Component({
    selector: 'jhi-payroll-summary-dialog',
    templateUrl: './payroll-summary-dialog.component.html'
})
export class PayrollSummaryDialogComponent implements OnInit {

    payrollSummary: PayrollSummary;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollSummaryService: PayrollSummaryService,
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
        if (this.payrollSummary.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollSummaryService.update(this.payrollSummary));
        } else {
            this.subscribeToSaveResponse(
                this.payrollSummaryService.create(this.payrollSummary));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollSummary>) {
        result.subscribe((res: PayrollSummary) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollSummary) {
        this.eventManager.broadcast({ name: 'payrollSummaryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-summary-popup',
    template: ''
})
export class PayrollSummaryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollSummaryPopupService: PayrollSummaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollSummaryPopupService
                    .open(PayrollSummaryDialogComponent as Component, params['id']);
            } else {
                this.payrollSummaryPopupService
                    .open(PayrollSummaryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
