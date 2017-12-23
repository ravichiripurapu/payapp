import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEmployerTaxes } from './payroll-employer-taxes.model';
import { PayrollEmployerTaxesPopupService } from './payroll-employer-taxes-popup.service';
import { PayrollEmployerTaxesService } from './payroll-employer-taxes.service';

@Component({
    selector: 'jhi-payroll-employer-taxes-delete-dialog',
    templateUrl: './payroll-employer-taxes-delete-dialog.component.html'
})
export class PayrollEmployerTaxesDeleteDialogComponent {

    payrollEmployerTaxes: PayrollEmployerTaxes;

    constructor(
        private payrollEmployerTaxesService: PayrollEmployerTaxesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payrollEmployerTaxesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'payrollEmployerTaxesListModification',
                content: 'Deleted an payrollEmployerTaxes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payroll-employer-taxes-delete-popup',
    template: ''
})
export class PayrollEmployerTaxesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEmployerTaxesPopupService: PayrollEmployerTaxesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.payrollEmployerTaxesPopupService
                .open(PayrollEmployerTaxesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
