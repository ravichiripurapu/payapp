import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeLocalTax } from './employee-local-tax.model';
import { EmployeeLocalTaxPopupService } from './employee-local-tax-popup.service';
import { EmployeeLocalTaxService } from './employee-local-tax.service';

@Component({
    selector: 'jhi-employee-local-tax-delete-dialog',
    templateUrl: './employee-local-tax-delete-dialog.component.html'
})
export class EmployeeLocalTaxDeleteDialogComponent {

    employeeLocalTax: EmployeeLocalTax;

    constructor(
        private employeeLocalTaxService: EmployeeLocalTaxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeLocalTaxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeLocalTaxListModification',
                content: 'Deleted an employeeLocalTax'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-local-tax-delete-popup',
    template: ''
})
export class EmployeeLocalTaxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeLocalTaxPopupService: EmployeeLocalTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeLocalTaxPopupService
                .open(EmployeeLocalTaxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
