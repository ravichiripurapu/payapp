import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollSummary } from './payroll-summary.model';
import { PayrollSummaryPopupService } from './payroll-summary-popup.service';
import { PayrollSummaryService } from './payroll-summary.service';

@Component({
    selector: 'jhi-payroll-summary-delete-dialog',
    templateUrl: './payroll-summary-delete-dialog.component.html'
})
export class PayrollSummaryDeleteDialogComponent {

    payrollSummary: PayrollSummary;

    constructor(
        private payrollSummaryService: PayrollSummaryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payrollSummaryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'payrollSummaryListModification',
                content: 'Deleted an payrollSummary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payroll-summary-delete-popup',
    template: ''
})
export class PayrollSummaryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollSummaryPopupService: PayrollSummaryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.payrollSummaryPopupService
                .open(PayrollSummaryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
