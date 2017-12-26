import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeType } from './employee-type.model';
import { EmployeeTypePopupService } from './employee-type-popup.service';
import { EmployeeTypeService } from './employee-type.service';

@Component({
    selector: 'jhi-employee-type-dialog',
    templateUrl: './employee-type-dialog.component.html'
})
export class EmployeeTypeDialogComponent implements OnInit {

    employeeType: EmployeeType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeTypeService: EmployeeTypeService,
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
        if (this.employeeType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeTypeService.update(this.employeeType));
        } else {
            this.subscribeToSaveResponse(
                this.employeeTypeService.create(this.employeeType));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeType>) {
        result.subscribe((res: EmployeeType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeType) {
        this.eventManager.broadcast({ name: 'employeeTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-type-popup',
    template: ''
})
export class EmployeeTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeTypePopupService: EmployeeTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeTypePopupService
                    .open(EmployeeTypeDialogComponent as Component, params['id']);
            } else {
                this.employeeTypePopupService
                    .open(EmployeeTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
