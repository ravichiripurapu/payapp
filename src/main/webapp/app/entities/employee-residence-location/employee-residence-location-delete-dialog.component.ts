import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeResidenceLocation } from './employee-residence-location.model';
import { EmployeeResidenceLocationPopupService } from './employee-residence-location-popup.service';
import { EmployeeResidenceLocationService } from './employee-residence-location.service';

@Component({
    selector: 'jhi-employee-residence-location-delete-dialog',
    templateUrl: './employee-residence-location-delete-dialog.component.html'
})
export class EmployeeResidenceLocationDeleteDialogComponent {

    employeeResidenceLocation: EmployeeResidenceLocation;

    constructor(
        private employeeResidenceLocationService: EmployeeResidenceLocationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeResidenceLocationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeResidenceLocationListModification',
                content: 'Deleted an employeeResidenceLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-residence-location-delete-popup',
    template: ''
})
export class EmployeeResidenceLocationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeResidenceLocationPopupService: EmployeeResidenceLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeResidenceLocationPopupService
                .open(EmployeeResidenceLocationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
