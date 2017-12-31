import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeFederalTax } from './employee-federal-tax.model';
import { EmployeeFederalTaxPopupService } from './employee-federal-tax-popup.service';
import { EmployeeFederalTaxService } from './employee-federal-tax.service';

@Component({
    selector: 'jhi-employee-federal-tax-dialog',
    templateUrl: './employee-federal-tax-dialog.component.html'
})
export class EmployeeFederalTaxDialogComponent implements OnInit {

    employeeTaxDeduction: EmployeeFederalTax;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeFederalTaxService: EmployeeFederalTaxService,
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
        if (this.employeeTaxDeduction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeFederalTaxService.update(this.employeeTaxDeduction));
        } else {
            this.subscribeToSaveResponse(
                this.employeeFederalTaxService.create(this.employeeTaxDeduction));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeFederalTax>) {
        result.subscribe((res: EmployeeFederalTax) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeFederalTax) {
        this.eventManager.broadcast({ name: 'employeeFederalTaxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-federal-tax-popup',
    template: ''
})
export class EmployeeFederalTaxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeFederalTaxPopupService: EmployeeFederalTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeFederalTaxPopupService
                    .open(EmployeeFederalTaxDialogComponent as Component, params['id']);
            } else {
                this.employeeFederalTaxPopupService
                    .open(EmployeeFederalTaxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
