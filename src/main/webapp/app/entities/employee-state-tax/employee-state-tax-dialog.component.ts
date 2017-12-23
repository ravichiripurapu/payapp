import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeStateTax } from './employee-state-tax.model';
import { EmployeeStateTaxPopupService } from './employee-state-tax-popup.service';
import { EmployeeStateTaxService } from './employee-state-tax.service';

@Component({
    selector: 'jhi-employee-state-tax-dialog',
    templateUrl: './employee-state-tax-dialog.component.html'
})
export class EmployeeStateTaxDialogComponent implements OnInit {

    employeeStateTax: EmployeeStateTax;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeStateTaxService: EmployeeStateTaxService,
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
        if (this.employeeStateTax.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeStateTaxService.update(this.employeeStateTax));
        } else {
            this.subscribeToSaveResponse(
                this.employeeStateTaxService.create(this.employeeStateTax));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeStateTax>) {
        result.subscribe((res: EmployeeStateTax) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeStateTax) {
        this.eventManager.broadcast({ name: 'employeeStateTaxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-state-tax-popup',
    template: ''
})
export class EmployeeStateTaxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeStateTaxPopupService: EmployeeStateTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeStateTaxPopupService
                    .open(EmployeeStateTaxDialogComponent as Component, params['id']);
            } else {
                this.employeeStateTaxPopupService
                    .open(EmployeeStateTaxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
