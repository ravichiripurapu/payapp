import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployee } from './payroll-employee.model';
import { PayrollEmployeePopupService } from './payroll-employee-popup.service';
import { PayrollEmployeeService } from './payroll-employee.service';

@Component({
    selector: 'jhi-payroll-employee-dialog',
    templateUrl: './payroll-employee-dialog.component.html'
})
export class PayrollEmployeeDialogComponent implements OnInit {

    payrollEmployee: PayrollEmployee;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private payrollEmployeeService: PayrollEmployeeService,
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
        if (this.payrollEmployee.id !== undefined) {
            this.subscribeToSaveResponse(
                this.payrollEmployeeService.update(this.payrollEmployee));
        } else {
            this.subscribeToSaveResponse(
                this.payrollEmployeeService.create(this.payrollEmployee));
        }
    }

    private subscribeToSaveResponse(result: Observable<PayrollEmployee>) {
        result.subscribe((res: PayrollEmployee) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PayrollEmployee) {
        this.eventManager.broadcast({ name: 'payrollEmployeeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-payroll-employee-popup',
    template: ''
})
export class PayrollEmployeePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEmployeePopupService: PayrollEmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.payrollEmployeePopupService
                    .open(PayrollEmployeeDialogComponent as Component, params['id']);
            } else {
                this.payrollEmployeePopupService
                    .open(PayrollEmployeeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
