import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeStateTax } from './employee-state-tax.model';
import { EmployeeStateTaxPopupService } from './employee-state-tax-popup.service';
import { EmployeeStateTaxService } from './employee-state-tax.service';

@Component({
    selector: 'jhi-employee-state-tax-delete-dialog',
    templateUrl: './employee-state-tax-delete-dialog.component.html'
})
export class EmployeeStateTaxDeleteDialogComponent {

    employeeStateTax: EmployeeStateTax;

    constructor(
        private employeeStateTaxService: EmployeeStateTaxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeStateTaxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeStateTaxListModification',
                content: 'Deleted an employeeStateTax'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-state-tax-delete-popup',
    template: ''
})
export class EmployeeStateTaxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeStateTaxPopupService: EmployeeStateTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeStateTaxPopupService
                .open(EmployeeStateTaxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
