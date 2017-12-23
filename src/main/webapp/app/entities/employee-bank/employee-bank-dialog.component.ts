import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeBank } from './employee-bank.model';
import { EmployeeBankPopupService } from './employee-bank-popup.service';
import { EmployeeBankService } from './employee-bank.service';

@Component({
    selector: 'jhi-employee-bank-dialog',
    templateUrl: './employee-bank-dialog.component.html'
})
export class EmployeeBankDialogComponent implements OnInit {

    employeeBank: EmployeeBank;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeBankService: EmployeeBankService,
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
        if (this.employeeBank.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeBankService.update(this.employeeBank));
        } else {
            this.subscribeToSaveResponse(
                this.employeeBankService.create(this.employeeBank));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeBank>) {
        result.subscribe((res: EmployeeBank) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeBank) {
        this.eventManager.broadcast({ name: 'employeeBankListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-bank-popup',
    template: ''
})
export class EmployeeBankPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeBankPopupService: EmployeeBankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeBankPopupService
                    .open(EmployeeBankDialogComponent as Component, params['id']);
            } else {
                this.employeeBankPopupService
                    .open(EmployeeBankDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
