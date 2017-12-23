import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeWorkLocation } from './employee-work-location.model';
import { EmployeeWorkLocationPopupService } from './employee-work-location-popup.service';
import { EmployeeWorkLocationService } from './employee-work-location.service';

@Component({
    selector: 'jhi-employee-work-location-dialog',
    templateUrl: './employee-work-location-dialog.component.html'
})
export class EmployeeWorkLocationDialogComponent implements OnInit {

    employeeWorkLocation: EmployeeWorkLocation;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private employeeWorkLocationService: EmployeeWorkLocationService,
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
        if (this.employeeWorkLocation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeWorkLocationService.update(this.employeeWorkLocation));
        } else {
            this.subscribeToSaveResponse(
                this.employeeWorkLocationService.create(this.employeeWorkLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmployeeWorkLocation>) {
        result.subscribe((res: EmployeeWorkLocation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeWorkLocation) {
        this.eventManager.broadcast({ name: 'employeeWorkLocationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-employee-work-location-popup',
    template: ''
})
export class EmployeeWorkLocationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeWorkLocationPopupService: EmployeeWorkLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeWorkLocationPopupService
                    .open(EmployeeWorkLocationDialogComponent as Component, params['id']);
            } else {
                this.employeeWorkLocationPopupService
                    .open(EmployeeWorkLocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
