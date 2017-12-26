import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeResidenceLocation } from './employee-residence-location.model';
import { EmployeeResidenceLocationPopupService } from './employee-residence-location-popup.service';
import { EmployeeResidenceLocationService } from './employee-residence-location.service';

@Component({
    selector: 'jhi-employee-residence-location-dialog',
    templateUrl: './employee-residence-location-dialog.component.html'
})
export class EmployeeResidenceLocationDialogComponent implements OnInit {

    employeeResidenceLocation: EmployeeResidenceLocation;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeResidenceLocationService: EmployeeResidenceLocationService,
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
        if (this.employeeResidenceLocation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeResidenceLocationService.update(this.employeeResidenceLocation));
        } else {
            this.subscribeToSaveResponse(
                this.employeeResidenceLocationService.create(this.employeeResidenceLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeResidenceLocation>) {
        result.subscribe((res: EmployeeResidenceLocation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeResidenceLocation) {
        this.eventManager.broadcast({ name: 'employeeResidenceLocationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-residence-location-popup',
    template: ''
})
export class EmployeeResidenceLocationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeResidenceLocationPopupService: EmployeeResidenceLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeResidenceLocationPopupService
                    .open(EmployeeResidenceLocationDialogComponent as Component, params['id']);
            } else {
                this.employeeResidenceLocationPopupService
                    .open(EmployeeResidenceLocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
