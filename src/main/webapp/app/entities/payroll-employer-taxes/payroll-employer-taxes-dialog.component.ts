import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployerTaxes } from './payroll-employer-taxes.model';
import { PayrollEmployerTaxesPopupService } from './payroll-employer-taxes-popup.service';
import { PayrollEmployerTaxesService } from './payroll-employer-taxes.service';

@Component({
    selector: 'jhi-payroll-employer-taxes-dialog',
    templateUrl: './payroll-employer-taxes-dialog.component.html'
})
export class PayrollEmployerTaxesDialogComponent implements OnInit {

    payrollEmployerTaxes: PayrollEmployerTaxes;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollEmployerTaxesService: PayrollEmployerTaxesService,
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
        if (this.payrollEmployerTaxes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollEmployerTaxesService.update(this.payrollEmployerTaxes));
        } else {
            this.subscribeToSaveResponse(
                this.payrollEmployerTaxesService.create(this.payrollEmployerTaxes));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollEmployerTaxes>) {
        result.subscribe((res: PayrollEmployerTaxes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollEmployerTaxes) {
        this.eventManager.broadcast({ name: 'payrollEmployerTaxesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-employer-taxes-popup',
    template: ''
})
export class PayrollEmployerTaxesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEmployerTaxesPopupService: PayrollEmployerTaxesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollEmployerTaxesPopupService
                    .open(PayrollEmployerTaxesDialogComponent as Component, params['id']);
            } else {
                this.payrollEmployerTaxesPopupService
                    .open(PayrollEmployerTaxesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
