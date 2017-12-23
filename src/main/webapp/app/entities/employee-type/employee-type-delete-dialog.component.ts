import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeType } from './employee-type.model';
import { EmployeeTypePopupService } from './employee-type-popup.service';
import { EmployeeTypeService } from './employee-type.service';

@Component({
    selector: 'jhi-employee-type-delete-dialog',
    templateUrl: './employee-type-delete-dialog.component.html'
})
export class EmployeeTypeDeleteDialogComponent {

    employeeType: EmployeeType;

    constructor(
        private employeeTypeService: EmployeeTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeTypeListModification',
                content: 'Deleted an employeeType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-type-delete-popup',
    template: ''
})
export class EmployeeTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeTypePopupService: EmployeeTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeTypePopupService
                .open(EmployeeTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
