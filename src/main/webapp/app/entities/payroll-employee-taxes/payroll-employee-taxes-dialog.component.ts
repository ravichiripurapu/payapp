import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployeeTaxes } from './payroll-employee-taxes.model';
import { PayrollEmployeeTaxesPopupService } from './payroll-employee-taxes-popup.service';
import { PayrollEmployeeTaxesService } from './payroll-employee-taxes.service';

@Component({
    selector: 'jhi-payroll-employee-taxes-dialog',
    templateUrl: './payroll-employee-taxes-dialog.component.html'
})
export class PayrollEmployeeTaxesDialogComponent implements OnInit {

    payrollEmployeeTaxes: PayrollEmployeeTaxes;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollEmployeeTaxesService: PayrollEmployeeTaxesService,
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
        if (this.payrollEmployeeTaxes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollEmployeeTaxesService.update(this.payrollEmployeeTaxes));
        } else {
            this.subscribeToSaveResponse(
                this.payrollEmployeeTaxesService.create(this.payrollEmployeeTaxes));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollEmployeeTaxes>) {
        result.subscribe((res: PayrollEmployeeTaxes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollEmployeeTaxes) {
        this.eventManager.broadcast({ name: 'payrollEmployeeTaxesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-employee-taxes-popup',
    template: ''
})
export class PayrollEmployeeTaxesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEmployeeTaxesPopupService: PayrollEmployeeTaxesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollEmployeeTaxesPopupService
                    .open(PayrollEmployeeTaxesDialogComponent as Component, params['id']);
            } else {
                this.payrollEmployeeTaxesPopupService
                    .open(PayrollEmployeeTaxesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
