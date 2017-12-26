import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollFrequency } from './payroll-frequency.model';
import { PayrollFrequencyPopupService } from './payroll-frequency-popup.service';
import { PayrollFrequencyService } from './payroll-frequency.service';

@Component({
    selector: 'jhi-payroll-frequency-dialog',
    templateUrl: './payroll-frequency-dialog.component.html'
})
export class PayrollFrequencyDialogComponent implements OnInit {

    payrollFrequency: PayrollFrequency;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollFrequencyService: PayrollFrequencyService,
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
        if (this.payrollFrequency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollFrequencyService.update(this.payrollFrequency));
        } else {
            this.subscribeToSaveResponse(
                this.payrollFrequencyService.create(this.payrollFrequency));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollFrequency>) {
        result.subscribe((res: PayrollFrequency) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollFrequency) {
        this.eventManager.broadcast({ name: 'payrollFrequencyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-frequency-popup',
    template: ''
})
export class PayrollFrequencyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollFrequencyPopupService: PayrollFrequencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollFrequencyPopupService
                    .open(PayrollFrequencyDialogComponent as Component, params['id']);
            } else {
                this.payrollFrequencyPopupService
                    .open(PayrollFrequencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
