import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeWorkLocation } from './employee-work-location.model';
import { EmployeeWorkLocationPopupService } from './employee-work-location-popup.service';
import { EmployeeWorkLocationService } from './employee-work-location.service';

@Component({
    selector: 'jhi-employee-work-location-delete-dialog',
    templateUrl: './employee-work-location-delete-dialog.component.html'
})
export class EmployeeWorkLocationDeleteDialogComponent {

    employeeWorkLocation: EmployeeWorkLocation;

    constructor(
        private employeeWorkLocationService: EmployeeWorkLocationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeWorkLocationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeWorkLocationListModification',
                content: 'Deleted an employeeWorkLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-work-location-delete-popup',
    template: ''
})
export class EmployeeWorkLocationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeWorkLocationPopupService: EmployeeWorkLocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeWorkLocationPopupService
                .open(EmployeeWorkLocationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
