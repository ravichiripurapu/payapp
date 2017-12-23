import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeLocalTax } from './employee-local-tax.model';
import { EmployeeLocalTaxPopupService } from './employee-local-tax-popup.service';
import { EmployeeLocalTaxService } from './employee-local-tax.service';

@Component({
    selector: 'jhi-employee-local-tax-dialog',
    templateUrl: './employee-local-tax-dialog.component.html'
})
export class EmployeeLocalTaxDialogComponent implements OnInit {

    employeeLocalTax: EmployeeLocalTax;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeLocalTaxService: EmployeeLocalTaxService,
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
        if (this.employeeLocalTax.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeLocalTaxService.update(this.employeeLocalTax));
        } else {
            this.subscribeToSaveResponse(
                this.employeeLocalTaxService.create(this.employeeLocalTax));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeLocalTax>) {
        result.subscribe((res: EmployeeLocalTax) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeLocalTax) {
        this.eventManager.broadcast({ name: 'employeeLocalTaxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-local-tax-popup',
    template: ''
})
export class EmployeeLocalTaxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeLocalTaxPopupService: EmployeeLocalTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeLocalTaxPopupService
                    .open(EmployeeLocalTaxDialogComponent as Component, params['id']);
            } else {
                this.employeeLocalTaxPopupService
                    .open(EmployeeLocalTaxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
