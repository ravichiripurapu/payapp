import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployeeTaxes } from './payroll-employee-taxes.model';
import { PayrollEmployeeTaxesPopupService } from './payroll-employee-taxes-popup.service';
import { PayrollEmployeeTaxesService } from './payroll-employee-taxes.service';

@Component({
    selector: 'jhi-payroll-employee-taxes-delete-dialog',
    templateUrl: './payroll-employee-taxes-delete-dialog.component.html'
})
export class PayrollEmployeeTaxesDeleteDialogComponent {

    payrollEmployeeTaxes: PayrollEmployeeTaxes;

    constructor(
        private payrollEmployeeTaxesService: PayrollEmployeeTaxesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payrollEmployeeTaxesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'payrollEmployeeTaxesListModification',
                content: 'Deleted an payrollEmployeeTaxes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payroll-employee-taxes-delete-popup',
    template: ''
})
export class PayrollEmployeeTaxesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEmployeeTaxesPopupService: PayrollEmployeeTaxesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.payrollEmployeeTaxesPopupService
                .open(PayrollEmployeeTaxesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
