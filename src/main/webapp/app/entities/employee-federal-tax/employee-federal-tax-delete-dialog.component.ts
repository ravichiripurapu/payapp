import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeFederalTax } from './employee-federal-tax.model';
import { EmployeeFederalTaxPopupService } from './employee-federal-tax-popup.service';
import { EmployeeFederalTaxService } from './employee-federal-tax.service';

@Component({
    selector: 'jhi-employee-federal-tax-delete-dialog',
    templateUrl: './employee-federal-tax-delete-dialog.component.html'
})
export class EmployeeFederalTaxDeleteDialogComponent {

    employeeFederalTax: EmployeeFederalTax;

    constructor(
        private employeeFederalTaxService: EmployeeFederalTaxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeFederalTaxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeFederalTaxListModification',
                content: 'Deleted an employeeFederalTax'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-federal-tax-delete-popup',
    template: ''
})
export class EmployeeFederalTaxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeFederalTaxPopupService: EmployeeFederalTaxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeFederalTaxPopupService
                .open(EmployeeFederalTaxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
