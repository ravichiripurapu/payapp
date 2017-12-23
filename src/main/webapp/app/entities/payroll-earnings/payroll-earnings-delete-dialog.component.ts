import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PayrollEarnings } from './payroll-earnings.model';
import { PayrollEarningsPopupService } from './payroll-earnings-popup.service';
import { PayrollEarningsService } from './payroll-earnings.service';

@Component({
    selector: 'jhi-payroll-earnings-delete-dialog',
    templateUrl: './payroll-earnings-delete-dialog.component.html'
})
export class PayrollEarningsDeleteDialogComponent {

    payrollEarnings: PayrollEarnings;

    constructor(
        private payrollEarningsService: PayrollEarningsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payrollEarningsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'payrollEarningsListModification',
                content: 'Deleted an payrollEarnings'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payroll-earnings-delete-popup',
    template: ''
})
export class PayrollEarningsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private payrollEarningsPopupService: PayrollEarningsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.payrollEarningsPopupService
                .open(PayrollEarningsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
